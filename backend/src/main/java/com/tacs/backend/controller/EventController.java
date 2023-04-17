package com.tacs.backend.controller;

import com.tacs.backend.dto.EventDto;
import com.tacs.backend.dto.ExceptionResponse;
import com.tacs.backend.dto.UserDto;
import com.tacs.backend.model.User;
import com.tacs.backend.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
            @ApiResponse(responseCode = "429", description = "Too many requests")

    })
    @Schema(description = "Create", implementation = EventDto.class)
    public ResponseEntity<EventDto> createEvent(@Valid @NonNull @RequestBody EventDto requestBody, HttpServletRequest request) {
        String token = getToken(request);
        return new ResponseEntity<>(eventService.createEvent(requestBody, token), HttpStatus.CREATED);
    }

    @GetMapping("/event/{id}")
    @Operation(summary = "Get an event by id", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event found successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "429", description = "Too many requests")
    })
    public ResponseEntity<EventDto> getEventById(@NotBlank @PathVariable("id") String id, HttpServletRequest request) {
        String token = getToken(request);
        return ResponseEntity.ok(this.eventService.getEventById(id, token));

    }

    @PutMapping("/event")
    @Operation(summary = "Register an user to an event", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registered successfully"),
            @ApiResponse(responseCode = "404", description = "Registration failed", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "429", description = "Too many requests")

    })
    public ResponseEntity<EventDto> registerEvent(@NotBlank  @RequestParam String eventId, HttpServletRequest request) {
        String token = getToken(request);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(this.eventService.registerEvent(eventId, token));
    }

    @PatchMapping("/event/{id}/close")
    @Operation(summary = "Close a event's voting", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event's vote closed successfully"),
            @ApiResponse(responseCode = "400", description = "Event's vote closed failed", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "Event's vote closed failed", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "429", description = "Too many requests")

    })
    public ResponseEntity<EventDto> closeEventVote(@NotBlank @PathVariable("id") String id, HttpServletRequest request) {
        String token = getToken(request);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(this.eventService.closeEventVote(id, token));
    }

    @PutMapping("/event/options/option/vote")
    @Operation(summary = "Vote a event's option", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vote successfully"),
            @ApiResponse(responseCode = "404", description = "Vote failed", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "429", description = "Too many requests")

    })
    public ResponseEntity<EventDto> voteEventOption(@NotBlank @RequestParam String idEvent, @NotBlank @RequestParam String idEventOption, HttpServletRequest request) {
        String token = getToken(request);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(this.eventService.voteEventOption(idEvent, idEventOption, token));
    }

    private String getToken(HttpServletRequest request) {
        String autorization = request.getHeader("Authorization");
        return autorization.split(" ")[1];
    }
}
