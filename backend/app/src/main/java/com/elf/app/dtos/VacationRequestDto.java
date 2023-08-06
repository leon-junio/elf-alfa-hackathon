package com.elf.app.dtos;

import java.util.Date;

import lombok.NonNull;

public record VacationRequestDto(
        @NonNull String id,
        boolean isApproved,
        int requestStatusType,
        EmployeeDto employee,
        @NonNull Date vacationStart,
        @NonNull Date vacationEnd
) {}