package com.elf.app.models.mappers;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.elf.app.dtos.EmployeeDto;
import com.elf.app.models.Employee;

@Service
public class EmployeeMapper implements Function<Employee,EmployeeDto>{

private final RoleMapper roleMapper = new RoleMapper();

    @Override
public EmployeeDto apply(Employee arg0) {
    var dependents = arg0.getDependents();
    return new EmployeeDto(
        arg0.getUuid(),
        arg0.getName(),
        arg0.getMotherName(),
        arg0.getFatherName(),
        arg0.isGender(),
        arg0.getCivilStatus().ordinal(),
        arg0.getSchoolingType().ordinal(),
        arg0.getRaceType().ordinal(),
        arg0.getBirthday(),
        arg0.getNationality(),
        arg0.getCountryBirth(),
        arg0.getStateBirth(),
        arg0.getCityBirth(),
        arg0.getShoeSize(),
        arg0.getPantsSize(),
        arg0.getShirtSize(),
        arg0.getPhoneNumber1(),
        arg0.getPhoneNumber2(),
        arg0.getEmail(),
        arg0.getAddress(),
        arg0.getNumber(),
        arg0.getComplement(),
        arg0.getNeighbor(),
        arg0.getCity(),
        arg0.getState(),
        arg0.getCep(),
        arg0.getCountry(),
        arg0.getPublicAreaType().ordinal(),
        arg0.getRg(),
        arg0.getRgIssuer(),
        arg0.getRgIssuerState(),
        arg0.getRgIssuerCity(),
        arg0.getRgExpeditionDate(),
        arg0.getCpf(),
        arg0.getPis(),
        roleMapper.apply(arg0.getRole()),
        arg0.isPcd(),
        arg0.isHosted(),
        arg0.getFileRgPath(),
        arg0.getFileCpfPath(),
        arg0.getFileCvPath(),
        arg0.getFileCnhPath(),
        arg0.getFileReservistPath(),
        arg0.isHasFriend(),
        arg0.getFriendName(),
        arg0.getFriendRole(),
        arg0.getFriendCity(),
        arg0.isCandidate(),
        arg0.getEmployeeStatus().ordinal(),
        dependents != null ? dependents.stream().map(new DependentMapper()::apply).toList() : null
    );
}
    
}
