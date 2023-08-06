package com.elf.app.controllers;

import java.io.File;
import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elf.app.dtos.EmployeeDto;
import com.elf.app.dtos.ReportDto;
import com.elf.app.dtos.ResourceDto;
import com.elf.app.dtos.RoleDto;
import com.elf.app.exceptions.InvalidRequestException;
import com.elf.app.exceptions.ServiceException;
import com.elf.app.requests.EmployeeRequest;
import com.elf.app.requests.ReportRequest;
import com.elf.app.services.EmployeeService;
import com.elf.app.services.ReportService;
import com.elf.app.services.ResourceService;
import com.elf.app.services.RoleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/external")
@RequiredArgsConstructor
public class ExternalController extends BaseController {
    private final EmployeeService employeeService;
    private final ResourceService resourceService;
    private final RoleService roleService;
    private final ReportService reportService;

    @GetMapping("/role")
    public ResponseEntity<List<RoleDto>> getAllRoles() throws ServiceException, NotFoundException {
        return ResponseEntity.ok(roleService.getAll());
    }

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDto> createEmployee(@ModelAttribute @Valid EmployeeRequest request)
            throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(employeeService.createEmployee(request));
    }

    @PutMapping("/employee/{uuid}")
    public ResponseEntity<EmployeeDto> updateEmployee(@ModelAttribute @Valid EmployeeRequest request,
            @PathVariable(value = "uuid") String uuid) throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(employeeService.updateEmployee(request, uuid));
    }

    @GetMapping("/resource/documents/{uuid}")
    public ResponseEntity<?> getResourceDocument(@PathVariable(value = "uuid") String uuid)
            throws ServiceException, InvalidRequestException {
        File document = resourceService.getResourceDocument(uuid);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(document);
    }

    @GetMapping("/resource")
    public ResponseEntity<List<ResourceDto>> getAllResources(
            @RequestParam(required = false) Integer per_page,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer page) throws ServiceException, NotFoundException {
        return ResponseEntity.ok(resourceService.getAll(paginate(page, per_page, sort)));
    }

    @GetMapping("resource/{uuid}")
    public ResponseEntity<ResourceDto> getResourceData(
            @PathVariable(value = "uuid") String uuid) throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(resourceService.getbyUuid(uuid));
    }

    @PostMapping("/report")
    public ResponseEntity<ReportDto> createReport(@ModelAttribute @Valid ReportRequest request)
            throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(reportService.create(request));
    }
}
