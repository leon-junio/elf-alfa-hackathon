package com.elf.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elf.app.models.Dependent;

public interface DependentRepository extends JpaRepository<Dependent, Long> {
    <Optional> Dependent findByUuid(String uuid);
}
