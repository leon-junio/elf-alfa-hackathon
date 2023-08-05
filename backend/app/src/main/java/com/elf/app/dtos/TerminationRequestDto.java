package com.elf.app.dtos;

import jakarta.validation.constraints.Min;
import lombok.NonNull;

public record TerminationRequestDto(
        @NonNull String id,
        boolean isApproved,
        EmployeeDto employee,
        EmployeeDto targetEmployee,
        @Min(0)
        int terminationType,
        @Min(0)
        int rank,
        @Min(0)
        int requestStatusType
) {}