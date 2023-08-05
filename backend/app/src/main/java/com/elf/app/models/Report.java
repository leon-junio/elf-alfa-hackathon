package com.elf.app.models;

import java.util.Set;

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
import jakarta.persistence.ManyToOne;
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

public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, updatable = false)
    private String uuid;

    @Column(nullable = false, length = 350)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Resource resource;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Role role;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employee employee;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ReportPictures> reportPictures;

    @Column(nullable = false, length = 1000)
    private String ocurrenceDescription;

    @Column(nullable = false, length = 200)
    private String address;

    @Column(nullable = false)
    private String number;

    @Column(length = 60)
    private String complement;

    @Column(nullable = false)
    private String neighbor;

    @Column(nullable = false, length = 120)
    private String city;

    @Column(nullable = false, length = 2, columnDefinition = "char(2)")
    private String state;

    @Column(nullable = false, length = 100)
    private String country;

    @PrePersist
    public void prePersist() {
        setUuid(java.util.UUID.randomUUID().toString());
    }
}
