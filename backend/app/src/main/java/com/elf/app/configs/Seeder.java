package com.elf.app.configs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.elf.app.models.Role;
import com.elf.app.repositories.RoleRepository;
import com.elf.app.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Seeder {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        if (!usersExist()) {
            seedUsersTable();
        }
        if (!rolesExist()) {
            seedRoleTable();
        }
    }

    private void seedUsersTable() {
        // var user = User.builder()
        // .cpf("12345678900")
        // .password(passwordEncoder.encode("123456"))
        // .locked(false)
        // .enabled(true)
        // .build();
        // repository.save(user);
    }

    private void seedRoleTable() {
        try {
            Resource companyDataResource = new ClassPathResource("companies.txt");
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

    private boolean rolesExist() {
        return roleRepository.count() != 0;
    }
}
