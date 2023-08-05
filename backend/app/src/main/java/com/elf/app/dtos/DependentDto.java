package com.elf.app.dtos;

import lombok.NonNull;

public record DependentDto(
        @NonNull String id,
        @NonNull String cpf,
        boolean gender,
        String employee_id) {
}
