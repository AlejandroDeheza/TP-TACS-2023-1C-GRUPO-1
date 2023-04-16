package com.tacs.backend.controller;

import static com.tacs.backend.model.Event.Status.VOTE_CLOSED;
import static com.tacs.backend.model.Event.Status.VOTE_PENDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacs.backend.dto.EventDto;
import com.tacs.backend.dto.EventOptionDto;
import com.tacs.backend.dto.UserDto;
import com.tacs.backend.exception.EntityNotFoundException;
import com.tacs.backend.exception.EventStatusException;
import com.tacs.backend.exception.UserException;
import com.tacs.backend.exception.UserIsNotOwnerException;
import com.tacs.backend.service.EventService;
import com.tacs.backend.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;


@ExtendWith(MockitoExtension.class)
public class EventControllerTest {
    private MockMvc mvc;
    @Mock
    private EventService eventService;
    @Mock
    private RateLimiterService rateLimiterService;

    @InjectMocks
    private EventController eventController;
    private EventDto eventDto;
    private String idEvent;
    private String idEventOption;

    @BeforeEach
    void setup() {
        EventOptionDto eventOptionDto = EventOptionDto.builder()
                .dateTime(new Date())
                .voteQuantity(0)
                .voteUsers(new ArrayList<>())
                .build();
        Set<EventOptionDto> eventOptionDtoSet = new HashSet<>();
        eventOptionDtoSet.add(eventOptionDto);
        eventDto = EventDto.builder()
                .name("TACS")
                .description("TACS")
                .eventOptions(eventOptionDtoSet)
                .registeredUsers(new HashSet<>())
                .status(VOTE_PENDING.name())
                .build();
        idEvent = "643ac80c9093876185c40401";
        idEventOption = "643ac80c9093876185c40402";
        mvc = MockMvcBuilders.standaloneSetup(eventController)
                .setControllerAdvice(new ExceptionHandlerController())
                .build();

        when(rateLimiterService.reachedMaxRequestAllowed(anyString())).thenReturn(false);
    }




