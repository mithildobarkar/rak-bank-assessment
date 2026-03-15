package org.rakbank.customException;

public class ServiceUnavailableException extends RuntimeException{

    public ServiceUnavailableException(String message) {
        super(message);
    }
}
