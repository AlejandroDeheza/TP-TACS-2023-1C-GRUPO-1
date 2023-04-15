package com.tacs.backend.controller;

import com.tacs.backend.dto.EventDto;
import com.tacs.backend.dto.ExceptionResponse;
import com.tacs.backend.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping
    @Operation(summary = "Create a new event", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event created successfully"),
            @ApiResponse(responseCode = "400", description = "Event created failed", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @Schema(description = "Create", implementation = EventDto.class)
    public ResponseEntity<EventDto> createEvent(@Valid @NonNull @RequestBody EventDto request) {
        return new ResponseEntity<>(eventService.createEvent(request), HttpStatus.CREATED);
    }

    @GetMapping("/event/{id}")
    @Operation(summary = "Find a event by id", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event created successfully"),
            @ApiResponse(responseCode = "400", description = "Event created failed", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    public ResponseEntity<EventDto> getEventById(@NotBlank @PathVariable("id") String id) {
       return ResponseEntity.ok(this.eventService.getEventById(id));
    }

    @GetMapping("/event")
    @Operation(summary = "Register to a event", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register successfully"),
            @ApiResponse(responseCode = "400", description = "Registration failed", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    public ResponseEntity<EventDto> registerEvent(@NotBlank @RequestParam String id) {
        return ResponseEntity.ok(this.eventService.registerEvent(id));
    }

}
