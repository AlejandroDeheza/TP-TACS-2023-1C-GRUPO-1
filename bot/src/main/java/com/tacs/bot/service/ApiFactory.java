package com.tacs.bot.service;


import com.tacs.bot.dto.Message;
import com.tacs.bot.enums.Type;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;


@Component
@RequiredArgsConstructor
public class ApiFactory {
    private final EventApi eventApi;
    private final AuthApi authApi;
    private final MonitorApi monitorApi;
    private Map<String, Function> apiMethodsMap;

    @PostConstruct
    public void init() {
        apiMethodsMap = new HashMap<>();
        apiMethodsMap.put(Type.EVENTS_ALL.name(), m -> eventApi.getAllEvent((Message) m));
        apiMethodsMap.put(Type.EVENTS_BY_ID.name(), m -> eventApi.getEventById((Message) m));
        apiMethodsMap.put(Type.EVENTS_CREATE.name(), m -> eventApi.createEvent((Message) m));
        apiMethodsMap.put(Type.EVENTS_REGISTER.name(), m -> eventApi.registerEvent((Message) m));
        apiMethodsMap.put(Type.EVENTS_CHANGE_STATUS.name(), m -> eventApi.changeEventStatus((Message) m));
        apiMethodsMap.put(Type.EVENTS_VOTE.name(), m -> eventApi.voteEventOption((Message) m));
        apiMethodsMap.put(Type.AUTH_REGISTER.name(), m -> authApi.authenticate((Message) m));
        apiMethodsMap.put(Type.AUTH_REGISTER.name(), m -> eventApi.register((Message) m));
        apiMethodsMap.put(Type.MONITOR_MARKETING_REPORT.name(), m -> monitorApi.getCounterReport((Message) m));
        apiMethodsMap.put(Type.MONITOR_OPTIONS_REPORT.name(), m -> eventApi.getOptionsReport((Message) m));
    }

    public Function getApiService(String type){
        return Optional.ofNullable(this.apiMethodsMap.get(type))
                .orElseThrow(() -> new IllegalArgumentException("Invalid Service Type"));
    }
}
