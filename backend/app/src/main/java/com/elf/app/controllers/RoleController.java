package com.elf.app.controllers;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elf.app.dtos.RoleDto;
import com.elf.app.exceptions.ServiceException;
import com.elf.app.services.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController extends BaseController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles(
            @RequestParam(required = false) Integer per_page,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer page) throws ServiceException, NotFoundException {
        return ResponseEntity.ok(roleService.getAll(paginate(page, per_page, sort)));
    }
}
