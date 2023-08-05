package com.elf.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elf.app.models.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    Optional<Resource> findByUuid(String uuid);
}
