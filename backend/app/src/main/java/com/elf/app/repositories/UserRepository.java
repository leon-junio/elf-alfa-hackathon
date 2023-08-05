package com.elf.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elf.app.models.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    
    Optional<User> findByCpf(String cpf);
    
}