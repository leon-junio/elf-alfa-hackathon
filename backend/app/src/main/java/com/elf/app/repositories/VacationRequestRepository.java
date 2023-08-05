package com.elf.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elf.app.models.VacationRequest;

public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long> {
    <Optional>VacationRequest findByUuid(String uuid);
}
