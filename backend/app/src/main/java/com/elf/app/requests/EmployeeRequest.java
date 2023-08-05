package com.elf.app.requests;

import java.sql.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    @NotBlank
    @Length(min = 3, max = 255)
    private String name;
    @NotBlank
    @Length(min = 11, max = 11)
    private String cpf;
    private List<String> phones;
    private List<String> emails;
    @NotNull
    private Date contratation_date;
    @NotNull
    private Date birth_date;
    private float salary;
    @NotBlank
    @Pattern(regexp = "^(SUPERVISOR|MANAGER|EMPLOYEE|INTERN)$")
    private String employee_role;
}
