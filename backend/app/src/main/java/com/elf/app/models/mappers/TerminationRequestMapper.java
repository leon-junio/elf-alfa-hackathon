package com.elf.app.models.mappers;

import java.util.function.Function;

import com.elf.app.dtos.TerminationRequestDto;
import com.elf.app.models.TerminationRequest;

public class TerminationRequestMapper implements Function<TerminationRequest, TerminationRequestDto> {

    private final EmployeeMapper employeeMapper = new EmployeeMapper();

    @Override
    public TerminationRequestDto apply(TerminationRequest arg0) {
        var employee = arg0.getEmployee();
        var targetEmployee = arg0.getTargetEmployee();
        return new TerminationRequestDto(
                arg0.getUuid(),
                arg0.isApproved(),
                employee != null ? employeeMapper.apply(employee) : null,
                targetEmployee != null ? employeeMapper.apply(targetEmployee) : null,
                arg0.getTerminationType().ordinal(),
                arg0.getRank(),
                arg0.getRequestStatusType().ordinal(),
                arg0.getTerminationDate()
        );
    }
}