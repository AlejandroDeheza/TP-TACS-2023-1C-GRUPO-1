package com.tacs.backend.repository.impl;

import com.tacs.backend.model.Event;
import com.tacs.backend.model.EventOption;
import com.tacs.backend.repository.EventOptionRepository;
import com.tacs.backend.repository.EventOptionRepositoryCustom;
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
public class EventOptionRepositoryImpl implements EventOptionRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<EventOption> getLastVotedEventOptions() {
        Query query = new Query();
        query.addCriteria(Criteria.where("update_time").gt(Utils.getBeforeDate()));
        return mongoTemplate.find(query, EventOption.class);
    }
}
