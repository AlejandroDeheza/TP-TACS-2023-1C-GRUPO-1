package com.tacs.backend.repository.impl;

import com.tacs.backend.model.Event;
import com.tacs.backend.repository.EventRepositoryCustom;
import com.tacs.backend.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public long getLastCreatedEventsCount() {

        Query query = new Query();
        query.addCriteria(Criteria.where("create_time").gt(Utils.getBeforeDate()));
        return mongoTemplate.count(query, Event.class);
    }

    @Override
    public List<Event> getLastCreatedEvents() {
        return null;
    }
}
