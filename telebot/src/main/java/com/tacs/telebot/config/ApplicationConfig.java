package com.tacs.telebot.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tacs.telebot.service.ApiManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    @Value("${backend.baseUrl}")
    private String baseUrl;
    @Bean
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Bean
    ApiManager apiManager(Retrofit retrofit) {
        return retrofit.create(ApiManager.class);
    }

    @Bean
    public ObjectWriter objectWriter() {
        return new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
