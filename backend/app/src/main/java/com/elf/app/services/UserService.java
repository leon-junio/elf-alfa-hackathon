package com.elf.app.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.elf.app.dtos.UserDTO;
import com.elf.app.models.Employee;
import com.elf.app.models.User;
import com.elf.app.models.mappers.UserDtoMapper;
import com.elf.app.repositories.EmployeeRepository;
import com.elf.app.repositories.UserRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDtoMapper userDtoMapper;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    /**
     * Get the actual user logged in the application
     * 
     * @return UserDTO object with the user data
     */
    public Employee getActualUser(@NonNull String cpf) {
        var user = employeeRepository.findByCpf(cpf).orElseThrow(() -> new RuntimeException("User not found"));
        return user != null ? user : null;
    }
}
