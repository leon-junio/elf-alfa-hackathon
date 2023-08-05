package com.elf.app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elf.app.dtos.responses.AuthenticationResponse;
import com.elf.app.models.User;
import com.elf.app.repositories.EmployeeRepository;
import com.elf.app.repositories.UserRepository;
import com.elf.app.requests.AuthenticationRequest;
import com.elf.app.services.AuthenticationService;
import com.elf.app.services.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmployeeRepository employeeRepository;

    /**
     * 
     * Authenticate a user and generate a token for it
     * 
     * @param request the request with the user data
     * @return AuthenticationResponse with the token
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody AuthenticationRequest request) {
        try {
            // var user = User.builder()
            //         .cpf("14433628670")
            //         .password(passwordEncoder.encode(
            //                 "teste123"))
            //         .employee(employeeRepository.findById(1L).orElseThrow())
            //         .build();
            // repository.save(user);
            return ResponseEntity.ok(authenticationService.signin(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getLocalizedMessage());
        }
    }
}