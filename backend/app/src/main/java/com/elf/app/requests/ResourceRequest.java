package com.elf.app.requests;

import org.hibernate.validator.constraints.Length;
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

public class ResourceRequest {
    @NotBlank
    @Length(min = 3, max = 255)
    private String description;

    private boolean isAvailable;

    @NotNull
    private MultipartFile file; 

    
}
