package com.parkinglot;

public class ParkingServiceException extends RuntimeException {
    public enum ExceptionType {
        NULL,
        EMPTY
    }
    ExceptionType type;

    public ParkingServiceException(ExceptionType type,String message) {
        super(message);
        this.type = type;
    }
}
