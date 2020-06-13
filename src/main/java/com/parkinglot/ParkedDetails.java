package com.parkinglot;

public class ParkedDetails {

    private final Vehicle vehicle;
    private final long parkedTime;
    private int spot;

    public ParkedDetails(Vehicle vehicle,int spot, long parkedTime) {
        this.spot = spot;
        this.parkedTime = parkedTime;
        this.vehicle = vehicle;
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
}
