package com.tacs.backend.repository;

import com.tacs.backend.model.Event;

import java.util.List;

public interface EventRepositoryCustom {
    List<Event> findLastCreatedEvents(int milliseconds);
}
