package com.tacs.bot.service;

import com.tacs.bot.dto.*;
import com.tacs.bot.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitorApi implements ApiService {
    private final ApiManager apiManager;


    @Override
    public Object authenticate(Message message) {
        return null;
    }

    @Override
    public Object register(Message message) {
        return null;
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

    public Object getCounterReport(Message message) {
        Call<MarketingReportDto> request = apiManager.getCounterReport(message.getToken());
        Response<MarketingReportDto> response = null;
        try {
            response = request.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.isSuccessful() ? response.body() : Utils.getResponseError(response);
    }

    public Object getOptionsReport(Message message) {
        Call<List<EventOptionReportDto>> request = apiManager.getOptionsReport(message.getToken());
        Response<List<EventOptionReportDto>> response = null;
        try {
            response = request.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.isSuccessful() ? response.body(): Utils.getResponseError(response);
    }


}
