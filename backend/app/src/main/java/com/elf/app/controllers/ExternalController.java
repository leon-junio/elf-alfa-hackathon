package com.elf.app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elf.app.dtos.EmployeeDto;
import com.elf.app.exceptions.InvalidRequestException;
import com.elf.app.exceptions.ServiceException;
import com.elf.app.requests.EmployeeRequest;
import com.elf.app.services.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/external")
@RequiredArgsConstructor
public class ExternalController extends BaseController {
    private final EmployeeService employeeService;

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeRequest request)
            throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(employeeService.createEmployee(request));
    }

    @PutMapping("/employee/{uuid}")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody @Valid EmployeeRequest request,
            @PathVariable(value = "uuid") String uuid) throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(employeeService.updateEmployee(request, uuid));
    }
}
