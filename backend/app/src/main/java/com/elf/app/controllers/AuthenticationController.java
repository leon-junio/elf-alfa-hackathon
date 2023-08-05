package com.elf.app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elf.app.requests.AuthenticationRequest;
import com.elf.app.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * 
     * Authenticate a user and generate a token for it
     * 
     * @param request the request with the user data
     * @return AuthenticationResponse with the token
     */
    @PostMapping("/signin")
    public ResponseEntity<?> signin(
            @RequestBody AuthenticationRequest request) {
        try {
            return ResponseEntity.ok(authenticationService.signin(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getLocalizedMessage());
        }
    }
}