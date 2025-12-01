package com.openclassrooms.starterjwt.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;



public class ApiError {
    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String error;
    private String message;

    public ApiError(HttpStatus status, String message) {
        this.status = status.value();
        this.error = status.name();
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
