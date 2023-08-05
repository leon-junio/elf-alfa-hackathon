package com.elf.app.models;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table

public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, updatable = false)
    private String uuid;
    
    @Column(nullable = false, length = 350)
    private String description;

    @Column(nullable = false)
    private boolean isAvailable;

    @Column(nullable = false)
    private String filePath;

    @OneToMany(mappedBy = "resource")
    private Set<Report> reports;

    @PrePersist
    public void prePersist() {
        setUuid(java.util.UUID.randomUUID().toString());
    }    
}
