package com.elf.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.Forbidden;

import com.elf.app.dtos.ExceptionDto;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> NotFoundException(NotFoundException e) {
        new ExceptionDto(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionDto(HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(Forbidden.class)
    public ResponseEntity<ExceptionDto> ForbiddenException(Forbidden e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ExceptionDto(HttpStatus.FORBIDDEN, e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDto> RuntimeException(RuntimeException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionDto(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionDto> IllegalArgumentException(IllegalArgumentException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionDto(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDto> DataIntegrityViolationException(DataIntegrityViolationException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionDto(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ExceptionDto> InvalidRequestException(InvalidRequestException e) {

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                new ExceptionDto(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage()));
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ExceptionDto> ServiceException(ServiceException e) {

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                new ExceptionDto(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> Exception(Exception e) {

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(
                new ExceptionDto(HttpStatus.BAD_GATEWAY, e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> MethodArgumentNotValidException(MethodArgumentNotValidException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionDto(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

}