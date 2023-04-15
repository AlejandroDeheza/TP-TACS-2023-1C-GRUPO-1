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

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(this.eventService.registerEvent(id));
    }

    @DeleteMapping("/event/{id}/vote")
    @Operation(summary = "Close a event's vote", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event's vote closed successfully"),
            @ApiResponse(responseCode = "400", description = "Event's vote closed failed", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "Event's vote closed failed", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    public ResponseEntity<EventDto> closeEventVote(@NotBlank @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(this.eventService.closeEventVote(id));
    }

    @GetMapping("/event/options/option/vote")
    @Operation(summary = "Vote a event's option", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vote successfully"),
            @ApiResponse(responseCode = "400", description = "Vote failed", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    public ResponseEntity<EventDto> voteEventOption(@NotBlank @RequestParam String idEvent, @NotBlank @RequestParam String idEventOption) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(this.eventService.voteEventOption(idEvent, idEventOption));
    }

}
