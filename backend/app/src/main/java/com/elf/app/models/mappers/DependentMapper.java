package com.elf.app.models.mappers;

import java.util.function.Function;

import com.elf.app.dtos.DependentDto;
import com.elf.app.models.Dependent;

public class DependentMapper implements Function<Dependent, DependentDto> {

    @Override
    public DependentDto apply(Dependent dependet) {
        return new DependentDto(
                dependet.getUuid(),
                dependet.getName(),
                dependet.getCpf(),
                dependet.getBirthday(),
                dependet.isGender(),
                dependet.getRelationship(),
                dependet.getEmployee().getUuid());
    }
}
