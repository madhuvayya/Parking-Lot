package com.parkinglot;

import java.util.List;

public class ParkingStrategy {
    private int currentSlot = 0;
    public void parkVehicle(List<ParkingSlot> parkingSlots,Vehicle vehicle,Driver driver){
        if(vehicle.getVehicleSize().equals(Vehicle.VehicleSize.SMALL)) {
            int numberOfSlots = parkingSlots.size();
            for (int slot = 0; slot <= numberOfSlots; slot++) {
                if (currentSlot == numberOfSlots)
                    currentSlot = 0;
                if (parkingSlots.get(currentSlot).getOccupiedSpots() < parkingSlots.get(currentSlot).parkingSlotCapacity) {
                    parkingSlots.get(currentSlot).parkVehicle(vehicle, driver);
                    currentSlot++;
                    return;
                }
                currentSlot++;
            }
        }
    }
}
