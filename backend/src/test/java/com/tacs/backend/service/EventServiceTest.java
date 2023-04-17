package com.tacs.backend.service;

import com.tacs.backend.dto.EventDto;
import com.tacs.backend.dto.EventOptionDto;
import com.tacs.backend.dto.UserDto;
import com.tacs.backend.exception.EntityNotFoundException;
import com.tacs.backend.exception.RequestNotAllowException;
import com.tacs.backend.exception.UserException;
import com.tacs.backend.mapper.EventMapper;
import com.tacs.backend.mapper.EventMapperImpl;
import com.tacs.backend.mapper.EventOptionMapper;
import com.tacs.backend.mapper.EventOptionMapperImpl;
import com.tacs.backend.model.Event;
import com.tacs.backend.model.EventOption;
import com.tacs.backend.model.Role;
import com.tacs.backend.model.User;
import com.tacs.backend.repository.EventOptionRepository;
import com.tacs.backend.repository.EventRepository;
import com.tacs.backend.repository.UserRepository;
import com.tacs.backend.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
  @Mock
  private Utils utils;

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
  private User user0;

  @BeforeEach
  void setup() {
    eventService =
        new EventService(
            eventRepository,
            eventOptionRepository,
            userRepository,
            eventMapper,
            eventOptionMapper,
            rateLimiterService,
            utils
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
    user0 = User.builder()
        .id("idididiidi0")
        .firstName("Raul")
        .lastName("Flores")
        .username("123Flores")
        .password("UnaContraseña198!")
        .role(Role.USER)
        .build();
  }

  @Test
  @DisplayName("...")
  void reachedMaximumRequestTest() {
    Mockito.when(rateLimiterService.reachedMaxRequestAllowed(stringToken)).thenReturn(true);
    assertThrows(RequestNotAllowException.class, () -> eventService.createEvent(eventDto, stringToken));
    assertThrows(RequestNotAllowException.class, () -> eventService.getEventById("ididid", stringToken));
    assertThrows(RequestNotAllowException.class, () -> eventService.registerEvent("ididid", stringToken));
    assertThrows(RequestNotAllowException.class, () -> eventService.closeEventVote("ididid", stringToken));
    assertThrows(RequestNotAllowException.class,
        () -> eventService.voteEventOption("ididid", "ididid2", stringToken)
    );
  }

  @Test
  @DisplayName("...")
  void createEventTest(){
    Mockito.when(rateLimiterService.reachedMaxRequestAllowed(stringToken)).thenReturn(false);
    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(user0));

    Set<EventOption> eventOptionSet = eventOptionMapper.dtoSetToEntitySet(eventDto.getEventOptions());
    var eventOption = eventOptionMapper.dtoToEntity(eventOptionDto);
    var eventOption2 = eventOptionMapper.dtoToEntity(eventOptionDto2);
    Mockito.when(eventOptionRepository.saveAll(eventOptionSet)).thenReturn(Arrays.asList(eventOption, eventOption2));

    eventService.createEvent(eventDto, stringToken);

    Mockito.verify(userRepository).findByUsername(Mockito.any());
    Mockito.verify(eventOptionRepository).saveAll(eventOptionSet);
    Mockito.verify(eventRepository).save(Mockito.any());
    Mockito.verify(utils).getCurrentUsername();
  }

  @Test
  @DisplayName("...")
  void getEventTest(){
    Mockito.when(rateLimiterService.reachedMaxRequestAllowed(stringToken)).thenReturn(false);
    Mockito.when(eventRepository.findById("ididid")).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class,
        () -> eventService.getEventById("ididid", stringToken)
    );
  }

  @Test
  @DisplayName("...")
  void getEventByIdTest(){
    Mockito.when(rateLimiterService.reachedMaxRequestAllowed(stringToken)).thenReturn(false);
    Mockito.when(eventRepository.findById("ididid")).thenReturn(Optional.of(eventMapper.dtoToEntity(eventDto)));

    eventService.getEventById("ididid", stringToken);

    Mockito.verify(eventRepository).findById("ididid");
  }

  @Test
  @DisplayName("...")
  void registerEvent(){
    Mockito.when(rateLimiterService.reachedMaxRequestAllowed(stringToken)).thenReturn(false);
    Event event = eventMapper.dtoToEntity(eventDto);
    event.setRegisteredUsers(Set.of(user1));
    Mockito.when(eventRepository.findById("ididid")).thenReturn(Optional.of(event));
    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(user1));

    assertThrows(UserException.class, () -> eventService.registerEvent("ididid", stringToken));
    Mockito.verify(userRepository).findByUsername(Mockito.any());
    Mockito.verify(utils).getCurrentUsername();
  }

}
