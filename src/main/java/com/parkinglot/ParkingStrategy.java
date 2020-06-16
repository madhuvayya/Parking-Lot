package com.parkinglot;

import java.util.List;

public class ParkingStrategy {
    private int currentSlot = 0;
    public void parkVehicle(List<ParkingSlot> parkingSlots,Vehicle vehicle,Driver driver){
        if(vehicle.getVehicleSize().equals(Vehicle.VehicleSize.SMALL) && driver.equals(Driver.ABLED)) {
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

        if(driver.equals(Driver.DISABLED)){
            int numberOfSlots = parkingSlots.size();
            for (int slot = 0; slot <= numberOfSlots; slot++) {
                if (parkingSlots.get(slot).getOccupiedSpots() < parkingSlots.get(slot).parkingSlotCapacity) {
                    parkingSlots.get(slot).parkVehicle(vehicle, driver);
                    return;
                }
            }
        }

        if(vehicle.getVehicleSize().equals(Vehicle.VehicleSize.LARGE)){
            int numberOfSlots = parkingSlots.size();
            int requiredSpots = 2;
            for (int slot = 0; slot < numberOfSlots; slot++) {
                int availableSpots = parkingSlots.get(slot).parkingSlotCapacity - parkingSlots.get(slot).getOccupiedSpots();
                if (availableSpots >= requiredSpots) {
                    parkingSlots.get(slot).parkVehicle(vehicle, driver);
                    return;
                }
            }
        }
    }
}
