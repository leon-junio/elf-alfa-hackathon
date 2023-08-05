package com.elf.app.requests;

import java.io.File;
import java.sql.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
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
public class EmployeeRequest {
    @NotBlank
    @Length(min = 3, max = 255)
    private String name;
     @NotBlank
    @Length(min = 3, max = 255)
    private String motherName;
    @NotBlank
    @Length(min = 3, max = 255)
    private String fatherName;
    private boolean gender;
    @Min(0)
    private int civilStatus;
    @Min(0)
    private int schoolingType;
    @Min(0)
    private int raceType;
    @NotBlank
    @Length(min = 11, max = 11)
    private String cpf;
    private String email;
    @NotNull
    private Date birthday;
     @NotBlank
    private String nationality;
     @NotBlank
    private String countryBirth;
     @NotBlank
    private String stateBirth;
     @NotBlank
    private String cityBirth;
    @Min(1)
    private int shoeSize;
    @Min(1)
    private int pantsSize;
    @NotBlank
    private String shirtSize;
    @NotBlank
    private String phoneNumber1;
    private String phoneNumber2;
    @NotBlank
    private String address;
    @NotBlank
    private String number;
    private String complement;
    @NotBlank
    private String neighbor;
    @NotBlank
    private String city;
    @NotBlank
    @Length(min = 2, max = 2)
    private String state;
    @NotBlank
    @Length(min = 8, max = 8)
    private String cep;
    @NotBlank
    private String country;
    @Min(0)
    private int publicAreaType;
    @NotBlank
    private String rg;
    private String rgIssuer;
    @NotBlank
    private String rgIssuerState;
    @NotBlank
    private String rgIssuerCity;
    @NotNull
    private Date rgExpeditionDate;
    @NotBlank
    private String pis;
    private String role;
    private boolean pcd;
    private boolean hosted;
    private File fileRgPath;
    private File fileCpfPath;
    private File fileCvPath;
    private File fileCnhPath;
    private File fileReservistPath;
    private boolean hasFriend;
    private String friendName;
    private String friendRole;
    private String friendCity;
    private boolean candidate;
    @Min(0)
    private int employeeStatus;
    private List<DependentRequest> dependents;
}
