package com.elf.app.dtos;

import org.springframework.http.HttpStatus;

import lombok.NonNull;

public record ExceptionDto(
        @NonNull HttpStatus status,
        String message) {
}