package com.elf.app.requests;

import java.util.Set;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ReportRequest {
    @NotBlank
    @Length(min = 3, max = 255)
    private String name;


    @NotBlank
    @UUID
    private String resource;

    @NotBlank
    @UUID
    private String role;

    @UUID
    private String employee;

    @NotBlank
    @Length(min = 3, max = 1000)
    private String ocurrenceDescription;

    @NotBlank
    private String address;

    @NotBlank
    private String number;

    @NotBlank
    private String complement;

    @NotBlank
    private String neighbor;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String country;

    @NotEmpty
    @Size(min = 1, max = 3)
    private Set <ReportPictureRequest> reportPictures;
}
