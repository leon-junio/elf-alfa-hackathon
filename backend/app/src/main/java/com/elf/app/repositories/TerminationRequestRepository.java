package com.elf.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elf.app.models.TerminationRequest;

public interface TerminationRequestRepository extends JpaRepository<TerminationRequest, Long> {
    Optional <TerminationRequest> findByUuid(String uuid);
}
