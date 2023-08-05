package com.elf.app.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.elf.app.dtos.UserDTO;
import com.elf.app.models.User;
import com.elf.app.models.mappers.UserDtoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDtoMapper userDtoMapper;

    /**
     * Get the actual user logged in the application
     * 
     * @return UserDTO object with the user data
     */
    public UserDTO getActualUser() {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user != null ? userDtoMapper.apply(user) : null;
    }
}
