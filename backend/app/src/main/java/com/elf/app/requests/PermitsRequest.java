package com.elf.app.requests;

import org.hibernate.validator.constraints.UUID;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PermitsRequest {
    @NotBlank
    @UUID
    private String employee;

    private boolean assignPermits;
    private boolean registerUser;
    private boolean viewApplicant;
    private boolean viewReports;
    private boolean requestVacation;
    private boolean requestTermination;
    private boolean requestEmployeeTermination;
    private boolean viewRequests;
    private boolean registerResources;
}
