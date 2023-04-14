package com.tacs.backend.controller;

import com.tacs.backend.dto.EventDto;
import com.tacs.backend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/events")
public class EventController {
    private EventService eventService;

   @Autowired
    public EventController (EventService theEventService) {
        this.eventService = theEventService;
    }

    @PostMapping
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto) {
        EventDto newEvent = null;
       try {
           newEvent = eventService.createEvent(eventDto);
        } catch (Exception e) {
            //TO DO handlear exception
        }

        return ResponseEntity.ok(newEvent);
    }

    @GetMapping("/evens/event")
    public ResponseEntity<EventDto> getEventById(@RequestParam Long id) {
        Assert.notNull(id, "The id parameter cannot be null");
        EventDto eventDto = null;
        try {
            eventDto = this.eventService.getEventById(id);
        } catch (Exception e) {
            //Handlear exception
        }

       return ResponseEntity.ok(eventDto);
    }

}
