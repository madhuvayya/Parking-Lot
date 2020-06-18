package com.parkinglot;

import java.util.Objects;

public class Vehicle {

    private final VehicleProperty vehicleSize;
    private final String vehicleNumber;
    private final VehicleProperty vehicleColor;
    private final VehicleProperty vehicleBrand;

    enum VehicleProperty {
        SMALL,
        LARGE,
        WHITE,
        BLACK,
        RED,
        TOYOTA,
        BMW,
        BLUE
    }


    public Vehicle(String vehicleNumber, VehicleProperty vehicleSize, VehicleProperty vehicleColor, VehicleProperty vehicleBrand) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleSize = vehicleSize;
        this.vehicleColor = vehicleColor;
        this.vehicleBrand = vehicleBrand;
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

    public VehicleProperty getVehicleSize() {
        return vehicleSize;
    }

    public VehicleProperty getVehicleColor() {
        return vehicleColor;
    }

    public VehicleProperty getVehicleBrand() {
        return vehicleBrand;
    }
}
