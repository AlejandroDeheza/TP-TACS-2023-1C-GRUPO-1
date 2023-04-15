package com.tacs.backend.service;


import com.tacs.backend.dto.EventDto;
import com.tacs.backend.exception.EntityNotFoundException;
import com.tacs.backend.mapper.EventMapper;
import com.tacs.backend.mapper.EventOptionMapper;
import com.tacs.backend.model.Event;

import com.tacs.backend.model.EventOption;
import com.tacs.backend.model.User;
import com.tacs.backend.repository.EventOptionRepository;
import com.tacs.backend.repository.EventRepository;
import com.tacs.backend.repository.UserRepository;
import com.tacs.backend.utils.Utils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventOptionRepository eventOptionRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;
    private final EventOptionMapper eventOptionMapper;

    public EventDto createEvent(EventDto request) {
        User currentUser = userRepository.findByUsername(Utils.getCurrentUsername()).orElseThrow();
        Set<EventOption> eventOptionSet = eventOptionMapper.dtoSetToEntitySet(request.getEventOptions());
        Set<EventOption> savedEventOptionSet = Set.copyOf(eventOptionRepository.saveAll(eventOptionSet));

        Event event = Event.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(Event.Status.VOTATION_PENDING)
                .ownerUser(currentUser)
                .eventOptions(savedEventOptionSet).build();

        return eventMapper.entityToDto(this.eventRepository.save(event));
    }

    public EventDto getEventById(String id) {
        Event event = eventRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Event not found")
        );
        return eventMapper.entityToDto(event);
    }

    public EventDto registerEvent(String id) {
        Event event = eventRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Event not found")
        );
        User user = userRepository.findByUsername(Utils.getCurrentUsername()).orElseThrow();
        event.getRegisteredUsers().add(user);

        return eventMapper.entityToDto(eventRepository.save(event));
    }
}
