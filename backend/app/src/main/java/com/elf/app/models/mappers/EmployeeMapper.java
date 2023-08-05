package com.elf.app.models.mappers;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.elf.app.dtos.EmployeeDto;
import com.elf.app.models.Employee;

@Service
public class EmployeeMapper implements Function<Employee,EmployeeDto>{

    @Override
    public EmployeeDto apply(Employee arg0) {
        return new EmployeeDto(
            arg0.getUuid(),
            arg0.getName(),
            arg0.getCpf(),
            arg0.getPhones(),
            arg0.getEmails(),
            arg0.getContrationDate(),
            arg0.getBirthDate(),
            arg0.getSalary(),
            arg0.getEmployeerole()
        );
    }
    
}
