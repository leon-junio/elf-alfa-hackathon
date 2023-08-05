package com.elf.app.requests;

import org.hibernate.validator.constraints.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class TerminationRequestRequest {
    private boolean isApproved;

    @NotBlank
    @UUID
    private String employee;

    @NotBlank
    @UUID
    private String targetEmployee;

    @Min(0)
    private int terminationType;

    @Min(0)
    private int requestStatusType;
}
