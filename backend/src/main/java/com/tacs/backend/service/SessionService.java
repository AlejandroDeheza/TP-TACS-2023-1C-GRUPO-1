package com.tacs.backend.service;

import com.tacs.backend.dao.UserRepository;
import com.tacs.backend.dto.AuthenticationRequest;
import com.tacs.backend.dto.AuthenticationResponse;
import com.tacs.backend.dto.RegisterRequest;
import com.tacs.backend.exception.SessionException;
import com.tacs.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SessionService {

    @Autowired
    private UserRepository userRepository;

    public AuthenticationResponse signup(RegisterRequest request) throws SessionException {
        if(userRepository.exists(request.getUsername())) {
            throw new SessionException("Username already exists");
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();
        var savedUser = userRepository.save(user);
        //TODO
        /*var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);*/

        return AuthenticationResponse.builder().build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) throws SessionException {
        userRepository.findUserByUsername(request.getUsername()).orElseThrow(() -> new SessionException("Wrong username"));
        //TODO
        return AuthenticationResponse.builder().build();
    }
}
