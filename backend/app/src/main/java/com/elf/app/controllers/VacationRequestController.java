package com.elf.app.controllers;

import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.elf.app.dtos.VacationRequestDto;
import com.elf.app.exceptions.InvalidRequestException;
import com.elf.app.exceptions.ServiceException;
import com.elf.app.requests.VacationRequestRequest;
import com.elf.app.services.VacationRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vacationRequest")
@RequiredArgsConstructor

public class VacationRequestController extends BaseController {

    private final VacationRequestService vacationRequestService;

    @GetMapping("/{uuid}")
    public ResponseEntity<VacationRequestDto> getVacationRequestData(
            @PathVariable(value = "uuid") String uuid) throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(vacationRequestService.getbyUuid((uuid)));
    }

    @GetMapping
    public ResponseEntity<List<VacationRequestDto>> getAllVacationRequests(
            @RequestParam(required = false) Integer per_page,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer page) throws ServiceException, NotFoundException {
        return ResponseEntity.ok(vacationRequestService.getAll(paginate(page, per_page, sort)));
    }

    @PostMapping
    public ResponseEntity<VacationRequestDto> createVacationRequest(@RequestBody @Valid VacationRequestRequest request)
            throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(vacationRequestService.create(request));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<VacationRequestDto> updateVacationRequest(@RequestBody @Valid VacationRequestRequest request,
            @PathVariable(value = "uuid") String uuid) throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(vacationRequestService.update(uuid, request));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteVacationRequest(@PathVariable(value = "uuid") String uuid)
            throws ServiceException, InvalidRequestException {
        vacationRequestService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
