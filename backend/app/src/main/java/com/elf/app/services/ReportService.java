package com.elf.app.services;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;

import com.elf.app.dtos.ReportDto;
import com.elf.app.exceptions.ServiceException;
import com.elf.app.models.Employee;
import com.elf.app.models.Report;
import com.elf.app.models.ReportPictures;
import com.elf.app.models.Resource;
import com.elf.app.models.Role;
import com.elf.app.models.mappers.ReportMapper;
import com.elf.app.models.utils.ImageCompressor;
import com.elf.app.repositories.EmployeeRepository;
import com.elf.app.repositories.ReportPictureRepository;
import com.elf.app.repositories.ReportRepository;
import com.elf.app.repositories.ResourceRepository;
import com.elf.app.repositories.RoleRepository;
import com.elf.app.requests.ReportRequest;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final ReportPictureRepository reportPictureRepository;
    private final ReportMapper reportMapper = new ReportMapper();
    private final RoleRepository roleRepository;
    private final ResourceRepository resourceRepository;
    private final EmployeeRepository employeeRepository;

    /**
     * Busca por todos os reports
     * 
     * @return List of ReportDto
     * @throws ServiceException
     */
    @GetMapping
    public List<ReportDto> getAll(@NonNull Pageable paginate) throws ServiceException {
        try {
            var reports = reportRepository.findAll(paginate);
            return reports.stream().map(reportMapper).toList();
        } catch (Exception e) {
            throw new ServiceException("Report search failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Busca por um report específico
     * 
     * @param uuid o uuid do report
     * @return ReportDto
     * @throws ServiceException
     */
    @GetMapping("/{uuid}")
    public ReportDto getByUuid(@NonNull String uuid) throws ServiceException {
        try {
            var report = reportRepository.findByUuid(uuid).orElseThrow(() -> new ServiceException("Report not found"));
            return reportMapper.apply(report);
        } catch (Exception e) {
            throw new ServiceException("Report search failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Cria um novo report
     * 
     * @param reportDto o report a ser criado
     * @return ReportDto
     * @throws ServiceException
     */
    @PostMapping
    @Transactional(rollbackOn = Exception.class)
    public ReportDto create(@NonNull ReportRequest reportRequest) throws ServiceException {
        try {
            Employee employee = null;
            if (reportRequest.getEmployee() != null) {
                employee = employeeRepository.findByUuid(reportRequest.getEmployee())
                        .orElseThrow(() -> new ServiceException("Employee not found"));
            }
            Role role = roleRepository.findByUuid(reportRequest.getRole())
                    .orElseThrow(() -> new ServiceException("Role not found"));
            Resource resource = resourceRepository.findByUuid(reportRequest.getResource())
                    .orElseThrow(() -> new ServiceException("Resource not found"));
            var report = Report.builder()
                    .name(reportRequest.getName())
                    .resource(resource)
                    .role(role)
                    .employee(employee)
                    .ocurrenceDescription(reportRequest.getOcurrenceDescription())
                    .latitude(reportRequest.getLatitude())
                    .longitude(reportRequest.getLongitude())
                    .build();
            reportRepository.save(report);
            var pictures = reportRequest.getPictures();
            if (pictures != null && !pictures.isEmpty()) {
                for (MultipartFile file : pictures) {
                    var picture = ReportPictures.builder()
                            .report(report)
                            .name(file.getOriginalFilename())
                            .type(file.getContentType())
                            .pictureData(ImageCompressor.compressImage(file.getBytes()))
                            .build();
                    reportPictureRepository.save(picture);
                }
            }
            return reportMapper.apply(report);
        } catch (Exception e) {
            throw new ServiceException("Report creation failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Atualiza um report
     * 
     * @param uuid      o uuid do report
     * @param reportDto o report a ser atualizado
     * @return ReportDto
     * @throws ServiceException
     */
    @PutMapping("/{uuid}")
    @Transactional(rollbackOn = Exception.class)
    public ReportDto update(@NonNull String uuid, @NonNull ReportRequest reportRequest) throws ServiceException {
        try {
            var report = reportRepository.findByUuid(uuid).orElseThrow(() -> new ServiceException("Report not found"));
            Employee employee = null;
            if (reportRequest.getEmployee() != null) {
                employee = employeeRepository.findByUuid(reportRequest.getEmployee())
                        .orElseThrow(() -> new ServiceException("Employee not found"));
            }
            Role role = roleRepository.findByUuid(reportRequest.getRole())
                    .orElseThrow(() -> new ServiceException("Role not found"));
            Resource resource = resourceRepository.findByUuid(reportRequest.getResource())
                    .orElseThrow(() -> new ServiceException("Resource not found"));
            report.setName(reportRequest.getName());
            report.setResource(resource);
            report.setRole(role);
            report.setEmployee(employee);
            report.setOcurrenceDescription(reportRequest.getOcurrenceDescription());
            report.setLatitude(reportRequest.getLatitude());
            report.setLongitude(reportRequest.getLongitude());
            reportRepository.save(report);
            var pictures = reportRequest.getPictures();
            if (pictures != null && !pictures.isEmpty()) {
                for (MultipartFile file : pictures) {
                    var picture = ReportPictures.builder()
                            .report(report)
                            .name(file.getOriginalFilename())
                            .type(file.getContentType())
                            .pictureData(ImageCompressor.compressImage(file.getBytes()))
                            .build();
                    reportPictureRepository.save(picture);
                }
            }
            return reportMapper.apply(report);
        } catch (Exception e) {
            throw new ServiceException("Report update failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Deleta um report
     * 
     * @param uuid o uuid do report
     * @throws ServiceException
     */
    @DeleteMapping("/{uuid}")
    @Transactional(rollbackOn = Exception.class)
    public void delete(@NonNull String uuid) throws ServiceException {
        try {
            var report = reportRepository.findByUuid(uuid).orElseThrow(() -> new ServiceException("Report not found"));
            reportRepository.delete(report);
        } catch (Exception e) {
            throw new ServiceException("Report deletion failed due to a service exception: " + e.getMessage());
        }
    }

    @GetMapping("/{uuid}/pictures")
    public List<byte[]> getPictures(@NonNull String uuid) throws ServiceException {
        try {
            var report = reportRepository.findByUuid(uuid).orElseThrow(() -> new ServiceException("Report not found"));
            var pictures = reportPictureRepository.findByReportId(report.getId());
            List<byte[]> list = null;
            if (pictures.isPresent()) {
                list = pictures.get().stream()
                        .map((ReportPictures reportPictures) -> ImageCompressor
                                .decompressImage(reportPictures.getPictureData()))
                        .toList();
            }
            return list;
        } catch (Exception e) {
            throw new ServiceException("Report search failed due to a service exception: " + e.getMessage());
        }
    }

}
