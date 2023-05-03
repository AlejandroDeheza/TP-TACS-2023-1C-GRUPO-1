package com.tacs.bot.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


import com.tacs.bot.dto.Message;
import com.tacs.bot.utils.Utils;
import com.tacs.bot.validator.TypeValidatorChain;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import java.util.function.Function;


@Service
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramBot.class);
    private final String botName;
    private final String botToken;
    private final ApiFactory apiFactory;

    public TelegramBot(@Value("${bot.username}") String botName, @Value("${bot.token}") String botToken,
                       @Autowired ApiFactory apiFactory, @Autowired TelegramBotsApi telegramBotsApi) throws TelegramApiException {
        this.botName = botName;
        this.botToken = botToken;
        telegramBotsApi.registerBot(this);
        this.apiFactory = apiFactory;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String input = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        LOGGER.info("Input: {}", input);
        String json;
        try {
            String[] fields = StringUtils.split(input, "|");
            Message message = Utils.getMessageFromInput(fields);
            TypeValidatorChain.validate(message);
            Function<Message, Object> method = apiFactory.getApiService(message.getType());
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            Object result = method.apply(message);
            json = result.getClass().equals(String.class) ? (String)result : ow.writeValueAsString(result);
            json = json.getBytes().length > 4096 ? "Message is too long" : json;

        } catch (Exception e) {
            sendMessage(generateSendMessage(chatId, e.getMessage()));
            return;
        }
        sendMessage(generateSendMessage(chatId, json));
    }

    private SendMessage generateSendMessage(Long chatId, String json) {
        return new SendMessage(chatId.toString(), json);
    }

    private void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
