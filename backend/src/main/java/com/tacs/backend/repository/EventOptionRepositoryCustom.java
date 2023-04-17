package com.tacs.backend.repository;

import com.tacs.backend.model.Event;
import com.tacs.backend.model.EventOption;

import java.util.List;

public interface EventOptionRepositoryCustom {
    List<EventOption> getLastVotedEventOptions(int timeRange);
}
