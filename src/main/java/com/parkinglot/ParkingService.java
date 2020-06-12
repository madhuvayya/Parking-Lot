package com.parkinglot;

import java.util.LinkedList;
import java.util.List;

public class ParkingService {

    private final int PARKING_LOT_CAPACITY;
    List<Vehicle> vehicleList;

    public ParkingService(int parkingLotCapacity) {
        this.PARKING_LOT_CAPACITY = parkingLotCapacity;
        vehicleList = new LinkedList<>();
    }

    ParkingLotOwner parkingLotOwner= new ParkingLotOwner();
    AirportSecurity airportSecurity = new AirportSecurity();

    public boolean parkVehicle(Vehicle vehicle) {
        checkForException(vehicle);
        if (vehicleList.size() == PARKING_LOT_CAPACITY)
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.PARKING_LOT_IS_FULL,
                    "Parking lot is full");
        if(vehicleList.contains(vehicle))
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.EXISTING,
                                                "Entered vehicle number existing in the list");
        boolean added = vehicleList.add(vehicle);
        this.isFull();
        return added;
    }

    private void isFull() {
        if (vehicleList.size() == PARKING_LOT_CAPACITY) {
            parkingLotOwner.full();
            airportSecurity.full();
        }
    }

    public boolean unParkVehicle(Vehicle vehicle) {
        checkForException(vehicle);
        if(!vehicleList.contains(vehicle))
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.NOT_IN_THE_PARKED_LIST,
                                                "Not in the parked list");
        boolean removed = vehicleList.remove(vehicle);
        parkingLotOwner.availableSpace(PARKING_LOT_CAPACITY - vehicleList.size());
        return removed;
    }

    private void checkForException(Vehicle vehicle){
        if(vehicle == null)
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.ENTERED_NULL,"Entered null");
    }
}
