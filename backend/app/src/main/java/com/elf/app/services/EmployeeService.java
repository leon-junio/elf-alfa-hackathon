package com.elf.app.services;

import java.io.File;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.elf.app.configs.FileHandler;
import com.elf.app.dtos.EmployeeDto;
import com.elf.app.exceptions.InvalidRequestException;
import com.elf.app.exceptions.ServiceException;
import com.elf.app.models.Dependent;
import com.elf.app.models.Employee;
import com.elf.app.models.Role;
import com.elf.app.models.mappers.EmployeeMapper;
import com.elf.app.models.utils.CivilStatus;
import com.elf.app.models.utils.EmployeeStatus;
import com.elf.app.models.utils.FileOCR;
import com.elf.app.models.utils.PublicAreaType;
import com.elf.app.models.utils.RaceType;
import com.elf.app.models.utils.SchoolingType;
import com.elf.app.repositories.DependentRepository;
import com.elf.app.repositories.EmployeeRepository;
import com.elf.app.repositories.RoleRepository;
import com.elf.app.requests.DependentRequest;
import com.elf.app.requests.EmployeeRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final DependentRepository dependentRepository;
    private final RoleRepository roleRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

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
     * Busca por todos os funcionarios ou candidatos
     * 
     * @param paginate Pageable paginação
     * @return List of EmployeeDto funcionarios/candidatos
     * @throws ServiceException
     * @throws InvalidRequestException
     */
    public List<EmployeeDto> getEmployees(boolean candidate, @NonNull Pageable paginate) throws ServiceException {
        try {
            var employees = employeeRepository.findByCandidate(candidate, paginate);
            return employees.stream().flatMap(List::stream).map(employeeMapper::apply).toList();
        } catch (Exception e) {
            throw new ServiceException("Employee search failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Cria um novo funcionario ou candidato para ser um funcionario
     * 
     * @param request EmployeeRequest dados do funcionario/candidato
     * @return EmployeeDto funcionario/candidato criado
     * @throws ServiceException
     * @throws InvalidRequestException
     */
    @Transactional(rollbackOn = Exception.class)
    public EmployeeDto createEmployee(@NonNull EmployeeRequest request)
            throws ServiceException, InvalidRequestException {
        try {
            Role role = roleRepository.findByUuid(request.getRole())
                    .orElseThrow(() -> new InvalidRequestException("Role not found."));
            request.getRole();
            var employee = Employee.builder()
                    .name(request.getName())
                    .motherName(request.getMotherName())
                    .fatherName(request.getFatherName())
                    .gender(request.isGender())
                    .civilStatus(CivilStatus.getCivilStatus(request.getCivilStatus()))
                    .schoolingType(SchoolingType.getSchoolingType(request.getSchoolingType()))
                    .raceType(RaceType.getRaceType(request.getRaceType()))
                    .birthday(request.getBirthday())
                    .cpf(request.getCpf().replace(".", "").replace("-", ""))
                    .email(request.getEmail())
                    .nationality(request.getNationality())
                    .countryBirth(request.getCountryBirth())
                    .stateBirth(request.getStateBirth())
                    .cityBirth(request.getCityBirth())
                    .shoeSize(request.getShoeSize())
                    .pantsSize(request.getPantsSize())
                    .shirtSize(request.getShirtSize())
                    .phoneNumber1(request.getPhoneNumber1())
                    .phoneNumber2(request.getPhoneNumber2())
                    .address(request.getAddress())
                    .number(request.getNumber())
                    .complement(request.getComplement())
                    .neighbor(request.getNeighbor())
                    .city(request.getCity())
                    .state(request.getState())
                    .cep(request.getCep().replace("-", ""))
                    .country(request.getCountry())
                    .publicAreaType(PublicAreaType.getPublicAreaType(request.getPublicAreaType()))
                    .rg(request.getRg())
                    .rgIssuer(request.getRgIssuer())
                    .rgIssuerState(request.getRgIssuerState())
                    .rgIssuerCity(request.getRgIssuerCity())
                    .rgExpeditionDate(request.getRgExpeditionDate())
                    .pis(request.getPis())
                    .role(role)
                    .pcd(request.isPcd())
                    .hosted(request.isHosted())
                    .fileRgPath(FileHandler.getFilePath(request.getFileRgPath()))
                    .fileCpfPath(FileHandler.getFilePath(request.getFileCpfPath()))
                    .fileCvPath(FileHandler.getFilePath(request.getFileCvPath()))
                    .fileCnhPath(FileHandler.getFilePath(request.getFileCnhPath()))
                    .fileReservistPath(FileHandler.getFilePath(request.getFileReservistPath()))
                    .employeeStatus(EmployeeStatus.CANDIDATO)
                    .candidate(true)
                    .status(request.getStatus())
                    .build();
            employee = employeeRepository.save(employee);
            FileOCR.compare(0,FileHandler.saveFile(request.getFileRgPath(), employee.getFileRgPath()));
            FileOCR.compare(1,FileHandler.saveFile(request.getFileCpfPath(), employee.getFileCpfPath()));
            FileHandler.saveFile(request.getFileCvPath(), employee.getFileCvPath());
            FileOCR.compare(2,FileHandler.saveFile(request.getFileCnhPath(), employee.getFileCnhPath()));
            FileHandler.saveFile(request.getFileReservistPath(), employee.getFileReservistPath());
            String depJson = request.getDependents();
            if (depJson != null) {
                List<DependentRequest> dependents = objectMapper.readValue(depJson,
                        new TypeReference<List<DependentRequest>>() {
                        });
                if (dependents != null && !dependents.isEmpty()) {
                    for (DependentRequest dependentRequest : dependents) {
                        var obj = Dependent.builder()
                                .cpf(dependentRequest.getCpf().replace(".", "").replace("-", ""))
                                .employee(employee)
                                .gender(dependentRequest.isGender())
                                .build();
                        dependentRepository.save(obj);
                    }
                }
            }
            return employeeMapper.apply(employee);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new ServiceException("Employee creation failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Atualiza um funcionario/candidato
     * 
     * @param request EmployeeRequest dados do funcionario/candidato
     * @param uuid    String uuid do funcionario/candidato
     * @return EmployeeDto funcionario/candidato atualizado
     * @throws ServiceException
     * @throws InvalidRequestException
     */
    @Transactional(rollbackOn = Exception.class)
    public EmployeeDto updateEmployee(@NonNull EmployeeRequest request, @NonNull String uuid)
            throws ServiceException, InvalidRequestException {
        var employee = employeeRepository.findByUuid(uuid)
                .orElseThrow(() -> new InvalidRequestException("Employee not found."));
        Role role = roleRepository.findByUuid(request.getRole())
                .orElseThrow(() -> new InvalidRequestException("Role not found."));
        try {
            employee.setName(request.getName());
            employee.setMotherName(request.getMotherName());
            employee.setFatherName(request.getFatherName());
            employee.setGender(request.isGender());
            employee.setCivilStatus(CivilStatus.getCivilStatus(request.getCivilStatus()));
            employee.setSchoolingType(SchoolingType.getSchoolingType(request.getSchoolingType()));
            employee.setRaceType(RaceType.getRaceType(request.getRaceType()));
            employee.setBirthday(request.getBirthday());
            employee.setCpf(request.getCpf().replace(".", "").replace("-", ""));
            employee.setEmail(request.getEmail());
            employee.setNationality(request.getNationality());
            employee.setCountryBirth(request.getCountryBirth());
            employee.setStateBirth(request.getStateBirth());
            employee.setCityBirth(request.getCityBirth());
            employee.setShoeSize(request.getShoeSize());
            employee.setPantsSize(request.getPantsSize());
            employee.setShirtSize(request.getShirtSize());
            employee.setPhoneNumber1(request.getPhoneNumber1());
            employee.setPhoneNumber2(request.getPhoneNumber2());
            employee.setAddress(request.getAddress());
            employee.setNumber(request.getNumber());
            employee.setComplement(request.getComplement());
            employee.setNeighbor(request.getNeighbor());
            employee.setCity(request.getCity());
            employee.setState(request.getState());
            employee.setCep(request.getCep().replace("-", ""));
            employee.setCountry(request.getCountry());
            employee.setPublicAreaType(PublicAreaType.getPublicAreaType(request.getPublicAreaType()));
            employee.setRg(request.getRg());
            employee.setRgIssuer(request.getRgIssuer());
            employee.setRgIssuerState(request.getRgIssuerState());
            employee.setRgIssuerCity(request.getRgIssuerCity());
            employee.setRgExpeditionDate(request.getRgExpeditionDate());
            employee.setPis(request.getPis());
            employee.setRole(role);
            employee.setPcd(request.isPcd());
            employee.setHosted(request.isHosted());
            employee.setEmployeeStatus(EmployeeStatus.getEmployeeStatus(request.getEmployeeStatus()));
            employee.setCandidate(request.isCandidate());
            employee.setStatus(request.getStatus());
            if (request.getFileRgPath() != null)
                employee.setFileRgPath(FileHandler.getFilePath(request.getFileRgPath()));
            if (request.getFileCpfPath() != null)
                employee.setFileCpfPath(FileHandler.getFilePath(request.getFileCpfPath()));
            if (request.getFileCvPath() != null)
                employee.setFileCvPath(FileHandler.getFilePath(request.getFileCvPath()));
            if (request.getFileCnhPath() != null)
                employee.setFileCnhPath(FileHandler.getFilePath(request.getFileCnhPath()));
            if (request.getFileReservistPath() != null)
                employee.setFileReservistPath(FileHandler.getFilePath(request.getFileReservistPath()));
            employee = employeeRepository.save(employee);
            if (request.getFileRgPath() != null)
                FileHandler.saveFile(request.getFileRgPath(), employee.getFileRgPath());
            if (request.getFileCpfPath() != null)
                FileHandler.saveFile(request.getFileCpfPath(), employee.getFileCpfPath());
            if (request.getFileCvPath() != null)
                FileHandler.saveFile(request.getFileCvPath(), employee.getFileCvPath());
            if (request.getFileCnhPath() != null)
                FileHandler.saveFile(request.getFileCnhPath(), employee.getFileCnhPath());
            if (request.getFileReservistPath() != null)
                FileHandler.saveFile(request.getFileReservistPath(), employee.getFileReservistPath());
            String depJson = request.getDependents();
            if (depJson != null) {
                List<DependentRequest> dependents = objectMapper.readValue(depJson,
                        new TypeReference<List<DependentRequest>>() {
                        });
                if (dependents != null && !dependents.isEmpty()) {
                    for (DependentRequest dependentRequest : dependents) {
                        var obj = Dependent.builder()
                                .cpf(dependentRequest.getCpf().replace(".", "").replace("-", ""))
                                .employee(employee)
                                .gender(dependentRequest.isGender())
                                .build();
                        dependentRepository.save(obj);
                    }
                }
            }
            return employeeMapper.apply(employee);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new ServiceException("Employee update failed due to a a service exception: " + e.getMessage());
        }
    }

    /**
     * Deleta um funcionario/candidato
     * 
     * @param uuid String uuid do funcionario/candidato
     * @throws ServiceException
     * @throws InvalidRequestException
     */
    public void deleteEmployee(@NonNull String uuid) throws ServiceException, InvalidRequestException {
        var employee = employeeRepository.findByUuid(uuid)
                .orElseThrow(() -> new InvalidRequestException("Employee not found."));
        try {
            employeeRepository.delete(employee);
            FileHandler.deleteFile(List.of(employee.getFileRgPath(), employee.getFileCpfPath(),
                    employee.getFileCvPath(), employee.getFileCnhPath(), employee.getFileReservistPath()));
        } catch (Exception e) {
            throw new ServiceException("Employee delete failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Busca um funcionario/candidato pelo uuid
     * 
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
     * 
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

    // busca os documentos de um funcionario/candidato
    public File getEmployeeDocumentRg(@NonNull String uuid) throws ServiceException, InvalidRequestException {
        var employee = employeeRepository.findByUuid(uuid)
                .orElseThrow(() -> new InvalidRequestException("Employee not found."));
        try {
            File file = FileHandler.getFile(employee.getFileRgPath());
            if (file == null) {
                throw new ServiceException("Employee document not found.");
            }
            return file;
        } catch (Exception e) {
            throw new ServiceException("Employee search failed due to a service exception: " + e.getMessage());
        }
    }

    public File getEmployeeDocumentCpf(@NonNull String uuid) throws ServiceException, InvalidRequestException {
        var employee = employeeRepository.findByUuid(uuid)
                .orElseThrow(() -> new InvalidRequestException("Employee not found."));
        try {
            File file = FileHandler.getFile(employee.getFileCpfPath());
            if (file == null) {
                throw new ServiceException("Employee document not found.");
            }
            return file;
        } catch (Exception e) {
            throw new ServiceException("Employee search failed due to a service exception: " + e.getMessage());
        }
    }

    public File getEmployeeDocumentCv(@NonNull String uuid) throws ServiceException, InvalidRequestException {
        var employee = employeeRepository.findByUuid(uuid)
                .orElseThrow(() -> new InvalidRequestException("Employee not found."));
        try {
            File file = FileHandler.getFile(employee.getFileCvPath());
            if (file == null) {
                throw new ServiceException("Employee document not found.");
            }
            return file;
        } catch (Exception e) {
            throw new ServiceException("Employee search failed due to a service exception: " + e.getMessage());
        }
    }

    public File getEmployeeDocumentCnh(@NonNull String uuid) throws ServiceException, InvalidRequestException {
        var employee = employeeRepository.findByUuid(uuid)
                .orElseThrow(() -> new InvalidRequestException("Employee not found."));
        try {
            File file = FileHandler.getFile(employee.getFileCnhPath());
            if (file == null) {
                throw new ServiceException("Employee document not found.");
            }
            return file;
        } catch (Exception e) {
            throw new ServiceException("Employee search failed due to a service exception: " + e.getMessage());
        }
    }

    public File getEmployeeDocumentReservist(@NonNull String uuid)
            throws ServiceException, InvalidRequestException {
        var employee = employeeRepository.findByUuid(uuid)
                .orElseThrow(() -> new InvalidRequestException("Employee not found."));
        try {
            File file = FileHandler.getFile(employee.getFileReservistPath());
            if (file == null) {
                throw new ServiceException("Employee document not found.");
            }
            return file;
        } catch (Exception e) {
            throw new ServiceException("Employee search failed due to a service exception: " + e.getMessage());
        }
    }

}
