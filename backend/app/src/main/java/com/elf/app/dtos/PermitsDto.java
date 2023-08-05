package com.elf.app.dtos;

import lombok.NonNull;

public record PermitsDto (
    @NonNull String id,
    String employee_id,
    boolean assignPermits,
    boolean registerUser,
    boolean viewApplicant,
    boolean viewReports,
    boolean requestVacation,
    boolean requestTermination,
    boolean requestEmployeeTermination,
    boolean viewRequests,
    boolean registerResources){
}
