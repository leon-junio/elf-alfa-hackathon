package com.elf.app.services;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.elf.app.dtos.ReportDto;
import com.elf.app.exceptions.ServiceException;
import com.elf.app.models.mappers.ReportMapper;
import com.elf.app.repositories.ReportPictureRepository;
import com.elf.app.repositories.ReportRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final ReportPictureRepository reportPictureRepository;
    private final ReportMapper reportMapper = new ReportMapper();


    /**
     * Busca por todos os reports
     * 
     * @return List of ReportDto
     * @throws ServiceException
     */
    public List<ReportDto> getAll(@NonNull Pageable paginate) throws ServiceException {
        try {
            var reports = reportRepository.findAll(paginate);
            return reports.stream().map(reportMapper).toList();
        } catch (Exception e) {
            throw new ServiceException("Report search failed due to a service exception: " + e.getMessage());
        }
    }

}
