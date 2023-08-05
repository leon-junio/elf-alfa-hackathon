package com.elf.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.elf.app.models.Permits;

public interface PermitsRepository extends JpaRepository<Permits, Long> {
    <Optional> Permits findByUuid(String uuid);
}
