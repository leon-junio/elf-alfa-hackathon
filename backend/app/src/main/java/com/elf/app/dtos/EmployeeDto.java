package com.elf.app.dtos;

import java.util.Date;
import java.util.List;

import com.elf.app.models.Role;
import com.elf.app.models.utils.CivilStatus;
import com.elf.app.models.utils.PublicAreaType;
import com.elf.app.models.utils.RaceType;
import com.elf.app.models.utils.SchoolingType;

import io.micrometer.common.lang.NonNull;

public record EmployeeDto(
        @NonNull String id,
        @NonNull String name,
        @NonNull String motherName,
        @NonNull String fatherName,
        boolean gender,
        @NonNull CivilStatus civilStatus,
        @NonNull SchoolingType schoolingType,
        @NonNull RaceType raceType,
        @NonNull Date birthday,
        @NonNull String nacionality,
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
        @NonNull PublicAreaType publicAreaType,
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
        @NonNull String friendCity
) {}