package com.elf.app.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.elf.app.dtos.responses.AuthenticationResponse;
import com.elf.app.repositories.UserRepository;
import com.elf.app.requests.AuthenticationRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Authenticate a user and generate a token for it
     * 
     * @param request the request with the user data
     * @return AuthenticationResponse with the token
     */
    public AuthenticationResponse signin(AuthenticationRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getCpf(), request.getPassword()));
        var user = repository.findByCpf(request.getCpf()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
