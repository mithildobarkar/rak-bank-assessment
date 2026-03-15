package org.rakbank.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rakbank.customException.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setup() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void shouldHandleServiceUnavailable() {

        ServiceUnavailableException ex =
                new ServiceUnavailableException("Service down");

        ResponseEntity<String> response =
                handler.handleServiceUnavailable(ex);

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE,
                response.getStatusCode());
    }

    @Test
    void shouldHandleStudentNotFound() {

        StudentNotFoundException ex =
                new StudentNotFoundException("Student not found");

        ResponseEntity<String> response =
                handler.handleStudentNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND,
                response.getStatusCode());
    }

    @Test
    void shouldHandleNoReceiptsFound() {

        NoReceiptsRecordFoundException ex =
                new NoReceiptsRecordFoundException("No receipts");

        ResponseEntity<String> response =
                handler.handleNoReceiptsFound(ex);

        assertEquals(HttpStatus.NOT_FOUND,
                response.getStatusCode());
    }
}