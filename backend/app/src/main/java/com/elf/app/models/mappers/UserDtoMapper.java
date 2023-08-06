package com.elf.app.models.mappers;

import java.util.function.Function;
import org.springframework.stereotype.Service;

import com.elf.app.dtos.UserDTO;
import com.elf.app.models.User;

@Service
public class UserDtoMapper implements Function<User, UserDTO> {

    @Override
    /**
     * Convert a User object to a UserDTO object
     * 
     * @param user User object
     * @return UserDTO object
     */
    public UserDTO apply(User user) {
        var employee = user.getEmployee();
        return new UserDTO(
                user.getCpf(),
                employee.getUuid(),
                employee);
    }
}