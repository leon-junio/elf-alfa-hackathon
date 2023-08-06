package com.elf.app.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.elf.app.dtos.responses.AuthenticationResponse;
import com.elf.app.models.Employee;
import com.elf.app.models.User;
import com.elf.app.providers.WhatsappBuilder;
import com.elf.app.repositories.EmployeeRepository;
import com.elf.app.repositories.UserRepository;
import com.elf.app.requests.AuthenticationRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

        private final UserRepository repository;
        private final EmployeeRepository employeeRepository;
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
                String genPass = "" + request.getName().length() + random.nextInt(1000);
                var user = User.builder()
                                .cpf(request.getCpf())
                                .password(passwordEncoder.encode(genPass))
                                .employee(employeeRepository.findById(request.getId()).orElseThrow())
                                .locked(true)
                                .enabled(true)
                                .build();
                repository.save(user);
                try {
                        WhatsappBuilder.send("Novo usuario cadastrado no sistema.\nSenha de acesso: " + genPass,
                                        user.getEmployee());
                        System.out.println("whatsapp: connection -> " + 12203);
                } catch (Exception e) {
                        System.err.println(e.getMessage());
                }
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
                authenticationManager
                                .authenticate(new UsernamePasswordAuthenticationToken(
                                                request.getCpf().replace(".", "").replace("-", ""),
                                                request.getPassword()));
                var user = repository.findByCpf(request.getCpf().replace(".", "").replace("-", "")).orElseThrow();
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build();
        }
}
