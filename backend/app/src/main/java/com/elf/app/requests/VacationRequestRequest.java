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

public class VacationRequestRequest {
    private boolean isApproved;

    @NotBlank
    @UUID
    private String employee;
}
