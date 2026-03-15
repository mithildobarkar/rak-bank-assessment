package org.rakbank.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rakbank.customException.GlobalExceptionHandler;
import org.rakbank.customException.NoStudentsRecordFoundException;
import org.rakbank.customException.StudentAlreadyExistsException;
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
    void shouldHandleStudentAlreadyExistsException() {

        StudentAlreadyExistsException ex =
                new StudentAlreadyExistsException("Student already exists");

        ResponseEntity<String> response =
                handler.handleStudentAlreadyExists(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Student already exists", response.getBody());
    }

    @Test
    void shouldHandleNoStudentsRecordFoundException() {

        NoStudentsRecordFoundException ex =
                new NoStudentsRecordFoundException("Student not found");

        ResponseEntity<String> response =
                handler.handleNoStudentsRecordFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Student not found", response.getBody());
    }

}