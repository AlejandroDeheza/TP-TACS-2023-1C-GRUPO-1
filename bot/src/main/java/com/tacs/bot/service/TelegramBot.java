package com.tacs.bot.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


import com.tacs.bot.dto.EventDto;
import com.tacs.bot.dto.Message;
import com.tacs.bot.utils.Utils;
import com.tacs.bot.validator.TypeValidator;
import com.tacs.bot.validator.TypeValidatorChain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;


@Service
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final static Logger LOGGER = LoggerFactory.getLogger(TelegramBot.class);

    @Autowired
    private ApiFactory apiFactory;


    public TelegramBot(@Autowired TelegramBotsApi telegramBotsApi) throws TelegramApiException {
        telegramBotsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return "tacs_telegram_bot";
    }

    @Override
    public String getBotToken() {
        return "5803459495:AAECMPcsjkrwLWSCbVFFw9umglY3WWbBmGI";
    }

    @Override
    public void onUpdateReceived(Update update) {
        String input = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        LOGGER.info("Input: {}", input);
        String json = null;
        try {
            String[] fields = StringUtils.split(input, "|");
            Message message = Utils.getMessageFromInput(fields);
            TypeValidatorChain.validate(message);
            Function method = apiFactory.getApiService(message.getType());
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            Object result = method.apply(message);
            json = result.getClass().equals(String.class) ? (String)result : ow.writeValueAsString(result);

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
            e.printStackTrace();
        }
    }
}
