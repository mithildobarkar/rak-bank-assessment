package org.rakbank.customException;

public class StudentAlreadyExistsException extends RuntimeException{

    public StudentAlreadyExistsException(String message) {
        super(message);
    }
}
