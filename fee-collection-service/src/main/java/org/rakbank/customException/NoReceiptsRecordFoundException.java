package org.rakbank.customException;

public class NoReceiptsRecordFoundException extends RuntimeException{
    public NoReceiptsRecordFoundException(String message) {
        super(message);
    }
}
