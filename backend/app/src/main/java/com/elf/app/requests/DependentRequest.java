package com.elf.app.requests;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;
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

public class DependentRequest {
    @NotBlank
    @Length(min = 11, max = 14)
    private String cpf;

    @NotNull
    private Date birthday;

    private boolean gender;

    @NotBlank
    @Length(min = 3, max = 255)
    private String relationship;

    @NotBlank
    @UUID
    private String employee;
}
