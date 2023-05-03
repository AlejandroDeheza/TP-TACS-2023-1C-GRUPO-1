package com.tacs.bot.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacs.bot.dto.ExceptionResponse;
import com.tacs.bot.dto.Message;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;

public class Utils {
    public static Message getMessageFromInput(String[] input) {
        Message message = new Message();
        for (String s: input) {
            String value = null;
            try{
                value = StringUtils.split(s, "=")[1];
            }
            catch (Exception e) {
                throw new RuntimeException(getErrorMessage(s) + "' value is required");
            }

            if(StringUtils.containsIgnoreCase(s,"TYPE")) {
                message.setType(StringUtils.upperCase(value));
            }
            if(StringUtils.containsIgnoreCase(s,"TOKEN")) {
                message.setToken("Bearer " + value);
            }
            if(StringUtils.containsIgnoreCase(s,"EVENT_ID")) {
                message.setEventId(value);
            }
            if(StringUtils.containsIgnoreCase(s,"OPTION_ID")) {
                message.setOptionId(value);
            }
            if(StringUtils.containsIgnoreCase(s,"STATUS")) {
                message.setStatus(value);
            }
            if(StringUtils.containsIgnoreCase(s,"BODY")) {
                message.setBody(value);
            }
        }

        return message;
    }

    public static String getErrorMessage(String s) {
        String type = null;
        if(StringUtils.containsIgnoreCase(s,"TYPE")) {
            type = "TYPE";
        }
        if(StringUtils.containsIgnoreCase(s,"TOKEN")) {
            type = "TOKEN";
        }
        if(StringUtils.containsIgnoreCase(s,"EVENT_ID")) {
            type = "EVENT_ID";
        }
        if(StringUtils.containsIgnoreCase(s,"OPTION_ID")) {
            type = "OPTION_ID";
        }
        if(StringUtils.containsIgnoreCase(s,"STATUS")) {
            type = "STATUS";
        }
        if(StringUtils.containsIgnoreCase(s,"BODY")) {
            type = "BODY";
        }
        return type;
    }

    public static String getResponseError(Response response) {
        final ResponseBody errorBody = response.errorBody();
        final String errorMessage;
        try {
            String error = Objects.isNull(errorBody) ? response.message() : errorBody.string();
            ObjectMapper mapper = new ObjectMapper();
            ExceptionResponse exceptionResponse = mapper.readValue(error, ExceptionResponse.class);
            errorMessage = exceptionResponse.getMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return errorMessage;
    }
}
