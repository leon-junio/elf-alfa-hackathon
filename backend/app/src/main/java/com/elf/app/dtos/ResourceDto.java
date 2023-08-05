package com.elf.app.dtos;

import lombok.NonNull;

public record ResourceDto(
        @NonNull String id,
        @NonNull String description,
        boolean isAvailable,
        @NonNull String filePath) {
}