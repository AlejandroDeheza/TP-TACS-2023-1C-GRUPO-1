package com.tacs.bot.service;

import com.tacs.bot.dto.*;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;


import java.util.List;
import java.util.Set;


public interface ApiManager {

    @Headers("Content-Type: application/json")
    @POST("/v1/auth/authentication")
    Call<Object> authenticate(@Body RequestBody body);

    @Headers("Content-Type: application/json")
    @POST("/v1/auth/register")
    Call<Object> register(@Body RequestBody body);

    @Headers("Content-Type: application/json")
    @POST("/v1/events")
    Call<EventDto> createEvent(@Header("Authorization") String authHeader, @Body RequestBody body);

    @Headers({"Content-Type: application/json"})
    @GET("/v1/events")
    Call<Set<EventDto>> getAllEvents(@Header("Authorization") String authHeader);

    @Headers("Content-Type: application/json")
    @GET("/v1/events/{id}")
    Call<EventDto> getEventById(@Header("Authorization") String authHeader, @Path("id") String id);

    @Headers("Content-Type: application/json")
    @PATCH("/v1/events/{id}/user")
    Call<EventDto> registerEvent(@Header("Authorization") String authHeader, @Path("id") String id);

    @Headers("Content-Type: application/json")
    @PATCH("/v1/events/{eventId}")
    Call<EventDto> changeEventStatus(@Header("Authorization") String authHeader, @Path("eventId") String eventId, @Query("status") String status);

    @Headers("Content-Type: application/json")
    @PATCH("/v1/events/{eventId}/options/{optionId}/vote")
    Call<EventDto> voteEventOption(@Header("Authorization") String authHeader, @Path("eventId") String eventId, @Path("optionId") String optionId);

    @Headers("Content-Type: application/json")
    @GET("/v1/monitor/ratios")
    Call<MarketingReportDto> getCounterReport(@Header("Authorization") String authHeader);

    @Headers("Content-Type: application/json")
    @GET("/v1/monitor/options")
    Call<List<EventOptionReportDto>> getOptionsReport(@Header("Authorization") String authHeader);
}
