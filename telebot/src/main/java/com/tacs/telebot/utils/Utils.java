package com.tacs.telebot.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacs.telebot.dto.*;

import okhttp3.ResponseBody;

import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Utils {
    
    public static final Map<String, Class> CLASS_MAP = new HashMap<>();
    static {
        CLASS_MAP.put(Type.EVENTS_CREATE.name(), Event.class);
        CLASS_MAP.put(Type.AUTH_AUTHENTICATION.name(), Authentication.class);
        CLASS_MAP.put(Type.AUTH_REGISTER.name(), Register.class);
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

    public static boolean isBodyValid(Message message) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readValue(message.getBody(), CLASS_MAP.get(message.getType()));
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
