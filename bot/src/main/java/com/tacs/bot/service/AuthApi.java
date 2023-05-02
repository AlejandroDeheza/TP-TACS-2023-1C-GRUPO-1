package com.tacs.bot.service;

import com.tacs.bot.dto.*;
import com.tacs.bot.utils.Utils;
import lombok.*;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;


import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthApi implements ApiService{
    private final ApiManager apiManager;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");


    public Object authenticate(Message message) {
        Response<Object> response;
        RequestBody requestBody = RequestBody.create(message.getBody(), JSON);
        try {
            Call<Object> request = apiManager.authenticate(requestBody);
            response = request.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.isSuccessful() ? response.body(): Utils.getResponseError(response);
    }

    public Object register(Message message) {
        Response<Object> response;
        RequestBody requestBody = RequestBody.create(message.getBody(), JSON);
        try {
            Call<Object> request = apiManager.register(requestBody);
            response = request.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.isSuccessful() ? response.body(): Utils.getResponseError(response);
    }

    @Override
    public Object getAllEvent(Message message) {
        return null;
    }

    @Override
    public Object createEvent(Message message) {
        return null;
    }

    @Override
    public Object getEventById(Message message) {
        return null;
    }

    @Override
    public Object registerEvent(Message message) {
        return null;
    }

    @Override
    public Object changeEventStatus(Message message) {
        return null;
    }

    @Override
    public Object getCounterReport(Message message) {
        return null;
    }

    @Override
    public Object getOptionsReport(Message message) {
        return null;
    }


}
