package com.elf.app.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.elf.app.dtos.responses.AuthenticationResponse;
import com.elf.app.models.Employee;
import com.elf.app.models.User;
import com.elf.app.repositories.UserRepository;
import com.elf.app.requests.AuthenticationRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final java.util.Random random = new java.util.Random();
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Create a new user and generate a token for it
     * 
     * @param request the request with the user data
     * @return AuthenticationResponse with the token
     */
    public AuthenticationResponse signup(Employee request) {
        var user = User.builder()
                .cpf(request.getCpf())
                .password(passwordEncoder.encode(
                        request.getName() + random.nextInt(1000)))
                .locked(true)
                .enabled(true)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Authenticate a user and generate a token for it
     * 
     * @param request the request with the user data
     * @return AuthenticationResponse with the token
     */
    public AuthenticationResponse signin(AuthenticationRequest request) {
        System.out.println(request);
        System.out.println(request.getCpf().replace(".", "").replace("-", ""));
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        request.getCpf().replace(".", "").replace("-", ""), request.getPassword()));
        var user = repository.findByCpf(request.getCpf().replace(".", "").replace("-", "")).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
