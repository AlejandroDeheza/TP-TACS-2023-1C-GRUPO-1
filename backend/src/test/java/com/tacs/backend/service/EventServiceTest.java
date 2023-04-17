package com.tacs.backend.service;

import com.tacs.backend.dto.EventDto;
import com.tacs.backend.dto.EventOptionDto;
import com.tacs.backend.dto.UserDto;
import com.tacs.backend.exception.RequestNotAllowException;
import com.tacs.backend.mapper.EventMapper;
import com.tacs.backend.mapper.EventMapperImpl;
import com.tacs.backend.mapper.EventOptionMapper;
import com.tacs.backend.mapper.EventOptionMapperImpl;
import com.tacs.backend.model.Role;
import com.tacs.backend.model.User;
import com.tacs.backend.repository.EventOptionRepository;
import com.tacs.backend.repository.EventRepository;
import com.tacs.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

  @Mock
  private EventRepository eventRepository;
  @Mock
  private EventOptionRepository eventOptionRepository;
  @Mock
  private UserRepository userRepository;
  private EventMapper eventMapper = new EventMapperImpl();
  private EventOptionMapper eventOptionMapper = new EventOptionMapperImpl();
  @Mock
  private RateLimiterService rateLimiterService;

  private EventService eventService;
  private EventDto eventDto;
  private String stringToken = "jwtToken";
  private EventOptionDto eventOptionDto;
  private EventOptionDto eventOptionDto2;
  private Set<EventOptionDto> eventOptionDtoSet;

  private UserDto userDto;
  private User user1;
  private User user2;
  private List<User> users;

  @BeforeEach
  void setup() {
    eventService =
        new EventService(
            eventRepository,
            eventOptionRepository,
            userRepository,
            eventMapper,
            eventOptionMapper,
            rateLimiterService
        );
    user1 = User.builder()
        .id("idididiidid")
        .firstName("Facundo")
        .lastName("Perez")
        .username("123Perez")
        .password("UnaContraseña198!")
        .role(Role.USER)
        .build();
    user2 = User.builder()
        .id("idididiidid1")
        .firstName("Marcos")
        .lastName("Gonzalez")
        .username("123Gonzalez")
        .password("UnaContraseña198!")
        .role(Role.USER)
        .build();
    users = Arrays.asList(user1, user2);

    eventOptionDto = EventOptionDto.builder()
        .id("idididididid2")
        .dateTime(Date.valueOf(LocalDate.now().plusDays(3)))
        .voteQuantity(0)
        .voteUsers(null)
        .build();
    eventOptionDto2 = EventOptionDto.builder()
        .id("idididididid3")
        .dateTime(Date.valueOf(LocalDate.now().plusDays(4)))
        .voteQuantity(0)
        .voteUsers(null)
        .build();
    eventOptionDtoSet = Set.of(eventOptionDto, eventOptionDto2);
    userDto = UserDto.builder()
        .id("idididiidid0")
        .firstName("Raul")
        .lastName("Flores")
        .build();
    eventDto = EventDto.builder()
        .id("idididididid4")
        .name("unEvento")
        .description("descripcion")
        .status("VOTE_PENDING")
        .eventOptions(eventOptionDtoSet)
        .ownerUser(userDto)
        .registeredUsers(Set.of())
        .build();
  }

  @Test
  @DisplayName("...")
  void reachedMaximumRequestTest() {
    Mockito.when(rateLimiterService.reachedMaxRequestAllowed(stringToken)).thenReturn(true);
    assertThrows(RequestNotAllowException.class, () -> eventService.getEventById("ididid", stringToken));
    assertThrows(RequestNotAllowException.class, () -> eventService.registerEvent("ididid", stringToken));
    assertThrows(RequestNotAllowException.class, () -> eventService.closeEventVote("ididid", stringToken));
    assertThrows(RequestNotAllowException.class,
        () -> eventService.voteEventOption("ididid", "ididid2", stringToken)
    );
  }

  

}
