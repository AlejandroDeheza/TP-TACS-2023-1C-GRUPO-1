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
import java.util.Set;


@Service
@RequiredArgsConstructor
public class EventApi implements ApiService{
    private final ApiManager apiManager;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Override
    public Object authenticate(Message message) {
        return null;
    }

    @Override
    public Object register(Message message) {
        return null;
    }

    public Object getAllEvent(Message message)  {

        Response<Object> response = null;
        try {
            Call<Object> request = apiManager.getAllEvents(message.getToken());
            response = request.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.isSuccessful() ? response.body(): Utils.getResponseError(response);
    }

    public Object createEvent(Message message) {
        Response<Object> response = null;
        RequestBody requestBody = RequestBody.create(message.getBody(), JSON);
        try {
            Call<Object> request = apiManager.createEvent(message.getToken(), requestBody);
            response = request.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.isSuccessful() ? response.body(): Utils.getResponseError(response);
    }

    public Object getEventById(Message message) {
        Call<Object> request = apiManager.getEventById(message.getToken(), message.getEventId());
        Response<Object> response = null;
        try {
            response = request.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.isSuccessful() ? response.body(): Utils.getResponseError(response);
    }

    public Object registerEvent(Message message) {
        Call<Object> request = apiManager.registerEvent(message.getToken(), message.getEventId());
        Response<Object> response;
        try {
            response = request.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.isSuccessful() ? response.body(): Utils.getResponseError(response);
    }

    public Object voteEventOption(Message message) {
        Call<Object> request = apiManager.voteEventOption(message.getToken(), message.getEventId(), message.getOptionId());
        Response<Object> response = null;
        try {
            response = request.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.isSuccessful() ? response.body(): Utils.getResponseError(response);
    }

    public Object changeEventStatus(Message message)  {
        Call<Object> request = apiManager.changeEventStatus(message.getToken(), message.getEventId(), message.getStatus());
        Response<Object> response = null;
        try {
            response = request.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.isSuccessful() ? response.body(): Utils.getResponseError(response);
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
