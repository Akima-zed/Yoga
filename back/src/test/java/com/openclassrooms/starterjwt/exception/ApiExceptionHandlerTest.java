package com.openclassrooms.starterjwt.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class ApiExceptionHandlerTest {
    private final ApiExceptionHandler handler = new ApiExceptionHandler();

    @Test
    void handleNotFound_shouldReturnNotFound() {
        NotFoundException ex = new NotFoundException("Not found");
        ResponseEntity<ApiError> response = handler.handleNotFound(ex);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Not found", response.getBody().getMessage());
    }

    @Test
    void handleBadRequest_shouldReturnBadRequest() {
        BadRequestException ex = new BadRequestException("Bad request");
        ResponseEntity<ApiError> response = handler.handleBadRequest(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Bad request", response.getBody().getMessage());
    }

    @Test
    void handleGeneric_shouldReturnInternalServerError() {
        Exception ex = new Exception("Erreur interne");
        ResponseEntity<ApiError> response = handler.handleGeneric(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erreur interne", response.getBody().getMessage());
    }

    @Test
    void handleUnauthorized_shouldReturnForbidden() {
        UnauthorizedException ex = new UnauthorizedException("Non autorisé");
        ResponseEntity<ApiError> response = handler.handleUnauthorized(ex);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Non autorisé", response.getBody().getMessage());
    }
}
