package com.elf.app.models.mappers;

import java.util.function.Function;

import com.elf.app.dtos.RoleDto;
import com.elf.app.models.Role;

public class RoleMapper implements Function<Role, RoleDto> {

    @Override
    public RoleDto apply(Role role) {
        return new RoleDto(
                role.getUuid(),
                role.getName(),
                role.getCbo(),
                role.getCode()
        );
    }
}
