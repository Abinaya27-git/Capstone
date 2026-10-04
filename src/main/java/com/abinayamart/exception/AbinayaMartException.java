package com.abinayamart.exception;

/** Base checked exception for AbinayaMart business / data errors. */
public class AbinayaMartException extends Exception {
    public AbinayaMartException(String message) {
        super(message);
    }

    public AbinayaMartException(String message, Throwable cause) {
        super(message, cause);
    }
}
