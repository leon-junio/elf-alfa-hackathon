package com.elf.app.models.mappers;

import java.util.function.Function;

import com.elf.app.dtos.DependentDto;
import com.elf.app.models.Dependent;

public class DependentMapper implements Function<Dependent, DependentDto> {

    @Override
    public DependentDto apply(Dependent arg0) {
        return new DependentDto(
                arg0.getUuid(),
                arg0.getCpf(),
                arg0.isGender(),
                arg0.getEmployee().getUuid());
    }
}
