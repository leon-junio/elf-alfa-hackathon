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
import com.elf.app.dtos.TerminationRequestDto;
import com.elf.app.exceptions.InvalidRequestException;
import com.elf.app.exceptions.ServiceException;
import com.elf.app.requests.TerminationRequestRequest;
import com.elf.app.services.TerminationRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/terminationRequest")
@RequiredArgsConstructor

public class TerminationRequestController extends BaseController {

    private final TerminationRequestService terminationRequestService;

    @GetMapping("/{uuid}")
    public ResponseEntity<TerminationRequestDto> getTerminationRequestData(
            @PathVariable(value = "uuid") String uuid) throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(terminationRequestService.getbyUuid((uuid)));
    }

    @GetMapping
    public ResponseEntity<List<TerminationRequestDto>> getAllTerminationRequests(
            @RequestParam(required = false) Integer per_page,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer page) throws ServiceException, NotFoundException {
        return ResponseEntity.ok(terminationRequestService.getAll(paginate(page, per_page, sort)));
    }

    @PostMapping
    public ResponseEntity<TerminationRequestDto> createTerminationRequest(@RequestBody @Valid TerminationRequestRequest request)
            throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(terminationRequestService.create(request));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<TerminationRequestDto> updateTerminationRequest(@RequestBody @Valid TerminationRequestRequest request,
            @PathVariable(value = "uuid") String uuid) throws ServiceException, InvalidRequestException {
        return ResponseEntity.ok(terminationRequestService.update(uuid, request));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteTerminationRequest(@PathVariable(value = "uuid") String uuid)
            throws ServiceException, InvalidRequestException {
        terminationRequestService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
