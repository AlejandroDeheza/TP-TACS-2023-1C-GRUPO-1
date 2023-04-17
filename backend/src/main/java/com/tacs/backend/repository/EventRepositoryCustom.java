package com.tacs.backend.repository;

import com.tacs.backend.model.Event;

import java.util.List;

public interface EventRepositoryCustom {
    long getLastCreatedEventsCount(int timeRange);

    List<Event> getLastCreatedEvents();
}
