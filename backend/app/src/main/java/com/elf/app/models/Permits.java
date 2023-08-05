package com.elf.app.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table

public class Permits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, updatable = false)
    private String uuid;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employee employee;

    @Column(nullable = false)
    private boolean assignPermits;

    @Column(nullable = false)
    private boolean registerUser;

    @Column(nullable = false)
    private boolean viewApplicant;

    @Column(nullable = false)
    private boolean viewReports;

    @Column(nullable = false)
    private boolean requestVacation;

    @Column(nullable = false)
    private boolean requestTermination;

    @Column(nullable = false)
    private boolean requestEmployeeTermination;

    @Column(nullable = false)
    private boolean viewRequests;

    @Column(nullable = false)
    private boolean registerResources;

    @PrePersist
    public void prePersist() {
        setUuid(java.util.UUID.randomUUID().toString());
    }
}
