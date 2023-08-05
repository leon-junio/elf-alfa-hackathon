package com.elf.app.services;

import org.springframework.stereotype.Service;
import java.io.File;
import java.util.List;

import org.springframework.data.domain.Pageable;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.elf.app.configs.FileHandler;
import com.elf.app.dtos.ResourceDto;
import com.elf.app.exceptions.InvalidRequestException;
import com.elf.app.exceptions.ServiceException;
import com.elf.app.models.Resource;
import com.elf.app.models.mappers.ResourceMapper;
import com.elf.app.repositories.ResourceRepository;
import com.elf.app.requests.ResourceRequest;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@RequiredArgsConstructor
public class ResourceService {
    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper = new ResourceMapper();

    /**
     * Busca por todos os resources
     * 
     * @return List of ResourceDto
     * @throws ServiceException
     */
    public List<ResourceDto> getAll(@NonNull Pageable paginate) throws ServiceException {
        try {
            var resources = resourceRepository.findAll(paginate);
            return resources.stream().map(resourceMapper).toList();
        } catch (Exception e) {
            throw new ServiceException("resource search failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Deleta um recurso criado
     * 
     * @param uuid String uuid do recurso
     * @throws ServiceException
     * @throws InvalidRequestException
     */
    public void delete(@NonNull String uuid) throws ServiceException {
        try {
            var resource = resourceRepository.findByUuid(uuid)
                    .orElseThrow(() -> new InvalidRequestException("Resource not found."));
            if (resource == null) {
                throw new ServiceException("resource not found");
            }
            resourceRepository.delete(resource);
            FileHandler.deleteFile(List.of(resource.getFilePath()));
        } catch (Exception e) {
            throw new ServiceException("resource deletion failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Busca por um recurso criado
     * 
     * @param uuid String uuid do recurso
     * @return ResourceDto
     * @throws ServiceException
     */
    public ResourceDto getbyUuid(@NonNull String uuid) throws ServiceException {
        try {
            var resource = resourceRepository.findByUuid(uuid)
                    .orElseThrow(() -> new InvalidRequestException("Resource not found."));
            if (resource == null) {
                throw new ServiceException("resource not found");
            }
            return resourceMapper.apply(resource);
        } catch (Exception e) {
            throw new ServiceException("resource search failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Cria um novo recurso
     * 
     * @param resourceDto ResourceDto
     * @return ResourceDto
     * @throws ServiceException
     */
    @Transactional(rollbackOn = Exception.class)
    public ResourceDto create(@Valid @NonNull ResourceRequest request) throws ServiceException {
        try {
            var resource = Resource.builder()
                    .description(request.getDescription())
                    .filePath(FileHandler.getFilePath(request.getFilePath()))
                    .isAvailable(request.isAvailable())
                    .build();
            resourceRepository.save(resource);
            FileHandler.saveFile(request.getFilePath(), resource.getFilePath());
            return resourceMapper.apply(resource);
        } catch (Exception e) {
            throw new ServiceException("resource creation failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Atualiza um recurso criado
     * 
     * @param uuid        String uuid do recurso
     * @param resourceDto ResourceDto
     * @return ResourceDto
     * @throws ServiceException
     * @throws InvalidRequestException
     */
    @Transactional(rollbackOn = Exception.class)
    public ResourceDto update(@NonNull String uuid, @Valid @NonNull ResourceRequest request) throws ServiceException {
        try {
            var resource = resourceRepository.findByUuid(uuid)
                    .orElseThrow(() -> new InvalidRequestException("Resource not found."));
            if (resource == null) {
                throw new ServiceException("resource not found");
            }
            FileHandler.deleteFile(List.of(resource.getFilePath()));
            resource.setDescription(request.getDescription());
            if (request.getFilePath() != null)
                resource.setFilePath(FileHandler.getFilePath(request.getFilePath()));
            resource.setAvailable(request.isAvailable());
            resourceRepository.save(resource);
            if (request.getFilePath() != null)
                FileHandler.saveFile(request.getFilePath(), resource.getFilePath());
            return resourceMapper.apply(resource);
        } catch (Exception e) {
            throw new ServiceException("resource update failed due to a service exception: " + e.getMessage());
        }
    }

    /**
     * Busca por um recurso criado especificamente pelo arquivo do mesmo
     * 
     * @param uuid String uuid do recurso
     * @return File de PDF do recurso
     * @throws ServiceException
     * @throws InvalidRequestException
     */
    public File getResourceDocument(@NonNull String uuid) throws ServiceException, InvalidRequestException {
        var resource = resourceRepository.findByUuid(uuid)
                .orElseThrow(() -> new InvalidRequestException("Resource not found."));
        try {
            File file = FileHandler.getFile(resource.getFilePath());
            if (file == null) {
                throw new ServiceException("Resource document not found.");
            }
            return file;
        } catch (Exception e) {
            throw new ServiceException("Resource search failed due to a service exception: " + e.getMessage());
        }
    }
}
