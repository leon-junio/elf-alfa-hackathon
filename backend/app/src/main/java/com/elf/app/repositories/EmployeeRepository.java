package com.elf.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.elf.app.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByCpf(String cpf);

    Optional<Employee> findByUuid(String uuid);

    Optional<List<Employee>> findByCandidate(boolean candidate, Pageable pageable);

    long count();
}
