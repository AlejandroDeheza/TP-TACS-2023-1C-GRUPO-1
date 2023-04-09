package com.tacs.backend.controller;

import com.tacs.backend.dto.AuthenticationRequest;
import com.tacs.backend.dto.AuthenticationResponse;
import com.tacs.backend.dto.RegisterRequest;
import com.tacs.backend.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
public class SessionController {

    @Autowired
    SessionService sessionService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signUp(@RequestBody RegisterRequest request){
        return new ResponseEntity<>(sessionService.signup(request), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return new ResponseEntity<>(sessionService.login(request), HttpStatus.OK);
    }

}
