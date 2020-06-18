package com.parkinglot.exception;

public class ParkingLotException extends RuntimeException {
    public enum ExceptionType {
        ENTERED_NULL,
        NOT_IN_THE_PARKED_LIST,
        PARKING_LOT_IS_FULL,
        EXISTING,
        PARKING_LOTS_ARE_FULL,
        NOT_IN_SLOTS_LIST
    }
    public ExceptionType type;

    public ParkingLotException(ExceptionType type, String message) {
        super(message);
        this.type = type;
    }
}
