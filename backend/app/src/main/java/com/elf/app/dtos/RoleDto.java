package com.elf.app.dtos;

import lombok.NonNull;

public record RoleDto(
        @NonNull String id,
        @NonNull String name,
        int cbo
) {}
