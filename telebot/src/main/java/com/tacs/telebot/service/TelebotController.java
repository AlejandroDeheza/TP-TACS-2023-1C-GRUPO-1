package com.tacs.telebot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.kshashov.telegram.api.TelegramMvcController;
import com.github.kshashov.telegram.api.bind.annotation.BotController;
import com.github.kshashov.telegram.api.bind.annotation.BotPathVariable;
import com.github.kshashov.telegram.api.bind.annotation.request.MessageRequest;
import com.tacs.telebot.dto.Message;
import com.tacs.telebot.dto.Type;
import com.tacs.telebot.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotBlank;


@BotController
@RequiredArgsConstructor
public class TelebotController implements TelegramMvcController {
    private static final String BEARER = "Bearer";
    @Value("${bot.token}")
    private String token;
    private final TelebotService telebotService;

    @Override
    public String getToken() {
        return token;
    }

    @MessageRequest("/authentication {body}" )
    public String authentication(@NotBlank @BotPathVariable("body") String body) {
        Message message = Message.builder()
                        .type(Type.AUTH_AUTHENTICATION.name())
                        .body(body).build();
        return telebotService.getResult(message);
    }

    @MessageRequest("/register {body}" )
    public String register(@BotPathVariable("body") String body) {
        Message message = Message.builder()
                .type(Type.AUTH_REGISTER.name())
                .body(body).build();
        return telebotService.getResult(message);
    }

    @MessageRequest("/new_event {token} {body}" )
    public String createEvent(@BotPathVariable("token") String token, @BotPathVariable("body") String body) {
        Message message = Message.builder()
                .type(Type.EVENTS_CREATE.name())
                .token(BEARER + token)
                .body(body).build();
        return telebotService.getResult(message);
    }

    @MessageRequest("/all_events {token}" )
    public String getAllEvents(@BotPathVariable("token") String token) {
        Message message = Message.builder()
                .type(Type.EVENTS_ALL.name())
                .token(BEARER + token).build();
        return telebotService.getResult(message);
    }

    @MessageRequest("/event_by_id {token} {eventId}" )
    public String getEventById(@BotPathVariable("token") String token, @BotPathVariable("eventId") String eventId) {
        Message message = Message.builder()
                .type(Type.EVENTS_BY_ID.name())
                .token(BEARER + token)
                .eventId(eventId).build();
        return telebotService.getResult(message);
    }

    @MessageRequest("/vote_event_option {token} {eventId} {optionId}")
    public String voteEventOption(@BotPathVariable("token") String token, @BotPathVariable("eventId") String eventId, @BotPathVariable("optionId") String optionId) {
        Message message = Message.builder()
                .type(Type.EVENTS_VOTE.name())
                .token(BEARER + token)
                .eventId(eventId)
                .optionId(optionId).build();
        return telebotService.getResult(message);
    }

    @MessageRequest("/register_event {token} {eventId}")
    public String registerEvent(@BotPathVariable("token") String token, @BotPathVariable("eventId") String eventId) {
        Message message = Message.builder()
                .type(Type.EVENTS_REGISTER.name())
                .token(BEARER + token)
                .eventId(eventId).build();
        return telebotService.getResult(message);
    }

    @MessageRequest("/change_event_status {token} {eventId} {status}")
    public String changeEventStatus(@BotPathVariable("token") String token, @BotPathVariable("eventId") String eventId, @BotPathVariable("status") String status) {
        Message message = Message.builder()
                .type(Type.EVENTS_CHANGE_STATUS.name())
                .token(BEARER + token)
                .eventId(eventId)
                .status(status).build();
        return telebotService.getResult(message);
    }

    @MessageRequest("/event_marketing_report {token}")
    public String getCounterReport(@BotPathVariable("token") String token) {
        Message message = Message.builder()
                .type(Type.MONITOR_MARKETING_REPORT.name())
                .token(BEARER + token).build();
        return telebotService.getResult(message);
    }

    @MessageRequest("/options_report {token}")
    public String getOptionsReport(@BotPathVariable("token") String token) {
        Message message = Message.builder()
                .type(Type.MONITOR_OPTIONS_REPORT.name())
                .token(BEARER + token).build();
        return telebotService.getResult(message);
    }

}
