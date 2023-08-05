package com.elf.app.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidRequestException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidRequestException(String message) {
        super(message);
    }
}
