package com.parkinglot;

import java.util.List;

public class ParkingLotService {

    private final int numberOfParkingLots;
    List<ParkingLot> parkingLots;
    int currentLot = 0;

    public ParkingLotService(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
        this.numberOfParkingLots = parkingLots.size();
    }

    public int getNumberOfParkingLots(){
        return parkingLots.size();
    }

    public void parkVehicle(Vehicle vehicle,Driver driver){
        this.directVehicle(vehicle,driver);
    }

    private void directVehicle(Vehicle vehicle,Driver driver) {
        for (int lot = 0; lot <= numberOfParkingLots;lot++) {
           if (parkingLots.get(currentLot).getOccupiedSpots() < parkingLots.get(currentLot).PARKING_LOT_CAPACITY){
               parkingLots.get(currentLot).parkVehicle(vehicle, driver);
               return;
           }
           currentLot++;
           if(currentLot > numberOfParkingLots-1)
                currentLot = 0;
           this.checkParkingLotsFull();
       }
    }

    private void checkParkingLotsFull() {
        int numberOfLotsFull = 0;
        for (ParkingLot parkingLot: parkingLots) {
            if(parkingLot.getOccupiedSpots() == parkingLot.PARKING_LOT_CAPACITY)
                numberOfLotsFull++;
        }
        if(numberOfLotsFull == numberOfParkingLots)
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.PARKING_LOTS_ARE_FULL,"All lots are full");
    }

    public int getOccupiedSpotsInAlot(ParkingLot parkingLot) {
        return parkingLots.get(1).getOccupiedSpots();
    }
}
