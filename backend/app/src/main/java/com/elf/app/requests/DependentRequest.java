package com.elf.app.requests;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class DependentRequest {
    @NotBlank
    @Length(min = 11, max = 11)
    private String cpf;

    private boolean gender;
}
