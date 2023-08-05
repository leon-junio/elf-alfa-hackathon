package com.elf.app.requests;

import org.hibernate.validator.constraints.UUID;
import org.springframework.web.multipart.MultipartFile;

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

public class ReportPictureRequest {
    @NotBlank
    @UUID
    private String report;
    
    @NotNull
    private MultipartFile pictureData; 
}
