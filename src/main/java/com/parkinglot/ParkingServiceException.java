package com.parkinglot;

public class ParkingServiceException extends RuntimeException {
    public enum ExceptionType {
        ENTERED_NULL,
        ENTERED_EMPTY,
        NOT_IN_THE_PARKED_LIST,
        PARKING_LOT_IS_FULL;
    }
    ExceptionType type;

    public ParkingServiceException(ExceptionType type,String message) {
        super(message);
        this.type = type;
    }
}
