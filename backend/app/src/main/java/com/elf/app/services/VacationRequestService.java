package com.elf.app.services;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.elf.app.dtos.VacationRequestDto;
import com.elf.app.exceptions.InvalidRequestException;
import com.elf.app.exceptions.ServiceException;
import com.elf.app.models.Employee;
import com.elf.app.models.VacationRequest;
import com.elf.app.models.mappers.VacationRequestMapper;
import com.elf.app.models.utils.RequestStatusType;
import com.elf.app.repositories.EmployeeRepository;
import com.elf.app.repositories.VacationRequestRepository;
import com.elf.app.requests.VacationRequestRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class VacationRequestService {
    private final VacationRequestRepository vacationRequestRepository;
    private final VacationRequestMapper vacationRequestMapper = new VacationRequestMapper();
    private final EmployeeRepository employeeRepository;

    /**
     * Busca por todos os vacationRequests
     * 
     * @return List of VacationRequestDto
     * @throws ServiceException
     */
    public List<VacationRequestDto> getAll(@NonNull Pageable paginate) throws ServiceException {
        try {
            var vacationRequests = vacationRequestRepository.findAll(paginate);
            return vacationRequests.stream().map(vacationRequestMapper).toList();
        } catch (Exception e) {
            throw new ServiceException("vacationRequest search failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Deleta um vacationRequest criado
     * 
     * @param uuid String uuid do vacationRequest
     * @throws ServiceException
     * @throws InvalidRequestException
     */
    public void delete(@NonNull String uuid) throws ServiceException {
        try {
            var vacationRequest = vacationRequestRepository.findByUuid(uuid)
                    .orElseThrow(() -> new InvalidRequestException("VacationRequest not found."));
            if (vacationRequest == null) {
                throw new ServiceException("vacationRequest not found");
            }
            vacationRequestRepository.delete(vacationRequest);
        } catch (Exception e) {
            throw new ServiceException("vacationRequest deletion failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Busca por um vacationRequest criado
     * 
     * @param uuid String uuid do vacationRequest
     * @return VacationRequestDto
     * @throws ServiceException
     */
    public VacationRequestDto getbyUuid(@NonNull String uuid) throws ServiceException {
        try {
            var vacationRequest = vacationRequestRepository.findById(30l)
                    .orElseThrow(() -> new InvalidRequestException("VacationRequest not found."));
            if (vacationRequest == null) {
                throw new ServiceException("vacationRequest not found");
            }
            return vacationRequestMapper.apply(vacationRequest);
        } catch (Exception e) {
            throw new ServiceException("vacationRequest search failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Cria um novo vacationRequest
     * 
     * @param vacationRequestDto VacationRequestDto
     * @return VacationRequestDto
     * @throws ServiceException
     */
    @Transactional(rollbackOn = Exception.class)
    public VacationRequestDto create(@NonNull VacationRequestRequest request) throws ServiceException {
        try {
            Employee employee = employeeRepository.findById(30l)
            .orElseThrow(() -> new InvalidRequestException("Employee not found."));
var vacationRequest = VacationRequest.builder()
                    .isApproved(request.isApproved())
                    .requestStatusType(RequestStatusType.getRequestStatusType(request.getRequestStatusType()))
                    .employee(employee)
                    .build();
            vacationRequestRepository.save(vacationRequest);
            return vacationRequestMapper.apply(vacationRequest);
        } catch (Exception e) {
            throw new ServiceException("vacationRequest creation failed due to a service exception: " + e.getMessage());
        }
    }

     /**
     * Atualiza um vacationRequest criado
     * 
     * @param uuid        String uuid do vacationRequest
     * @param vacationRequestDto VacationRequestDto
     * @return VacationRequestDto
     * @throws ServiceException
     * @throws InvalidRequestException
     */
    @Transactional(rollbackOn = Exception.class)
    public VacationRequestDto update(@NonNull String uuid, @Valid @NonNull VacationRequestRequest request) throws ServiceException {
        try {
                Employee employee = employeeRepository.findByUuid(request.getEmployee())
                    .orElseThrow(() -> new InvalidRequestException("Employee not found."));

                var vacationRequest = vacationRequestRepository.findByUuid(uuid)
                        .orElseThrow(() -> new InvalidRequestException("VacationRequest not found."));
            if (vacationRequest == null) {
                throw new ServiceException("vacationRequest not found");
            }

            vacationRequest.setApproved(request.isApproved());
            vacationRequest.setRequestStatusType(RequestStatusType.getRequestStatusType(request.getRequestStatusType()));
            vacationRequest.setEmployee(employee);
            vacationRequestRepository.save(vacationRequest);

            return vacationRequestMapper.apply(vacationRequest);
        } catch (Exception e) {
            throw new ServiceException("vacationRequest update failed due to a service exception: " + e.getMessage());
        }
    }
}
