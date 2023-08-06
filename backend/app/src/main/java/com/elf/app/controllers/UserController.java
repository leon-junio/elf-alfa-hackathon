package com.elf.app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elf.app.dtos.EmployeeDto;
import com.elf.app.models.User;
import com.elf.app.models.mappers.EmployeeMapper;
import com.elf.app.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController extends BaseController {

    private final UserService userService;
    private final EmployeeMapper employeeMapper;

    /**
     * Get the actual user logged in the application
     * 
     * @return UserDTO object with the user data
     */
    @GetMapping
    public ResponseEntity<EmployeeDto> getLoggedUser() {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var employee = userService.getActualUser(user.getCpf());
        return ResponseEntity.ok(employeeMapper.apply(employee));
    }
}
