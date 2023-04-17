package com.tacs.backend.repository;

import com.tacs.backend.model.Event;
import com.tacs.backend.model.EventOption;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventOptionRepository extends MongoRepository<EventOption, String> ,EventOptionRepositoryCustom{
}
