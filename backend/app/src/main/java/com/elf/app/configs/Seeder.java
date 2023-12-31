package com.elf.app.configs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.elf.app.models.Employee;
import com.elf.app.models.Role;
import com.elf.app.models.User;
import com.elf.app.models.utils.CivilStatus;
import com.elf.app.models.utils.EmployeeStatus;
import com.elf.app.models.utils.PublicAreaType;
import com.elf.app.models.utils.RaceType;
import com.elf.app.models.utils.SchoolingType;
import com.elf.app.repositories.EmployeeRepository;
import com.elf.app.repositories.RoleRepository;
import com.elf.app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Seeder {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRepository repository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final java.util.Random random = new java.util.Random();

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        if (!usersExist() && !employeesExist()) {
            seedUsersTable();
        }
        if (!rolesExist()) {
            seedRoleTable();
        }
    }

    private void seedUsersTable() {
        try {
            var admin = Employee.builder()
                    .name("Administrador")
                    .motherName("Adm")
                    .fatherName("Adm")
                    .gender(false)
                    .civilStatus(CivilStatus.getCivilStatus(0))
                    .schoolingType(SchoolingType.getSchoolingType(0))
                    .raceType(RaceType.getRaceType(0))
                    .birthday(new Date())
                    .cpf("00000000000")
                    .email("admin@gmail.com")
                    .nationality("Brasileiro")
                    .countryBirth("Brasil")
                    .stateBirth("Ad")
                    .cityBirth("Adm")
                    .shoeSize(1)
                    .pantsSize(1)
                    .shirtSize("M")
                    .phoneNumber1("(31) 98765-4321")
                    .phoneNumber2("(31) 91234-5678")
                    .address("Contorno")
                    .number("1000")
                    .complement("Adm")
                    .neighbor("Adm")
                    .city("Adm")
                    .state("Ad")
                    .cep("00000000")
                    .country("Adm")
                    .publicAreaType(PublicAreaType.getPublicAreaType(0))
                    .rg("000000000")
                    .rgIssuer("Adm")
                    .rgIssuerState("Adm")
                    .rgIssuerCity("Adm")
                    .rgExpeditionDate(new Date())
                    .pis("Adm")
                    .role(null)
                    .pcd(false)
                    .hosted(false)
                    .fileRgPath("/docs/Adm.pdf")
                    .fileCpfPath("/docs/Adm.pdf")
                    .fileCvPath("/docs/Adm.pdf")
                    .fileCnhPath("/docs/Adm.pdf")
                    .fileReservistPath("/docs/Adm.pdf")
                    .employeeStatus(EmployeeStatus.CONTRATADO)
                    .candidate(false)
                    .build();
            var employee = employeeRepository.save(admin);
            String genPass = "" + employee.getName().length() + random.nextInt(1000);
            System.out.println("senha: " + genPass);
            var user = User.builder()
                    .cpf(employee.getCpf())
                    .password(passwordEncoder.encode(genPass))
                    .employee(employeeRepository.findById(employee.getId()).orElseThrow())
                    .locked(true)
                    .enabled(true)
                    .build();
            repository.save(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void seedRoleTable() {
        try {
            Resource companyDataResource = new ClassPathResource("funcoes.dat");
            File file = companyDataResource.getFile();
            try (BufferedReader buff = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = buff.readLine()) != null) {
                    String[] data = line.split("\t");
                    var role = Role.builder()
                            .code(data[0])
                            .name(data[1])
                            .cbo(Integer.parseInt(data[2]))
                            .build();
                    roleRepository.save(role);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private boolean usersExist() {
        return userRepository.count() != 0;
    }

    private boolean employeesExist() {
        return employeeRepository.count() != 0;
    }

    private boolean rolesExist() {
        return roleRepository.count() != 0;
    }
}
