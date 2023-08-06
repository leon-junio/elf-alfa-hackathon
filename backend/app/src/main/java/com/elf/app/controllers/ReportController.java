package com.elf.app.controllers;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elf.app.dtos.ReportDto;
import com.elf.app.exceptions.InvalidRequestException;
import com.elf.app.exceptions.ServiceException;
import com.elf.app.requests.ReportRequest;
import com.elf.app.services.ReportService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController extends BaseController {
    private final ReportService reportService;

    @GetMapping("/{uuid}")
    public ResponseEntity<ReportDto> getReportData(
            @PathVariable(value = "uuid") String uuid) throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(reportService.getByUuid(uuid));
    }

    @GetMapping
    public ResponseEntity<List<ReportDto>> getAllReports(
            @RequestParam(required = false) Integer per_page,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer page) throws ServiceException, NotFoundException {
        return ResponseEntity.ok(reportService.getAll(paginate(page, per_page, sort)));
    }

    @PostMapping
    public ResponseEntity<ReportDto> createReport(@ModelAttribute @Valid ReportRequest request)
            throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(reportService.create(request));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ReportDto> updateReport(@ModelAttribute @Valid ReportRequest request,
            @PathVariable(value = "uuid") String uuid) throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(reportService.update(uuid, request));
    }

    @GetMapping("/pictures/{uuid}")
    public ResponseEntity<List<byte[]>> getReportPicture(@PathVariable(value = "uuid") String uuid)
            throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(reportService.getPictures(uuid));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ReportDto> deleteReport(@PathVariable(value = "uuid") String uuid)
            throws ServiceException, InvalidRequestException {
        reportService.delete(uuid);
        return ResponseEntity.ok().build();
    }

}
