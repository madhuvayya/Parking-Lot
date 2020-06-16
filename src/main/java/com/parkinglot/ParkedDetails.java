package com.parkinglot;

import java.util.Objects;

public class ParkedDetails {

    private final Vehicle vehicle;
    private final long parkedTime;
    private final Driver driver;
    private int spot;

    public ParkedDetails(Vehicle vehicle,Driver driver,int spot, long parkedTime) {
        this.spot = spot;
        this.parkedTime = parkedTime;
        this.vehicle = vehicle;
        this.driver = driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkedDetails that = (ParkedDetails) o;
        return parkedTime == that.parkedTime &&
                spot == that.spot &&
                Objects.equals(vehicle, that.vehicle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicle, parkedTime, spot);
    }

    public long getParkedTime() {
        return parkedTime;
    }

    public void setSpot(int spot) {
        this.spot = spot;
    }

    public int getSpot() {
        return spot;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Driver getDriver() {
        return driver;
    }

}
