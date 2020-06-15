package com.parkinglot;

import java.util.List;

public class ParkingLotService {

    private final int numberOfParkingSlots;
    List<ParkingSlot> parkingSlots;
    int currentSlot = 0;

    public ParkingLotService(List<ParkingSlot> parkingLots) {
        this.parkingSlots = parkingLots;
        this.numberOfParkingSlots = parkingLots.size();
    }

    public int getNumberOfParkingSlots(){
        return parkingSlots.size();
    }

    public void parkVehicle(Vehicle vehicle,Driver driver){
        this.directVehicle(vehicle,driver);
    }

    private void directVehicle(Vehicle vehicle,Driver driver) {
        for (int slot = 0; slot <= numberOfParkingSlots; slot++) {
           if (parkingSlots.get(currentSlot).getOccupiedSpots() < parkingSlots.get(currentSlot).PARKING_SLOT_CAPACITY){
               parkingSlots.get(currentSlot).parkVehicle(vehicle, driver);
               return;
           }
           currentSlot++;
           if(currentSlot > numberOfParkingSlots -1)
                currentSlot = 0;
           this.checkParkingSlotsFull();
       }
    }

    private void checkParkingSlotsFull() {
        int numberOfLotsFull = 0;
        for (ParkingSlot parkingLot: parkingSlots) {
            if(parkingLot.getOccupiedSpots() == parkingLot.PARKING_SLOT_CAPACITY)
                numberOfLotsFull++;
        }
        if(numberOfLotsFull == numberOfParkingSlots)
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.PARKING_LOTS_ARE_FULL,"All lots are full");
    }

    public int getOccupiedSpotsInALot(ParkingSlot parkingLot) {
        for (int i = 0; i < numberOfParkingSlots; i++) {
            if (parkingSlots.get(i).equals(parkingLot))
                return parkingSlots.get(i).getOccupiedSpots();
        }
        throw new ParkingServiceException(ParkingServiceException.ExceptionType.NOT_IN_SLOTS_LIST,"Not in the lots list");
    }

    public void unParkVehicle(Vehicle vehicle, ParkingSlot parkingLot) {
        parkingSlots.get(1).unParkVehicle(vehicle);
    }
}
