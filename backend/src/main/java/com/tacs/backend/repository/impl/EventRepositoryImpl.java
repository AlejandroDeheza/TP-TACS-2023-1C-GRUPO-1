package com.tacs.backend.repository.impl;

import com.tacs.backend.model.Event;
import com.tacs.backend.repository.EventRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Event> findLastCreatedEvents(int milliseconds) {
        /*
        Query query = new Query();
        query.addCriteria(Criteria.where("date")
                .gt(1));
        return mongoTemplate.find(query, Event.class);
        */

        return mongoTemplate.findAll(Event.class);

    }
}
