package com.elf.app.dtos;

import lombok.NonNull;

public record ReportDto(
        @NonNull String id,
        @NonNull String name,
        @NonNull ResourceDto resource,
        @NonNull RoleDto role,
        EmployeeDto employee,
        @NonNull String ocurrenceDescription,
        @NonNull String latitude,
        @NonNull String longitude
) {}
