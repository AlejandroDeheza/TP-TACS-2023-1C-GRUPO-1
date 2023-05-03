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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);
    private final EventRepository eventRepository;
    private final EventOptionRepository eventOptionRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;
    private final EventOptionMapper eventOptionMapper;

    public EventDto createEvent(EventDto request) {

        User currentUser = userRepository.findByUsername(Utils.getCurrentUsername()).orElseThrow();
        request.getEventOptions().forEach(options -> options.setEventName(request.getName()));
        Set<EventOption> eventOptionSet = eventOptionMapper.dtoSetToEntitySet(request.getEventOptions());
        Set<EventOption> savedEventOptionSet = Set.copyOf(eventOptionRepository.saveAll(eventOptionSet));

        Event event = Event.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(Event.Status.VOTE_PENDING)
                .ownerUser(currentUser)
                .createDate(new Date())
                .eventOptions(savedEventOptionSet).build();

        return eventMapper.entityToDto(this.eventRepository.save(event));
    }

    public EventDto getEventById(String id) {
        Event event = getEvent(id);
        return eventMapper.entityToDto(event);
    }


    public Set<EventDto> getAllEvents() {
        return eventMapper.entitySetToDtoSet(Set.copyOf(eventRepository.findAll()));
    }

    public EventDto registerEvent(String id) {
        Event event = getEvent(id);
        User user = userRepository.findByUsername(Utils.getCurrentUsername()).orElseThrow();
        if(event.getRegisteredUsers().contains(user)) {
            throw new UserException("User already registered to the event");
        }
        event.getRegisteredUsers().add(user);

        return eventMapper.entityToDto(eventRepository.save(event));
    }

    public EventDto changeEventStatus(String id, String status) {
        Event event = getEvent(id);
        User user = userRepository.findByUsername(Utils.getCurrentUsername()).orElseThrow();
        if(!event.getOwnerUser().getUsername().equals(user.getUsername())) {
            throw new UserIsNotOwnerException("Not allowed to close the vote of event");
        }

        if (status.equals(event.getStatus().name())) {
            throw new EventStatusException("The event's has already status: " + status);
        }

        var state = Event.Status.valueOf(StringUtils.upperCase(status.trim()));
        event.setStatus(state);
        return eventMapper.entityToDto(eventRepository.save(event));
    }

    public EventDto voteEventOption(String idEvent, String idEventOption) {
        EventOption eventOption = eventOptionRepository.findById(idEventOption).orElseThrow(
                () -> new EntityNotFoundException("Event option not found")
        );

        Event event = getEvent(idEvent);
        if (Event.Status.VOTE_CLOSED == event.getStatus()) {
            throw new EventStatusException("The event's vote has already closed, not allowed to vote the event");
        }

        User user = userRepository.findByUsername(Utils.getCurrentUsername()).orElseThrow();
        eventOption.setVoteQuantity(eventOption.getVoteQuantity() + 1);
        eventOption.getVoteUsers().add(user);
        eventOption.setUpdateDate(new Date());
        eventOptionRepository.save(eventOption);
        return eventMapper.entityToDto(eventRepository.findById(idEvent).orElseThrow());
    }

    private Event getEvent(String id) {
        return eventRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Event not found")
        );
    }

}
