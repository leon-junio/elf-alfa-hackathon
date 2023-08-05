package com.elf.app.models;

import java.util.Date;
import java.util.Set;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import com.elf.app.models.utils.CivilStatus;
import com.elf.app.models.utils.EmployeeStatus;
import com.elf.app.models.utils.PublicAreaType;
import com.elf.app.models.utils.RaceType;
import com.elf.app.models.utils.SchoolingType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, updatable = false)
    private String uuid;
    @Column(nullable = false, length = 350)
    private String name;
    @Column(nullable = false, length = 350)
    private String motherName;
    @Column(nullable = false, length = 350)
    private String fatherName;
    @Column(nullable = false)
    private boolean gender;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CivilStatus civilStatus;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SchoolingType schoolingType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RaceType raceType;
    @Column(nullable = false)
    private Date birthday;
    @Column(nullable = false)
    private String nacionality;
    @Column(nullable = false)
    private String countryBirth;
    @Column(nullable = false)
    private String stateBirth;
    @Column(nullable = false)
    private String cityBirth;
    @Column(nullable = false)
    private int shoeSize;
    @Column(nullable = false)
    private int pantsSize;
    @Column(nullable = false, length = 2)
    private String shirtSize;
    @Column(nullable = false, length = 16)
    private String phoneNumber1;
    @Column(length = 16)
    private String phoneNumber2;
    @Column(nullable = false)
    private String email;
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
    @Column(nullable = false, length = 8, columnDefinition = "char(8)")
    @Length(min = 8, max = 8)
    private String cep;
    @Column(nullable = false, length = 100)
    private String country;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PublicAreaType publicAreaType;
    @Column(nullable = false, length = 25, unique = true)
    private String rg;
    @Column(length = 10)
    private String rgIssuer;
    @Column(nullable = false, length = 80)
    private String rgIssuerState;
    @Column(nullable = false, length = 120)
    private String rgIssuerCity;
    @Column(nullable = false)
    private Date rgExpeditionDate;
    @Column(nullable = false, length = 11, unique = true)
    private String cpf;
    @Column(nullable = false, length = 11, unique = true)
    private String pis;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Role role;
    @Column(nullable = false)
    private boolean pcd;
    @Column(nullable = false)
    private boolean hosted;
    @Column(nullable = false)
    private String fileRgPath;
    @Column(nullable = false)
    private String fileCpfPath;
    @Column(nullable = false)
    private String fileCvPath;
    private String fileCnhPath;
    private String fileReservistPath;
    @Column(nullable = false)
    private boolean hasFriend;
     @Column(nullable = false)
    private String friendName;
     @Column(nullable = false)
    private String friendRole;
     @Column(nullable = false)
    private String friendCity;
    @Column(nullable = false)
    private boolean candidate;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmployeeStatus employeeStatus;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Dependent> dependents;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Report> reports;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TerminationRequest> terminationRequests;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<VacationRequest> vacationRequests;


    @PrePersist
    public void prePersist() {
        setUuid(java.util.UUID.randomUUID().toString());
    }
}
