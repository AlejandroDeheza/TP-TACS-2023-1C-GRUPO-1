package com.tacs.backend.service;


import com.mongodb.MongoException;
import com.tacs.backend.dto.EventDto;
import com.tacs.backend.dto.EventOptionDto;
import com.tacs.backend.dto.EventRequest;
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
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventOptionRepository eventOptionRepository;
    private final EventMapper eventMapper;
    private final EventOptionMapper eventOptionMapper;
    private final UserRepository userRepository;

    public EventDto createEvent(EventRequest request) {
        User user = userRepository.findByUsername(Utils.getCurrentUsername()).orElseThrow();

        Set<EventOptionDto> eventOptionDtoSet = request.getEventOptions().stream().map(r ->
            EventOptionDto.builder().date(r.getDate()).time(r.getTime()).build()).collect(Collectors.toSet());
        Set<EventOption> eventOptionSet = eventOptionMapper.dtoSetToEntitySet(eventOptionDtoSet);
        Set<EventOption> savedEventOptionSet = Set.copyOf(eventOptionRepository.saveAll(eventOptionSet));

        Event event = Event.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(Event.Status.VOTATION_PENDING)
                .ownerUser(user)
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
