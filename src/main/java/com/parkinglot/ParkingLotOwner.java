package com.parkinglot;

public class ParkingLotOwner {
    public void full(boolean fullStatus) {
        if(fullStatus)
            System.out.println("Parking lot is full");
    }

    public void availableSpace(int spaces) {
        System.out.println("Available spaces:" + spaces);
    }
}
