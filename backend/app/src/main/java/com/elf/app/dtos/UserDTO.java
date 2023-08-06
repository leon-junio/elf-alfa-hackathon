package com.elf.app.dtos;

import com.elf.app.models.Employee;

public record UserDTO (
    String cpf,
    String id,
    Employee employee
) {
}