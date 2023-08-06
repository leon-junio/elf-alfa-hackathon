package com.elf.app.services;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.elf.app.dtos.PermitsDto;
import com.elf.app.exceptions.InvalidRequestException;
import com.elf.app.exceptions.ServiceException;
import com.elf.app.models.Employee;
import com.elf.app.models.Permits;
import com.elf.app.models.mappers.PermitsMapper;
import com.elf.app.repositories.EmployeeRepository;
import com.elf.app.repositories.PermitsRepository;
import com.elf.app.requests.PermitsRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class PermitsService {
    private final PermitsRepository permitsRepository;
    private final PermitsMapper permitsMapper = new PermitsMapper();
    private final EmployeeRepository employeeRepository;

    /**
     * Busca por todos os permits
     * 
     * @return List of PermitseDto
     * @throws ServiceException
     */

    public List<PermitsDto> getAll(@NonNull Pageable paginate) throws ServiceException {
        try {
            var permits = permitsRepository.findAll(paginate);
            return permits.stream().map(permitsMapper).toList();
        } catch (Exception e) {
            throw new ServiceException("permits search failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Busca por todos os permits
     * 
     * @return List of PermitseDto
     * @throws ServiceException
     */

    public List<PermitsDto> getByEmployeeId (@NonNull Employee employee) throws ServiceException {
        try {
            var permits = permitsRepository.findByEmployeeId(employee.getId())
                .orElseThrow(() -> new InvalidRequestException("Employee not found."));
            return permits.stream().map(permitsMapper).toList();
        } catch (Exception e) {
            throw new ServiceException("permits search failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Deleta um permit criado
     * 
     * @param uuid String uuid do permit
     * @throws ServiceException
     * @throws InvalidRequestException
     */
    public void delete(@NonNull String uuid) throws ServiceException {
        try {
            var permits = permitsRepository.findByUuid(uuid)
                    .orElseThrow(() -> new InvalidRequestException("Permits not found."));
            if (permits == null) {
                throw new ServiceException("permits not found");
            }
            permitsRepository.delete(permits);
        } catch (Exception e) {
            throw new ServiceException("permits deletion failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Busca por um permit criado
     * 
     * @param uuid String uuid do permit
     * @return PermitsDto
     * @throws ServiceException
     */
    public PermitsDto getbyUuid(@NonNull String uuid) throws ServiceException {
        try {
            var permits = permitsRepository.findByUuid(uuid)
                    .orElseThrow(() -> new InvalidRequestException("Permits not found."));
            if (permits == null) {
                throw new ServiceException("permits not found");
            }
            return permitsMapper.apply(permits);
        } catch (Exception e) {
            throw new ServiceException("permits search failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Cria um novo permit
     * 
     * @param permitsDto PermitsDto
     * @return PermitsDto
     * @throws ServiceException
     */
    @Transactional(rollbackOn = Exception.class)
    public PermitsDto create(@Valid @NonNull PermitsRequest request) throws ServiceException {
        try {
            Employee employee = employeeRepository.findByUuid(request.getEmployee())
                .orElseThrow(() -> new InvalidRequestException("employee not found."));
            var permits = Permits.builder()
                    .employee(employee)
                    .assignPermits(request.isAssignPermits())
                    .registerUser(request.isRegisterUser())
                    .viewApplicant(request.isViewApplicant())
                    .viewReports(request.isViewReports())
                    .requestVacation(request.isRequestVacation())
                    .requestTermination(request.isRequestTermination())
                    .requestEmployeeTermination(request.isRequestEmployeeTermination())
                    .viewRequests(request.isViewRequests())
                    .registerResources(request.isRegisterResources())
                    .build();
            permitsRepository.save(permits);
            return permitsMapper.apply(permits);
        } catch (Exception e) {
            throw new ServiceException("permits creation failed due to a service exception: " + e.getMessage());
        }
    }

        /**
     * Atualiza um permits criado
     * 
     * @param uuid        String uuid do permits
     * @param permitsDto PermitsDto
     * @return PermitsDto
     * @throws ServiceException
     * @throws InvalidRequestException
     */
    @Transactional(rollbackOn = Exception.class)
    public PermitsDto update(@NonNull String uuid, @Valid @NonNull PermitsRequest request) throws ServiceException {
        try {
            Employee employee = employeeRepository.findByUuid(request.getEmployee())
                .orElseThrow(() -> new InvalidRequestException("employee not found."));

            var permits = permitsRepository.findByUuid(uuid)
                    .orElseThrow(() -> new InvalidRequestException("Permits not found."));
            if (permits == null) {
                throw new ServiceException("permits not found");
            }

            permits.setEmployee(employee);
            permits.setAssignPermits(request.isAssignPermits());
            permits.setRegisterUser(request.isRegisterUser());
            permits.setViewApplicant(request.isViewApplicant());
            permits.setViewReports(request.isViewReports());
            permits.setRequestTermination(request.isRequestTermination());
            permits.setRequestEmployeeTermination(request.isRequestEmployeeTermination());
            permits.setViewRequests(request.isViewRequests());
            permits.setRegisterResources(request.isRegisterResources());
            permits.setRequestVacation(request.isRequestVacation());
            permitsRepository.save(permits);
            return permitsMapper.apply(permits);

        } catch (Exception e) {
            throw new ServiceException("permit update failed due to a service exception: " + e.getMessage());
        }
    }
}
