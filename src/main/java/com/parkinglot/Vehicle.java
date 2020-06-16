package com.parkinglot;

import java.util.Objects;

public class Vehicle {

    private final VehicleSize vehicleSize;
    private final String vehicleNumber;
    private final VehicleColor vehicleColor;

    enum VehicleSize {
        SMALL,
        LARGE
    }

    enum VehicleColor{
        WHITE,
        BLACK,
        YELLOW,
        RED
    }

    enum VehicleBrand{

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
        return vehicleSize == vehicle.vehicleSize &&
                Objects.equals(vehicleNumber, vehicle.vehicleNumber) &&
                vehicleColor == vehicle.vehicleColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleSize, vehicleNumber, vehicleColor);
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public VehicleSize getVehicleSize() {
        return vehicleSize;
    }

    public VehicleColor getVehicleColor() {
        return vehicleColor;
    }
}
