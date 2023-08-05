package com.elf.app.controllers;

import java.io.File;
import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elf.app.dtos.ResourceDto;
import com.elf.app.exceptions.InvalidRequestException;
import com.elf.app.exceptions.ServiceException;
import com.elf.app.requests.ResourceRequest;
import com.elf.app.services.ResourceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/resource")
@RequiredArgsConstructor
public class ResourceController extends BaseController {

    private final ResourceService resourceService;

    @GetMapping("/{uuid}")
    public ResponseEntity<ResourceDto> getResourceData(
            @PathVariable(value = "uuid") String uuid) throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(resourceService.getbyUuid(uuid));
    }

    @GetMapping
    public ResponseEntity<List<ResourceDto>> getAllResources(
            @RequestParam(required = false) Integer per_page,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer page) throws ServiceException, NotFoundException {
        return ResponseEntity.ok(resourceService.getAll(paginate(page, per_page, sort)));
    }

    @PostMapping
    public ResponseEntity<ResourceDto> createResource(@ModelAttribute @Valid ResourceRequest request)
            throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(resourceService.create(request));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ResourceDto> updateResource(@ModelAttribute @Valid ResourceRequest request,
            @PathVariable(value = "uuid") String uuid) throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(resourceService.update(uuid, request));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ResourceDto> deleteResource(@PathVariable(value = "uuid") String uuid)
            throws ServiceException, InvalidRequestException {
        resourceService.delete(uuid);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/documents/rg/{uuid}")
    public ResponseEntity<?> getResourceDocument(@PathVariable(value = "uuid") String uuid)
            throws ServiceException, InvalidRequestException {
        File document = resourceService.getResourceDocument(uuid);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(document);
    }

}
