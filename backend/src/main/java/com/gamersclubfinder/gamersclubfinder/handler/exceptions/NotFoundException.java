package com.gamersclubfinder.gamersclubfinder.handler.exceptions;

import java.io.Serial;

public class NotFoundException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public NotFoundException(String resourceName, String id) {
        super(resourceName + " not found with id: " + id);
    }
}