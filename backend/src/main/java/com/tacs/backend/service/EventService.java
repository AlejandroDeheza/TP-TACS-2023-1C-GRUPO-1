package com.tacs.backend.service;

import com.tacs.backend.dao.EventRepository;
import com.tacs.backend.dto.EventDto;
import com.tacs.backend.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private EventRepository eventRepository;
    @Autowired
    public EventService(EventRepository theEventRepository) {
        this.eventRepository = theEventRepository;
    }
    public EventDto createEvent(EventDto eventToSave) {
        Event event = Event.builder()
                .description(eventToSave.getDescription())
                .status(Event.Status.VOTATION_PENDING)
                .build();
        
        return this.eventRepository.save(event).transformToDto();
    }

    public EventDto getEventById(long id) {
        return null;
    }
}
