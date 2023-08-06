package com.elf.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elf.app.models.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByUuid(String uuid);
}
