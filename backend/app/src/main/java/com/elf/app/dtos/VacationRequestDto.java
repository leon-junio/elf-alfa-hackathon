package com.elf.app.dtos;

import lombok.NonNull;

public record VacationRequestDto(
        @NonNull String id,
        boolean isApproved,
        int requestStatusType,
        EmployeeDto employee
) {}