package com.tacs.backend.service;


import com.tacs.backend.dto.EventDto;
import com.tacs.backend.exception.*;
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


@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventOptionRepository eventOptionRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;
    private final EventOptionMapper eventOptionMapper;
    private final RateLimiterService rateLimiterService;
    private final Utils utils;

    public EventDto createEvent(EventDto request, String token) {

        reachedMaximumRequest(token);
        User currentUser = userRepository.findByUsername(utils.getCurrentUsername()).orElseThrow();
        Set<EventOption> eventOptionSet = eventOptionMapper.dtoSetToEntitySet(request.getEventOptions());
        Set<EventOption> savedEventOptionSet = Set.copyOf(eventOptionRepository.saveAll(eventOptionSet));

        Event event = Event.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(Event.Status.VOTE_PENDING)
                .ownerUser(currentUser)
                .eventOptions(savedEventOptionSet).build();

        return eventMapper.entityToDto(this.eventRepository.save(event));
    }

    public EventDto getEventById(String id, String token) {
        reachedMaximumRequest(token);
        Event event = getEvent(id);
        return eventMapper.entityToDto(event);
    }

    public EventDto registerEvent(String id, String token) {
        reachedMaximumRequest(token);
        Event event = getEvent(id);
        User user = userRepository.findByUsername(utils.getCurrentUsername()).orElseThrow();
        if(event.getRegisteredUsers().contains(user)) {
            throw new UserException("User already registered to the event");
        }
        event.getRegisteredUsers().add(user);

        return eventMapper.entityToDto(eventRepository.save(event));
    }

    public EventDto closeEventVote(String id, String token) {
        reachedMaximumRequest(token);
        Event event = getEvent(id);
        User user = userRepository.findByUsername(utils.getCurrentUsername()).orElseThrow();
        if(!event.getOwnerUser().getUsername().equals(user.getUsername())) {
            throw new UserIsNotOwnerException("Not allowed to close the vote of event");
        }

        event.setStatus(Event.Status.VOTE_CLOSED);
        return eventMapper.entityToDto(eventRepository.save(event));
    }

    public EventDto voteEventOption(String idEvent, String idEventOption, String token) {
        reachedMaximumRequest(token);
        EventOption eventOption = eventOptionRepository.findById(idEventOption).orElseThrow(
                () -> new EntityNotFoundException("Event option not found")
        );

        Event event = getEvent(idEvent);
        if (Event.Status.VOTE_CLOSED == event.getStatus()) {
            throw new EventStatusException("The event's vote has already closed, not allowed to vote the event");
        }


        User user = userRepository.findByUsername(utils.getCurrentUsername()).orElseThrow();
        eventOption.setVoteQuantity(eventOption.getVoteQuantity() + 1);
        eventOption.getVoteUsers().add(user);
        eventOptionRepository.save(eventOption);
        return eventMapper.entityToDto(eventRepository.findById(idEvent).orElseThrow());
    }


    private Event getEvent(String id) {
        return eventRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Event not found")
        );
    }

    private void reachedMaximumRequest(String token) {
        boolean reachedMaxRequestAllowed = rateLimiterService.reachedMaxRequestAllowed(token);
        if (reachedMaxRequestAllowed) {
            throw new RequestNotAllowException("User reached maximum number of request for applicacion. Try again in a while.");
        }
    }
}
