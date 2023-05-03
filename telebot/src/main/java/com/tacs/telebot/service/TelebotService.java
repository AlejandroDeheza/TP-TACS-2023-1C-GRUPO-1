package com.tacs.telebot.service;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.tacs.telebot.dto.Message;
import com.tacs.telebot.validator.MessageValidatorChain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelebotService {
    private final ObjectWriter objectWriter;
    private final ApiFactory apiFactory;

    public String getResult(Message message)  {
        String result = null;
        try {
            MessageValidatorChain.validate(message);
            Object response = apiFactory.getApiService(message.getType()).apply(message);
            result = response.getClass().equals(String.class) ? (String) response : objectWriter.writeValueAsString(response);
            result = result.getBytes().length > 4096 ? "Message is too long" : result;
        }
        catch(Exception e) {
            return e.getMessage();
        }

        return result;
    }

}
