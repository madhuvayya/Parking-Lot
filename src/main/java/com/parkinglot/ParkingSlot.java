package com.parkinglot;

import java.util.*;

public class ParkingSlot {

    public final int parkingSlotCapacity;
    Map<Vehicle,ParkedDetails> vehicleParkedDetailsMap;
    boolean[] spots;

    public ParkingSlot(int parkingLotCapacity) {
        this.parkingSlotCapacity = parkingLotCapacity;
        vehicleParkedDetailsMap = new HashMap<>();
        spots = new boolean[parkingSlotCapacity];
        for(int i = 0; i < parkingSlotCapacity; i++){
            spots[i] = false;
        }
    }

    public void parkVehicle(Vehicle vehicle,Driver driver,ParkingSlot parkingSlot,ParkingAttendant attendant) {
        vehicleDistribution(vehicle,driver,parkingSlot,attendant);
    }

    private void vehicleDistribution(Vehicle vehicle, Driver driver,ParkingSlot parkingSlot,ParkingAttendant attendant) {
        int spot = 0;
        for(int i = 0; i < parkingSlotCapacity; i++){
            if(!spots[i]) {
                spots[i] = true;
                spot = i+1;
                break;
            }
        }
        ParkedDetails parkedDetails = new ParkedDetails(vehicle,driver,attendant,parkingSlot,
                spot, System.currentTimeMillis());
        vehicleParkedDetailsMap.put(vehicle,parkedDetails);
    }

    public void unParkVehicle(Vehicle vehicle) {
        if(!vehicleParkedDetailsMap.containsKey(vehicle))
            throw new ParkingLotException(ParkingLotException.ExceptionType.NOT_IN_SLOTS_LIST, "Not in the parked list");
        this.setParkedSpot(vehicle);
        vehicleParkedDetailsMap.remove(vehicle);
    }

    public int getParkedSpot(Vehicle vehicle) {
        return vehicleParkedDetailsMap.get(vehicle).getSpot();
    }

    public int getOccupiedSpots(){
        return vehicleParkedDetailsMap.size();
    }

    public long getParkedTime(Vehicle vehicle) {
        return System.currentTimeMillis() - vehicleParkedDetailsMap.get(vehicle).getParkedTime();
    }

    private void setParkedSpot(Vehicle vehicle) {
        int parkedSpot = this.getParkedSpot(vehicle);
        spots[parkedSpot-1] = false;
    }
}
