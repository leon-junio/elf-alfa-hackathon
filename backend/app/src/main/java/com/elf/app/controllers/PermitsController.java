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
import com.elf.app.dtos.PermitsDto;
import com.elf.app.exceptions.InvalidRequestException;
import com.elf.app.exceptions.ServiceException;
import com.elf.app.models.Employee;
import com.elf.app.repositories.EmployeeRepository;
import com.elf.app.requests.PermitsRequest;
import com.elf.app.services.PermitsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/permits")
@RequiredArgsConstructor

public class PermitsController extends BaseController {
    
    private final PermitsService permitsService;
    private final EmployeeRepository employeeRepository;

    @GetMapping("/{uuid}")
    public ResponseEntity<PermitsDto> getPermitsData(
            @PathVariable(value = "uuid") String uuid) throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(permitsService.getbyUuid(uuid));
    }

    @GetMapping
    public ResponseEntity<List<PermitsDto>> getAllPermitss(
            @RequestParam(required = false) Integer per_page,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer page) throws ServiceException, NotFoundException {
        return ResponseEntity.ok(permitsService.getAll(paginate(page, per_page, sort)));
    }

    @PostMapping
    public ResponseEntity<PermitsDto> createPermits(@ModelAttribute @Valid PermitsRequest request)
            throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(permitsService.create(request));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<PermitsDto> updatePermits(@ModelAttribute @Valid PermitsRequest request,
            @PathVariable(value = "uuid") String uuid) throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(permitsService.update(uuid, request));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<PermitsDto> deletePermits(@PathVariable(value = "uuid") String uuid)
            throws ServiceException, InvalidRequestException {
        permitsService.delete(uuid);
        return ResponseEntity.ok().build();
    }

    @GetMapping("employee/{uuid}")
        public ResponseEntity<List<PermitsDto>> getAllPermitss(@PathVariable(value = "uuid") String uuid) throws InvalidRequestException, ServiceException {
        Employee employee = employeeRepository.findByUuid(uuid)
                .orElseThrow(() -> new InvalidRequestException("employee not found."));
        return ResponseEntity.ok(permitsService.getByEmployeeId(employee));
    }
}
