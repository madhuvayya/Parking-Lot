package com.parkinglot;

import java.awt.*;
import java.util.Objects;

public class Vehicle {

    private final VehicleSize vehicleSize;
    private final String vehicleNumber;
    private final VehicleColor vehicleColor;

    public VehicleColor getVehicleColor() {
        return vehicleColor;
    }

    enum VehicleSize {
        SMALL
    }

    enum VehicleColor{
        White
    }


    public Vehicle(String vehicleNumber, VehicleSize vehicleSize, VehicleColor vehicleColor) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleSize = vehicleSize;
        this.vehicleColor = vehicleColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(vehicleNumber, vehicle.vehicleNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleNumber);
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public VehicleSize getVehicleSize() {
        return vehicleSize;
    }
}
