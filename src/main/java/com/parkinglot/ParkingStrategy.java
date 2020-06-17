package com.parkinglot;

import java.util.List;

public class ParkingStrategy {
    private int currentSlot = 0;
    public void parkVehicle(List<ParkingSlot> parkingSlots,Vehicle vehicle,Driver driver,ParkingAttendant attendant){
        if(vehicle.getVehicleSize().equals(Vehicle.VehicleProperty.SMALL) && driver.equals(Driver.ABLED)) {
            int numberOfSlots = parkingSlots.size();
            for (int slot = 0; slot <= numberOfSlots; slot++) {
                if (currentSlot == numberOfSlots)
                    currentSlot = 0;
                if (parkingSlots.get(currentSlot).getOccupiedSpots() < parkingSlots.get(currentSlot).parkingSlotCapacity) {
                    parkingSlots.get(currentSlot).parkVehicle(vehicle, driver,parkingSlots.get(currentSlot),attendant);
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
                    parkingSlots.get(slot).parkVehicle(vehicle, driver, parkingSlots.get(slot),attendant);
                    return;
                }
            }
        }

        if(vehicle.getVehicleSize().equals(Vehicle.VehicleProperty.LARGE)){
            int requiredSpots = 2;
            for (ParkingSlot parkingSlot : parkingSlots) {
                int availableSpots = parkingSlot.parkingSlotCapacity - parkingSlot.getOccupiedSpots();
                if (availableSpots >= requiredSpots) {
                    parkingSlot.parkVehicle(vehicle, driver,parkingSlot,attendant);
                    return;
                }
            }
        }
    }
}
