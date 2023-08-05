package com.elf.app.models.mappers;

import java.util.function.Function;

import com.elf.app.dtos.VacationRequestDto;
import com.elf.app.models.VacationRequest;

public class VacationRequestMapper implements Function<VacationRequest, VacationRequestDto> {

    private final EmployeeMapper employeeMapper = new EmployeeMapper();

    @Override
    public VacationRequestDto apply(VacationRequest arg0) {
        return new VacationRequestDto(
                arg0.getUuid(),
                arg0.isApproved(),
                arg0.getRequestStatusType().ordinal(),
                employeeMapper.apply(arg0.getEmployee())
        );
    }
}