package com.parkinglot;

public class Vehicle {
    private final String vehicleNumber;
    private final Driver driver;

    public Vehicle(String vehicleNumber,Driver driver) {
        this.vehicleNumber = vehicleNumber;
        this.driver = driver;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public Driver getDriver() {
        return driver;
    }
}
