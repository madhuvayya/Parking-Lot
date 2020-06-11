package com.parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingService {

    private final int parkingLotCapacity;
    List<String> vehicleList;

    public ParkingService(int parkingLotCapacity) {
        this.parkingLotCapacity = parkingLotCapacity;
        vehicleList = new ArrayList<>(parkingLotCapacity);
    }

    ParkingLotOwner parkingLotOwner= new ParkingLotOwner();

    public boolean parkVehicle(String vehicleNumber) {
        checkForException(vehicleNumber);
        if (vehicleList.size() == parkingLotCapacity )
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.PARKING_LOT_IS_FULL,
                    "Parking lot is full");
        if(vehicleList.contains(vehicleNumber))
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.EXISTING,
                                                "Entered vehicle number existing in the list");
        boolean added = vehicleList.add(vehicleNumber);
        this.isFull();
        return added;
    }

    private void isFull() {
        if (vehicleList.size() == parkingLotCapacity ) {
            parkingLotOwner.full();
        }
    }

    public boolean unParkVehicle(String vehicleNumber) {
        checkForException(vehicleNumber);
        if(!vehicleList.contains(vehicleNumber))
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.NOT_IN_THE_PARKED_LIST,
                                                "Not in the parked list");
        return vehicleList.remove(vehicleNumber);
    }

    private void checkForException(String vehicleNumber){
        if(vehicleNumber == null)
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.ENTERED_NULL,"Entered null");
        if(vehicleNumber.length() == 0)
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.ENTERED_EMPTY,"Entered empty");
    }
}
