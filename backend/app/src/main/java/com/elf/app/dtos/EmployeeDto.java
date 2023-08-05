package com.elf.app.dtos;

import java.util.Date;
import java.util.List;

import com.elf.app.models.utils.EmployeeRole;

import io.micrometer.common.lang.NonNull;

public record EmployeeDto(
                @NonNull String id,
                @NonNull String name,
                @NonNull String cpf,
                List<String> phones,
                List<String> emails,
                @NonNull AddressDto address,
                @NonNull Date contrationDate,
                @NonNull Date birthDate,
                float salary,
                @NonNull EmployeeRole employeerole) {
};