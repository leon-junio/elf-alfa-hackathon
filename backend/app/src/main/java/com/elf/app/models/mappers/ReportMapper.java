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
        var resource = report.getResource();
        var role = report.getRole();
        var employee = report.getEmployee();
        return new ReportDto(
                report.getUuid(),
                report.getName(),
                resource != null ? resourceMapper.apply(resource) : null,
                role != null ? roleMapper.apply(role) : null,
                employee != null ? employeeMapper.apply(employee) : null,
                report.getOcurrenceDescription(),
                report.getLatitude(),
                report.getLongitude()
        );
    }
}
