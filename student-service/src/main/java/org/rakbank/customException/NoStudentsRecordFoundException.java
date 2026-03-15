package org.rakbank.customException;

public class NoStudentsRecordFoundException extends RuntimeException{

    public NoStudentsRecordFoundException(String message) {
        super(message);
    }
}
