package com.elf.app.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.elf.app.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByCpf(String cpf);

    // //List<Employee> findByAnotherId(Long id, Pageable pageable);

    Optional<Employee> findByUuid(String uuid);
}