    @Test
    @DisplayName("Should return 201 when create a event")
    void itShouldReturnEventWith201StatusCodeWhenCalledCreateEvent() throws Exception {
        given(eventService.createEvent(eventDto)).willReturn(eventDto);
        HttpServletRequest request = mock(HttpServletRequest.class);

        MockHttpServletResponse response = mvc.perform(post("/v1/events")
                        .header("Authorization", "Bearer saraza123")
                        .requestAttr("javax.servlet.http.HttpServletRequest", request)
                        .content(asJsonString(eventDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(asJsonString(eventDto));
    }

    @Test
    @DisplayName("Should return 200 when get a event by id")
    void itShouldReturnEventWith200StatusCodeWhenCalledGetEventById() throws Exception {
        given(eventService.getEventById(idEvent)).willReturn(eventDto);
        HttpServletRequest request = mock(HttpServletRequest.class);

        MockHttpServletResponse response = mvc.perform(get("/v1/events/event/" + idEvent)
                        .header("Authorization", "Bearer saraza123")
                        .requestAttr("javax.servlet.http.HttpServletRequest", request)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(asJsonString(eventDto));
    }

    @Test
    @DisplayName("Should return 404 when get a event by id not exists")
    void itShouldReturnErrorWith404StatusCodeWhenCalledGetEventByIdNotExists() throws Exception {
        given(eventService.getEventById(idEvent)).willThrow(new EntityNotFoundException("Event not found"));
        HttpServletRequest request = mock(HttpServletRequest.class);

        MockHttpServletResponse response = mvc.perform(get("/v1/events/event/" + idEvent)
                        .header("Authorization", "Bearer saraza123")
                        .requestAttr("javax.servlet.http.HttpServletRequest", request)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).contains("Event not found");
    }

    @Test
    @DisplayName("Should return 200 when register to a event")
    void itShouldReturnEventWith200StatusCodeWhenCalledRegisterEvent() throws Exception {
        UserDto useDto = UserDto.builder().firstName("Juan").lastName("Perez").build();
        eventDto.getRegisteredUsers().add(useDto);
        given(eventService.registerEvent(idEvent)).willReturn(eventDto);
        HttpServletRequest request = mock(HttpServletRequest.class);

        MockHttpServletResponse response = mvc.perform(post("/v1/events/event")
                        .header("Authorization", "Bearer saraza123")
                        .requestAttr("javax.servlet.http.HttpServletRequest", request)
                        .content(idEvent)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Juan");
    }

    @Test
    @DisplayName("Should return 404 when register to a event not exists")
    void itShouldReturnErrorWith404StatusCodeWhenCalledRegisterEventNotExists() throws Exception {
        given(eventService.registerEvent(idEvent)).willThrow(new EntityNotFoundException("Event not found"));
        HttpServletRequest request = mock(HttpServletRequest.class);

        MockHttpServletResponse response = mvc.perform(post("/v1/events/event")
                        .header("Authorization", "Bearer saraza123")
                        .requestAttr("javax.servlet.http.HttpServletRequest", request)
                        .content(idEvent)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).contains("Event not found");
    }

    @Test
    @DisplayName("Should return 400 when the user already registered to a event")
    void itShouldReturnErrorWith400StatusCodeWhenCalledRegisterEventUserAlreadyRegistered() throws Exception {
        given(eventService.registerEvent(idEvent)).willThrow(new UserException("User already registered to the event"));
        HttpServletRequest request = mock(HttpServletRequest.class);

        MockHttpServletResponse response = mvc.perform(post("/v1/events/event")
                        .header("Authorization", "Bearer saraza123")
                        .requestAttr("javax.servlet.http.HttpServletRequest", request)
                        .content(idEvent)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("User already registered to the event");
    }

    @Test
    @DisplayName("Should return 200 when close a event vote")
    void itShouldReturnEventWith200StatusCodeWhenCalledCloseEventVote() throws Exception {
        eventDto.setStatus(VOTE_CLOSED.name());
        given(eventService.closeEventVote(anyString())).willReturn(eventDto);
        HttpServletRequest request = mock(HttpServletRequest.class);

        MockHttpServletResponse response = mvc.perform(patch(String.format("/v1/events/event/%s/close", idEvent))
                        .header("Authorization", "Bearer saraza123")
                        .requestAttr("javax.servlet.http.HttpServletRequest", request)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains(VOTE_CLOSED.name());
    }

    @Test
    @DisplayName("Should return 400 when close a event vote not exists")
    void itShouldReturnErrorWith400StatusCodeWhenCalledCloseEventVoteNotExists() throws Exception {
        given(eventService.closeEventVote(idEvent)).willThrow(new EntityNotFoundException("Event not found"));
        HttpServletRequest request = mock(HttpServletRequest.class);

        MockHttpServletResponse response = mvc.perform(patch(String.format("/v1/events/event/%s/close", idEvent))
                        .header("Authorization", "Bearer saraza123")
                        .requestAttr("javax.servlet.http.HttpServletRequest", request)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).contains("Event not found");
    }

    @Test
    @DisplayName("Should return 406 when a user wants to close a event vote that's not its owner")
    void itShouldReturnErrorWith406StatusCodeWhenCalledCloseEventVoteNotOwner() throws Exception {
        given(eventService.closeEventVote(idEvent)).willThrow(new UserIsNotOwnerException("Not allowed to close the vote of event"));
        HttpServletRequest request = mock(HttpServletRequest.class);

        MockHttpServletResponse response = mvc.perform(patch(String.format("/v1/events/event/%s/close", idEvent))
                        .header("Authorization", "Bearer saraza123")
                        .requestAttr("javax.servlet.http.HttpServletRequest", request)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_ACCEPTABLE.value());
        assertThat(response.getContentAsString()).contains("Not allowed to close the vote of event");
    }

    @Test
    @DisplayName("Should return 200 when voting an event option")
    void itShouldReturnEventWith200StatusCodeWhenCalledCVoteEventOption() throws Exception {
        UserDto useDto = UserDto.builder().firstName("Juan").lastName("Perez").build();
        eventDto.getEventOptions().stream().findFirst().orElseThrow().setVoteQuantity(1);
        eventDto.getEventOptions().stream().findFirst().orElseThrow().getVoteUsers().add(useDto);
        given(eventService.voteEventOption(idEvent, idEventOption)).willReturn(eventDto);
        HttpServletRequest request = mock(HttpServletRequest.class);

        MockHttpServletResponse response = mvc.perform(
                put(String.format("/v1/events/event/options/option/vote?idEvent=%s&idEventOption=%s", idEvent, idEventOption))
                        .header("Authorization", "Bearer saraza123")
                        .requestAttr("javax.servlet.http.HttpServletRequest", request)
                        .accept(MediaType.APPLICATION_JSON))

                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("1");
        assertThat(response.getContentAsString()).contains("Juan");
    }

    @Test
    @DisplayName("Should return 404 when vote event not exists")
    void itShouldReturnErrorWith400StatusCodeWhenCalledCVoteEventNotExists() throws Exception {
        given(eventService.voteEventOption(anyString(), anyString())).willThrow(new EntityNotFoundException("Event not found"));
        HttpServletRequest request = mock(HttpServletRequest.class);

        MockHttpServletResponse response = mvc.perform(
                        put(String.format("/v1/events/event/options/option/vote?idEvent=%s&idEventOption=%s", idEvent, idEventOption))
                                .header("Authorization", "Bearer saraza123")
                                .requestAttr("javax.servlet.http.HttpServletRequest", request)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).contains("Event not found");
    }

    @Test
    @DisplayName("Should return 400 when vote event option already close")
    void itShouldReturnErrorWith400StatusCodeWhenCalledCVoteEventOptionAlreadyClose() throws Exception {
        given(eventService.voteEventOption(idEvent, idEventOption)).willThrow(new EventStatusException("The event's vote has already closed, not allowed to vote the event"));
        HttpServletRequest request = mock(HttpServletRequest.class);

        MockHttpServletResponse response = mvc.perform(
                        put(String.format("/v1/events/event/options/option/vote?idEvent=%s&idEventOption=%s", idEvent, idEventOption))
                                .header("Authorization", "Bearer saraza123")
                                .requestAttr("javax.servlet.http.HttpServletRequest", request)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("The event's vote has already closed, not allowed to vote the event");
    }

    @Test
    @DisplayName("Should return 404 when vote event option not exists")
    void itShouldReturnErrorWith400StatusCodeWhenCalledCVoteEventOptionNotExists() throws Exception {
        given(eventService.voteEventOption(idEvent, idEventOption)).willThrow(new EntityNotFoundException("Event option not found"));
        HttpServletRequest request = mock(HttpServletRequest.class);

        MockHttpServletResponse response = mvc.perform(
                        put(String.format("/v1/events/event/options/option/vote?idEvent=%s&idEventOption=%s", idEvent, idEventOption))
                                .header("Authorization", "Bearer saraza123")
                                .requestAttr("javax.servlet.http.HttpServletRequest", request)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).contains("Event option not found");
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
