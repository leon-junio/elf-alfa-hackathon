package com.elf.app.models.mappers;

import java.util.function.Function;

import com.elf.app.dtos.ResourceDto;
import com.elf.app.models.Resource;


public class ResourceMapper implements Function<Resource, ResourceDto> {

    @Override
    public ResourceDto apply(Resource resource) {
        return new ResourceDto(
                resource.getUuid(),
                resource.getDescription(),
                resource.isAvailable(),
                resource.getFilePath()
        );
    }
}