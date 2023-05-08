package com.tacs.backend.repository;

import com.tacs.backend.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, String>, EventRepositoryCustom {
}
