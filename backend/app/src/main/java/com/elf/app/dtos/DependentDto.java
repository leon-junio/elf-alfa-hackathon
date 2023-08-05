package com.elf.app.dtos;

import java.util.Date;

import lombok.NonNull;

public record DependentDto(
        @NonNull String id,
        @NonNull String name,
        @NonNull String cpf,
        @NonNull Date birthday,
        boolean gender,
        @NonNull String relationship,
        String employee_id) {
}
