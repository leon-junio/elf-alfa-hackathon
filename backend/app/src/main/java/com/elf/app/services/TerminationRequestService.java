package com.elf.app.services;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.elf.app.dtos.TerminationRequestDto;
import com.elf.app.exceptions.InvalidRequestException;
import com.elf.app.exceptions.ServiceException;
import com.elf.app.models.Employee;
import com.elf.app.models.TerminationRequest;
import com.elf.app.models.mappers.TerminationRequestMapper;
import com.elf.app.models.utils.RequestStatusType;
import com.elf.app.repositories.EmployeeRepository;
import com.elf.app.repositories.TerminationRequestRepository;
import com.elf.app.requests.TerminationRequestRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class TerminationRequestService {
    private final TerminationRequestRepository terminationRequestRepository;
    private final TerminationRequestMapper terminationRequestMapper = new TerminationRequestMapper();
    private final EmployeeRepository employeeRepository;

    /**
     * Busca por todos os terminationRequests
     * 
     * @return List of TerminationRequestDto
     * @throws ServiceException
     */
    public List<TerminationRequestDto> getAll(@NonNull Pageable paginate) throws ServiceException {
        try {
            var terminationRequests = terminationRequestRepository.findAll(paginate);
            return terminationRequests.stream().map(terminationRequestMapper).toList();
        } catch (Exception e) {
            throw new ServiceException("terminationRequest search failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Deleta um terminationRequest criado
     * 
     * @param uuid String uuid do terminationRequest
     * @throws ServiceException
     * @throws InvalidRequestException
     */
    public void delete(@NonNull String uuid) throws ServiceException {
        try {
            var terminationRequest = terminationRequestRepository.findByUuid(uuid)
                    .orElseThrow(() -> new InvalidRequestException("TerminationRequest not found."));
            if (terminationRequest == null) {
                throw new ServiceException("terminationRequest not found");
            }
            terminationRequestRepository.delete(terminationRequest);
        } catch (Exception e) {
            throw new ServiceException("terminationRequest deletion failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Busca por um terminationRequest criado
     * 
     * @param uuid String uuid do terminationRequest
     * @return TerminationRequestDto
     * @throws ServiceException
     */
    public TerminationRequestDto getbyUuid(@NonNull String uuid) throws ServiceException {
        try {
            var terminationRequest = terminationRequestRepository.findByUuid(uuid)
                    .orElseThrow(() -> new InvalidRequestException("TerminationRequest not found."));
            if (terminationRequest == null) {
                throw new ServiceException("terminationRequest not found");
            }
            return terminationRequestMapper.apply(terminationRequest);
        } catch (Exception e) {
            throw new ServiceException("terminationRequest search failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Cria um novo terminationRequest
     * 
     * @param terminationRequestDto TerminationRequestDto
     * @return TerminationRequestDto
     * @throws ServiceException
     */
    @Transactional(rollbackOn = Exception.class)
    public TerminationRequestDto create(@NonNull TerminationRequestRequest request) throws ServiceException {
        try {
            System.out.println(request.getEmployee());
            Employee employee = employeeRepository.findById(30l)
                    .orElseThrow(() -> new InvalidRequestException("Employee not found."));
var terminationRequest = TerminationRequest.builder()
                    .isApproved(request.isApproved())
                    .requestStatusType(RequestStatusType.getRequestStatusType(request.getRequestStatusType()))
                    .employee(employee)
                    .terminationDate(request.getTerminationDate())
                    .build();
            terminationRequestRepository.save(terminationRequest);
            return terminationRequestMapper.apply(terminationRequest);
        } catch (Exception e) {
            throw new ServiceException("terminationRequest creation failed due to a service exception: " + e.getMessage());
        }
    }

     /**
     * Atualiza um terminationRequest criado
     * 
     * @param uuid        String uuid do terminationRequest
     * @param terminationRequestDto TerminationRequestDto
     * @return TerminationRequestDto
     * @throws ServiceException
     * @throws InvalidRequestException
     */
    @Transactional(rollbackOn = Exception.class)
    public TerminationRequestDto update(@NonNull String uuid, @NonNull TerminationRequestRequest request) throws ServiceException {
        try {
                Employee employee = employeeRepository.findById(30l)
                    .orElseThrow(() -> new InvalidRequestException("Employee not found."));

                var terminationRequest = terminationRequestRepository.findByUuid(uuid)
                        .orElseThrow(() -> new InvalidRequestException("TerminationRequest not found."));
            if (terminationRequest == null) {
                throw new ServiceException("terminationRequest not found");
            }

            terminationRequest.setApproved(request.isApproved());
            terminationRequest.setRequestStatusType(RequestStatusType.getRequestStatusType(request.getRequestStatusType()));
            terminationRequest.setEmployee(employee);
            terminationRequest.setTerminationDate(request.getTerminationDate());

            terminationRequestRepository.save(terminationRequest);

            return terminationRequestMapper.apply(terminationRequest);
        } catch (Exception e) {
            throw new ServiceException("terminationRequest update failed due to a service exception: " + e.getMessage());
        }
    }
}
