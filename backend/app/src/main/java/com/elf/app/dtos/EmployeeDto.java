package com.elf.app.dtos;

import java.util.Date;
import java.util.Set;

import com.elf.app.models.Dependent;
import com.elf.app.models.Role;

import io.micrometer.common.lang.NonNull;

public record EmployeeDto(
        @NonNull String id,
        @NonNull String name,
        @NonNull String motherName,
        @NonNull String fatherName,
        boolean gender,
        @NonNull int civilStatus,
        @NonNull int schoolingType,
        @NonNull int raceType,
        @NonNull Date birthday,
        @NonNull String nationality,
        @NonNull String countryBirth,
        @NonNull String stateBirth,
        @NonNull String cityBirth,
        int shoeSize,
        int pantsSize,
        @NonNull String shirtSize,
        @NonNull String phoneNumber1,
        String phoneNumber2,
        @NonNull String email,
        @NonNull String address,
        @NonNull String number,
        String complement,
        @NonNull String neighbor,
        @NonNull String city,
        @NonNull String state,
        @NonNull String cep,
        @NonNull String country,
        @NonNull int publicAreaType,
        @NonNull String rg,
        String rgIssuer,
        @NonNull String rgIssuerState,
        @NonNull String rgIssuerCity,
        @NonNull Date rgExpeditionDate,
        @NonNull String cpf,
        @NonNull String pis,
        @NonNull Role role,
        boolean pcd,
        boolean hosted,
        @NonNull String fileRgPath,
        @NonNull String fileCpfPath,
        @NonNull String fileCvPath,
        String fileCnhPath,
        String fileReservistPath,
        boolean hasFriend,
        @NonNull String friendName,
        @NonNull String friendRole,
        @NonNull String friendCity,
        @NonNull boolean candidate,
        @NonNull int employeeStatus,
        Set<Dependent> dependents
) {}