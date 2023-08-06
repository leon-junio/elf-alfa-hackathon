package com.elf.app.requests;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;
import org.springframework.web.multipart.MultipartFile;

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

    private String employee;

    @NotBlank
    @Length(min = 3, max = 1000)
    private String ocurrenceDescription;

    @NotBlank
    private String latitude;

    @NotBlank
    private String longitude;

    @NotEmpty
    @Size(min = 1, max = 6)
    private List<MultipartFile> pictures;
}
