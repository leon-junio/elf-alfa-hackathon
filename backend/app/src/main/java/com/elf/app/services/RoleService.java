package com.elf.app.services;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.elf.app.dtos.RoleDto;
import com.elf.app.exceptions.ServiceException;
import com.elf.app.models.mappers.RoleMapper;
import com.elf.app.repositories.RoleRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper = new RoleMapper();
    
    /**
     * Busca por todos os roles
     * 
     * @return List of RoleDto
     * @throws ServiceException
     */
    public List<RoleDto> getAll(@NonNull Pageable paginate) throws ServiceException {
        try {
            var roles = roleRepository.findAll(paginate);
            return roles.stream().map(roleMapper).toList();
        } catch (Exception e) {
            throw new ServiceException("role search failed due to a service exception: " + e.getMessage());
        }
    }
}
