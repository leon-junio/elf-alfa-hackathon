package com.elf.app.requests;

import java.util.Date;

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

public class TerminationRequestRequest {
    private boolean isApproved;
    private String employee;
    private String targetEmployee;
    private int terminationType;
    private int rank;
    private int requestStatusType;
    private Date terminationDate;
}
