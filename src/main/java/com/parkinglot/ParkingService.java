package com.parkinglot;

import java.util.HashMap;
import java.util.Map;

public class ParkingService {

    private final int PARKING_LOT_CAPACITY;
    Map<Vehicle,Integer> vehicleMap;
    int occupiedSpots = 0;

    public ParkingService(int parkingLotCapacity) {
        this.PARKING_LOT_CAPACITY = parkingLotCapacity;
        vehicleMap = new HashMap<>();
    }

    ParkingLotOwner parkingLotOwner= new ParkingLotOwner();
    AirportSecurity airportSecurity = new AirportSecurity();
    ParkingAttendant parkingAttendant = new ParkingAttendant();

    public void parkVehicle(Vehicle vehicle) {
        checkForException(vehicle);
        if (vehicleMap.size() == PARKING_LOT_CAPACITY)
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.PARKING_LOT_IS_FULL,
                    "Parking lot is full");
        if(vehicleMap.containsKey(vehicle))
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.EXISTING,
                                                "Entered vehicle number existing in the list");
        vehicleMap.put(vehicle, ++occupiedSpots);
        parkingAttendant.parkVehicle(true);
        this.isFull();
    }

    private void isFull() {
        if (vehicleMap.size() == PARKING_LOT_CAPACITY) {
            parkingLotOwner.full(true);
            airportSecurity.full(true);
        }
    }

    public void unParkVehicle(Vehicle vehicle) {
        checkForException(vehicle);
        if(!vehicleMap.containsKey(vehicle))
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.NOT_IN_THE_PARKED_LIST,
                                                "Not in the parked list");
        vehicleMap.remove(vehicle);
        occupiedSpots--;
        parkingLotOwner.availableSpace(PARKING_LOT_CAPACITY - vehicleMap.size());
    }

    private void checkForException(Vehicle vehicle){
        if(vehicle == null)
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.ENTERED_NULL,"Entered null");
    }

    public int getParkedSpot(Vehicle vehicle) {
        return vehicleMap.get(vehicle);
    }

    public int getOccupiedSpots(){
        return vehicleMap.size();
    }
}
