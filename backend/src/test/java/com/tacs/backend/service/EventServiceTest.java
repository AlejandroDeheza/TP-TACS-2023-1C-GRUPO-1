package com.tacs.backend.service;

import com.tacs.backend.dto.EventDto;
import com.tacs.backend.dto.EventOptionDto;
import com.tacs.backend.dto.UserDto;
import com.tacs.backend.exception.EntityNotFoundException;
import com.tacs.backend.exception.EventStatusException;
import com.tacs.backend.exception.UserException;
import com.tacs.backend.exception.UserIsNotOwnerException;
import com.tacs.backend.mapper.EventMapper;
import com.tacs.backend.mapper.EventOptionMapper;
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
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

  @Mock
  private EventRepository eventRepository;
  @Mock
  private EventOptionRepository eventOptionRepository;
  @Mock
  private UserRepository userRepository;
  @Mock
  private EventMapper eventMapper;
  @Mock
  private EventOptionMapper eventOptionMapper;

  @InjectMocks
  private EventService eventService;

  private EventDto eventDto;
  private EventOptionDto eventOptionDto;
  private EventOptionDto eventOptionDto2;
  private Set<EventOptionDto> eventOptionDtoSet;
  private EventOption eventOption;
  private Event event;

  private UserDto userDto;
  private User user1;
  private User user2;
  private List<User> users;
  private User user0;

  @BeforeEach
  void setup() {
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
        .voteUsers(List.of())
        .build();
    eventOptionDto2 = EventOptionDto.builder()
        .id("idididididid3")
        .dateTime(Date.valueOf(LocalDate.now().plusDays(4)))
        .voteQuantity(0)
        .voteUsers(List.of())
        .build();
    eventOptionDtoSet = Set.of(eventOptionDto, eventOptionDto2);
    eventOption = EventOption.builder()
        .id("idididididid2")
        .event(null)
        .dateTime(Date.valueOf(LocalDate.now().plusDays(3)))
        .voteUsers(new ArrayList<>())
        .voteQuantity(0)
        .updateDate(Date.valueOf(LocalDate.now()))
        .build();
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
        .id("idididiidid0")
        .firstName("Raul")
        .lastName("Flores")
        .username("123Flores")
        .password("UnaContraseña198!")
        .role(Role.USER)
        .build();
    event = Event.builder()
        .id("idididididid4")
        .name("unEvento")
        .description("descripcion")
        .ownerUser(user0)
        .status(Event.Status.VOTE_PENDING)
        .eventOptions(null)
        .registeredUsers(new HashSet<>())
        .createDate(Date.valueOf(LocalDate.now()))
        .build();
  }

  @Test
  @DisplayName("Should be created correctly an event")
  void createEventTest(){
    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(user0));

    try (MockedStatic<Utils> utilities = Mockito.mockStatic(Utils.class)) {
      utilities.when(Utils::getCurrentUsername).thenReturn("username");
      assertDoesNotThrow(() -> eventService.createEvent(eventDto));
    }
    Mockito.verify(userRepository).findByUsername(Mockito.any());
    Mockito.verify(eventMapper).entityToDto(Mockito.any());
    Mockito.verify(eventOptionMapper).dtoSetToEntitySet(eventDto.getEventOptions());
    Mockito.verify(eventOptionRepository).saveAll(Mockito.any());
    Mockito.verify(eventRepository).save(Mockito.any());

  }

  @Test
  @DisplayName("Should throw exception when Event does not exist")
  void getEventTest(){
    Mockito.when(eventRepository.findById("ididid")).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> eventService.getEventById("ididid"));
    Mockito.verify(eventRepository).findById("ididid");
  }

  @Test
  @DisplayName("Should get an existing event correctly")
  void getEventByIdTest(){
    Mockito.when(eventRepository.findById("ididid")).thenReturn(Optional.of(event));
    assertDoesNotThrow(() -> eventService.getEventById("ididid"));
    Mockito.verify(eventRepository).findById("ididid");
    Mockito.verify(eventMapper).entityToDto(event);
  }

  @Test
  @DisplayName("Should throw exception when the User is already registered for the Event")
  void registerEventTest(){
    event.setRegisteredUsers(Set.of(user1));
    Mockito.when(eventRepository.findById("ididid")).thenReturn(Optional.of(event));
    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(user1));

    try (MockedStatic<Utils> utilities = Mockito.mockStatic(Utils.class)) {
      utilities.when(Utils::getCurrentUsername).thenReturn("username");
      assertThrows(UserException.class, () -> eventService.registerEvent("ididid"));
    }
    Mockito.verify(eventRepository).findById("ididid");
    Mockito.verify(userRepository).findByUsername(Mockito.any());
  }

  @Test
  @DisplayName("Should register the user correctly to the event")
  void registerEventTest2(){
    Mockito.when(eventRepository.findById("ididid")).thenReturn(Optional.of(event));
    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(user1));
    assertTrue(event.getRegisteredUsers().isEmpty());

    try (MockedStatic<Utils> utilities = Mockito.mockStatic(Utils.class)) {
      utilities.when(Utils::getCurrentUsername).thenReturn("username");
      assertDoesNotThrow(() -> eventService.registerEvent("ididid"));
    }
    assertEquals(1, event.getRegisteredUsers().stream().count());
    Mockito.verify(eventRepository).findById("ididid");
    Mockito.verify(userRepository).findByUsername(Mockito.any());
    Mockito.verify(eventMapper).entityToDto(Mockito.any());
    Mockito.verify(eventRepository).save(Mockito.any());
  }

  @Test
  @DisplayName("Should throw exception when the User tries to close the vote and is not the Owner.")
  void closeEventVoteTest(){
    event.setOwnerUser(user2);
    Mockito.when(eventRepository.findById("ididid")).thenReturn(Optional.of(event));
    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(user1));

    try (MockedStatic<Utils> utilities = Mockito.mockStatic(Utils.class)) {
      utilities.when(Utils::getCurrentUsername).thenReturn("username");
      assertThrows(UserIsNotOwnerException.class, () -> eventService.closeEventVote("ididid"));
    }
    Mockito.verify(eventRepository).findById("ididid");
    Mockito.verify(userRepository).findByUsername(Mockito.any());
  }

  @Test
  @DisplayName("Should close the vote correctly")
  void closeEventVoteTest2(){
    event.setOwnerUser(user1);
    Mockito.when(eventRepository.findById("ididid")).thenReturn(Optional.of(event));
    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(user1));

    try (MockedStatic<Utils> utilities = Mockito.mockStatic(Utils.class)) {
      utilities.when(Utils::getCurrentUsername).thenReturn("username");
      assertDoesNotThrow(() -> eventService.closeEventVote("ididid"));
    }
    Mockito.verify(eventRepository).findById("ididid");
    Mockito.verify(userRepository).findByUsername(Mockito.any());
    Mockito.verify(eventRepository).save(event);
    Mockito.verify(eventMapper).entityToDto(Mockito.any());
    assertEquals(Event.Status.VOTE_CLOSED, event.getStatus());
  }

  @Test
  @DisplayName("Should throw exception when EventOption does not exist")
  void voteEventOptionTest(){
    Mockito.when(eventOptionRepository.findById("ididid")).thenReturn(Optional.empty());

    try (MockedStatic<Utils> utilities = Mockito.mockStatic(Utils.class)) {
      utilities.when(Utils::getCurrentUsername).thenReturn("username");
      assertThrows(EntityNotFoundException.class,
          () -> eventService.voteEventOption("ididid", "ididid")
      );
    }
    Mockito.verify(eventOptionRepository).findById("ididid");
  }

  @Test
  @DisplayName("Should throw exception when User try to vote but the voting is already closed")
  void voteEventOptionTest2(){
    Mockito.when(eventOptionRepository.findById("ididid")).thenReturn(Optional.of(eventOption));
    event.setStatus(Event.Status.VOTE_CLOSED);
    Mockito.when(eventRepository.findById("ididid")).thenReturn(Optional.of(event));

    try (MockedStatic<Utils> utilities = Mockito.mockStatic(Utils.class)) {
      utilities.when(Utils::getCurrentUsername).thenReturn("username");
      assertThrows(EventStatusException.class,
          () -> eventService.voteEventOption("ididid", "ididid")
      );
    }
    Mockito.verify(eventOptionRepository).findById("ididid");
    Mockito.verify(eventRepository).findById("ididid");
  }

  @Test
  @DisplayName("Should generate the vote correctly")
  void voteEventOptionTest3(){
    Mockito.when(eventOptionRepository.findById("ididid2")).thenReturn(Optional.of(eventOption));
    Mockito.when(eventRepository.findById("ididid")).thenReturn(Optional.of(event));
    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(user2));
    assertEquals(0, eventOption.getVoteQuantity());
    assertEquals(0, eventOption.getVoteUsers().size());

    try (MockedStatic<Utils> utilities = Mockito.mockStatic(Utils.class)) {
      utilities.when(Utils::getCurrentUsername).thenReturn("username");
      assertDoesNotThrow(() -> eventService.voteEventOption("ididid", "ididid2"));
    }
    assertEquals(1, eventOption.getVoteQuantity());
    assertEquals(1, eventOption.getVoteUsers().size());
    Mockito.verify(eventOptionRepository).findById("ididid2");
    Mockito.verify(eventRepository, Mockito.times(2)).findById("ididid");
    Mockito.verify(userRepository).findByUsername(Mockito.any());
    Mockito.verify(eventOptionRepository).save(eventOption);
    Mockito.verify(eventMapper).entityToDto(Mockito.any());
  }

}
