package com.elf.app.models.mappers;

import java.util.function.Function;
import com.elf.app.dtos.PermitsDto;
import com.elf.app.models.Permits;

public class PermitsMapper implements Function<Permits, PermitsDto> {
    
    @Override
    public PermitsDto apply (Permits permits) {
        return new PermitsDto(
            permits.getUuid(),
            permits.getEmployee().getUuid(),
            permits.isAssignPermits(),
            permits.isRegisterUser(),
            permits.isViewApplicant(),
            permits.isViewReports(),
            permits.isRequestVacation(),
            permits.isRequestTermination(),
            permits.isRequestEmployeeTermination(),
            permits.isViewRequests(),
            permits.isRegisterResources()
        );
    }
}
