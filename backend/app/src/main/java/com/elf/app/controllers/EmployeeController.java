package com.elf.app.controllers;

import java.io.File;
import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elf.app.dtos.EmployeeDto;
import com.elf.app.exceptions.InvalidRequestException;
import com.elf.app.exceptions.ServiceException;
import com.elf.app.requests.EmployeeRequest;
import com.elf.app.services.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController extends BaseController {

    private final EmployeeService employeeService;

    @GetMapping("/{uuid}")
    public ResponseEntity<EmployeeDto> getEmployeeData(
            @PathVariable(value = "uuid") String uuid) throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(employeeService.getEmployeeByUuid((uuid)));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(
            @RequestParam(required = false) Integer per_page,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer page) throws ServiceException, NotFoundException {
        return ResponseEntity.ok(employeeService.getAll(paginate(page, per_page, sort)));
    }

    @GetMapping("/filter/{candidate}")
    public ResponseEntity<List<EmployeeDto>> getEmployeesByFilter(
            @PathVariable(value = "candidate") boolean candidate,
            @RequestParam(required = false) Integer per_page,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer page) throws ServiceException, NotFoundException {
        return ResponseEntity.ok(employeeService.getEmployees(candidate, paginate(page, per_page, sort)));
    }

    @GetMapping("/documents/rg/{uuid}")
    public ResponseEntity<?> getRgDocument(@PathVariable(value = "uuid") String uuid)
            throws ServiceException, InvalidRequestException {
        File document = employeeService.getEmployeeDocumentRg(uuid);
        return ResponseEntity.ok().contentType(MediaType.valueOf("application/pdf")).body(document);
    }

    @GetMapping("/documents/cpf/{uuid}")
    public ResponseEntity<?> getCpfDocument(@PathVariable(value = "uuid") String uuid)
            throws ServiceException, InvalidRequestException {
        File document = employeeService.getEmployeeDocumentCpf(uuid);
        return ResponseEntity.ok().contentType(MediaType.valueOf("application/pdf")).body(document);
    }

    @GetMapping("/documents/cv/{uuid}")
    public ResponseEntity<?> getCvsDocument(@PathVariable(value = "uuid") String uuid)
            throws ServiceException, InvalidRequestException {
        File document = employeeService.getEmployeeDocumentCv(uuid);
        return ResponseEntity.ok().contentType(MediaType.valueOf("application/pdf")).body(document);
    }

    @GetMapping("/documents/cnh/{uuid}")
    public ResponseEntity<?> getCnhDocument(@PathVariable(value = "uuid") String uuid)
            throws ServiceException, InvalidRequestException {
        File document = employeeService.getEmployeeDocumentCnh(uuid);
        return ResponseEntity.ok().contentType(MediaType.valueOf("application/pdf")).body(document);
    }

    @GetMapping("/documents/reservist/{uuid}")
    public ResponseEntity<?> getReservistDocument(@PathVariable(value = "uuid") String uuid)
            throws ServiceException, InvalidRequestException {
        File document = employeeService.getEmployeeDocumentReservist(uuid);
        return ResponseEntity.ok().contentType(MediaType.valueOf("application/pdf")).body(document);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeRequest request)
            throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(employeeService.createEmployee(request));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody @Valid EmployeeRequest request,
            @PathVariable(value = "uuid") String uuid) throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(employeeService.updateEmployee(request, uuid));

    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable(value = "uuid") String uuid)
            throws ServiceException, InvalidRequestException {
        employeeService.deleteEmployee(uuid);
        return ResponseEntity.noContent().build();
    }

}
