package com.parkinglot;

import java.util.HashMap;
import java.util.Map;

public class ParkingService {

    private final int PARKING_LOT_CAPACITY;
    Map<Vehicle,ParkedDetails> vehicleParkedDetailsMap;
    int occupiedSpots = 0;

    public ParkingService(int parkingLotCapacity) {
        this.PARKING_LOT_CAPACITY = parkingLotCapacity;
        vehicleParkedDetailsMap = new HashMap<>();
    }

    ParkingLotOwner parkingLotOwner= new ParkingLotOwner();
    AirportSecurity airportSecurity = new AirportSecurity();

    public void parkVehicle(Vehicle vehicle) {
        checkForException(vehicle);
        if (vehicleParkedDetailsMap.size() == PARKING_LOT_CAPACITY)
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.PARKING_LOT_IS_FULL,
                    "Parking lot is full");
        if(vehicleParkedDetailsMap.containsKey(vehicle))
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.EXISTING,
                                                "Entered vehicle number existing in the list");
        ParkedDetails parkedDetails = new ParkedDetails(++occupiedSpots, System.currentTimeMillis());
        vehicleParkedDetailsMap.put(vehicle,parkedDetails);
        this.isFull();
    }

    private void isFull() {
        if (vehicleParkedDetailsMap.size() == PARKING_LOT_CAPACITY) {
            parkingLotOwner.full(true);
            airportSecurity.full(true);
        }
    }

    public void unParkVehicle(Vehicle vehicle) {
        checkForException(vehicle);
        if(!vehicleParkedDetailsMap.containsKey(vehicle))
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.NOT_IN_THE_PARKED_LIST,
                                                "Not in the parked list");
        vehicleParkedDetailsMap.remove(vehicle);
        occupiedSpots--;
        parkingLotOwner.availableSpace(PARKING_LOT_CAPACITY - vehicleParkedDetailsMap.size());
    }

    private void checkForException(Vehicle vehicle){
        if(vehicle == null)
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.ENTERED_NULL,"Entered null");
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

}
