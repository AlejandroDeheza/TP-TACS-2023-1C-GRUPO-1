package com.tacs.backend.service;

import com.tacs.backend.dto.AuthenticationRequest;
import com.tacs.backend.dto.AuthenticationResponse;
import com.tacs.backend.dto.RegisterRequest;
import com.tacs.backend.exception.UserException;
import com.tacs.backend.model.Role;
import com.tacs.backend.model.Token;
import com.tacs.backend.model.TokenType;
import com.tacs.backend.model.User;
import com.tacs.backend.repository.UserRepository;
import com.tacs.backend.repository.impl.TokenRepositoryImpl;
import com.tacs.backend.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

  @Mock
  private UserRepository userRepositoryImpl;
  @Mock
  private TokenRepositoryImpl tokenRepositoryImpl;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private JwtService jwtService;
  @Mock
  private AuthenticationManager authenticationManager;
  @Mock
  private RateLimiterService rateLimiterService;

  private AuthenticationService authenticationService;

  private RegisterRequest registerRequest;
  private AuthenticationRequest authenticationRequest;
  private User user;
  private Token token1;
  private Token token2;

  @BeforeEach
  void setup() {
    authenticationService =
        new AuthenticationService(
            userRepositoryImpl,
            tokenRepositoryImpl,
            passwordEncoder,
            jwtService,
            authenticationManager,
            rateLimiterService
        );
    registerRequest = RegisterRequest.builder()
        .firstName("Facundo")
        .lastName("Perez")
        .username("123Perez")
        .password("UnaContraseña198!")
        .passwordConfirmation("UnaContraseña198!")
        .build();
    authenticationRequest =
        new AuthenticationRequest("123Perez", "UnaContraseña198!");
    user = User.builder()
        .id("idididiidid")
        .firstName("Facundo")
        .lastName("Perez")
        .username("123Perez")
        .password("UnaContraseña198!")
        .role(Role.USER)
        .build();
    token1 = Token.builder()
        .user(user)
        .token("jwtToken")
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    token2 = Token.builder()
        .user(user)
        .token("jwtToken2")
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
  }

  @Test
  @DisplayName("should throw exception when register a user that already exists")
  void registerTest() {
    Mockito.when(userRepositoryImpl.exists("123Perez")).thenReturn(true);
    assertThrows(UserException.class, () -> authenticationService.register(registerRequest));
  }

  

}
