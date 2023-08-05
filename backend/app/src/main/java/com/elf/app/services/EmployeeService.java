package com.elf.app.services;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.elf.app.dtos.EmployeeDto;
import com.elf.app.exceptions.InvalidRequestException;
import com.elf.app.exceptions.ServiceException;
import com.elf.app.models.Employee;
import com.elf.app.models.mappers.EmployeeMapper;
import com.elf.app.repositories.EmployeeRepository;
import com.elf.app.requests.EmployeeRequest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    /**
     * Busca por todos os funcionarios
     * 
     * @return List of EmployeeDto
     * @throws ServiceException
     */
    public List<EmployeeDto> getAll(@NonNull Pageable paginate) throws ServiceException {
        try {
            var employees = employeeRepository.findAll(paginate);
            return employees.stream().map(employeeMapper).toList();
        } catch (Exception e) {
            throw new ServiceException("Employee search failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Cria um novo funcionario ou candidato para ser um funcionario
     * @param request EmployeeRequest dados do funcionario/candidato
     * @return EmployeeDto funcionario/candidato criado
     * @throws ServiceException
     * @throws InvalidRequestException
     */
    public EmployeeDto createEmployee(@NonNull EmployeeRequest request)
            throws ServiceException, InvalidRequestException {
        try {
            var employee = Employee.builder()
                    .name(request.getName())
                    .salary(request.getSalary())
                    .address(request.getAddress())
                    .cpf(request.getCpf())
                    .phones(request.getPhones())
                    .emails(request.getEmails())
                    .address(address)
                    .contrationDate(request.getContratation_date())
                    .birthDate(request.getBirth_date())
                    .salary(request.getSalary())
                    .employeerole(EmployeeRole.valueOf(request.getEmployee_role()))
                    // .Another(AnotherRepository.findByUuid(request.getAnother_id())
                    //         .orElseThrow(() -> new InvalidRequestException("Another not found.")))
                    .build();
            employee = employeeRepository.save(employee);
            return employeeMapper.apply(employee);
        } catch (Exception e) {
            throw new ServiceException("Employee creation failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Atualiza um funcionario/candidato
     * @param request EmployeeRequest dados do funcionario/candidato
     * @param uuid String uuid do funcionario/candidato
     * @return EmployeeDto funcionario/candidato atualizado
     * @throws ServiceException
     * @throws InvalidRequestException
     */
    public EmployeeDto updateEmployee(@NonNull EmployeeRequest request, @NonNull String uuid)
            throws ServiceException, InvalidRequestException {
                var employee = employeeRepository.findByUuid(uuid)
                    .orElseThrow(() -> new InvalidRequestException("Employee not found."));
        try {
            employee.setName(request.getName());
            employee.setSalary(request.getSalary());
            employee.setAddress(request.getAddress());
            employee.setCpf(request.getCpf());
            employee.setPhones(request.getPhones());
            employee.setEmails(request.getEmails());
            employee.setAddress(addressRepository.save(request.getAddress()));
            employee.setContrationDate(request.getContratation_date());
            employee.setBirthDate(request.getBirth_date());
            employee.setSalary(request.getSalary());
            employee.setEmployeerole(EmployeeRole.valueOf(request.getEmployee_role()));
            employee = employeeRepository.save(employee);
            return employeeMapper.apply(employee);
        } catch (Exception e) {
            throw new ServiceException("Employee update failed due to a a service exception: " + e.getMessage());
        }
    }

    /**
     * Deleta um funcionario/candidato
     * @param uuid String uuid do funcionario/candidato
     * @throws ServiceException
     * @throws InvalidRequestException
     */
    public void deleteEmployee(@NonNull String uuid) throws ServiceException, InvalidRequestException {
        var employee = employeeRepository.findByUuid(uuid)
                    .orElseThrow(() -> new InvalidRequestException("Employee not found."));
        try { 
            employeeRepository.delete(employee);
        } catch (Exception e) {
            throw new ServiceException("Employee delete failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Busca um funcionario/candidato pelo uuid
     * @param uuid String uuid do funcionario/candidato
     * @return EmployeeDto funcionario/candidato encontrado
     * @throws ServiceException
     * @throws InvalidRequestException
     */
    public EmployeeDto getEmployeeByUuid(@NonNull String uuid) throws ServiceException, InvalidRequestException {
        var employee = employeeRepository.findByUuid(uuid)
                    .orElseThrow(() -> new InvalidRequestException("Employee not found."));
        try {
            return employeeMapper.apply(employee);
        } catch (Exception e) {
            throw new ServiceException("Employee search failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Busca um funcionario/candidato pelo cpf
     * @param cpf String cpf do funcionario/candidato
     * @return EmployeeDto funcionario/candidato encontrado
     * @throws ServiceException
     * @throws InvalidRequestException
     */
    public EmployeeDto getEmployeeByCpf(@NonNull String cpf) throws ServiceException, InvalidRequestException {
        var employee = employeeRepository.findByCpf(cpf)
                    .orElseThrow(() -> new InvalidRequestException("Employee not found."));
        try {
            return employeeMapper.apply(employee);
        } catch (Exception e) {
            throw new ServiceException("Employee search failed due to a service exception: " + e.getMessage());
        }
    }

    // TODO - Get de apenas candidatos
    // TODO - Get de apenas funcionarios

}
