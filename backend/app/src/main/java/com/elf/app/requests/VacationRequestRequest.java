package com.elf.app.requests;

import java.sql.Date;

import org.hibernate.validator.constraints.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class VacationRequestRequest {
    private boolean isApproved;

    @NotBlank
    @UUID
    private String employee;

    @Min(0)
    private int requestStatusType;

    @NotNull
    private Date vacationStart;

    @NotNull
    private Date vacationEnd;
}
