package com.elf.app.models.mappers;

import java.util.function.Function;

import com.elf.app.dtos.ReportDto;
import com.elf.app.models.Report;

public class ReportMapper implements Function<Report, ReportDto> {

    private final EmployeeMapper employeeMapper = new EmployeeMapper();
    private final ResourceMapper resourceMapper = new ResourceMapper();
    private final RoleMapper roleMapper = new RoleMapper();

    @Override
    public ReportDto apply(Report report) {
        return new ReportDto(
                report.getId().toString(),
                report.getName(),
                resourceMapper.apply(report.getResource()),
                roleMapper.apply(report.getRole()),
                employeeMapper.apply(report.getEmployee()),
                report.getOcurrenceDescription(),
                report.getAddress(),
                report.getNumber(),
                report.getComplement(),
                report.getNeighbor(),
                report.getCity(),
                report.getState(),
                report.getCountry()
        );
    }
}
