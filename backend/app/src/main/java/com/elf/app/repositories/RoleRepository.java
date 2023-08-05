package com.elf.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elf.app.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    <Optional>Role findByUuid(String uuid);
}
