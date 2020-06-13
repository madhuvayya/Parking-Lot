package com.parkinglot;

public class ParkedDetails {

    private final long parkedTime;
    private final int spot;

    public ParkedDetails(int spot, long parkedTime) {
        this.spot = spot;
        this.parkedTime = parkedTime;
    }

    public long getParkedTime() {
        return parkedTime;
    }

    public int getSpot() {
        return spot;
    }
}
