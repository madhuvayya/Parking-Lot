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
        if(vehicleList.size() < parkingLotCapacity) {
            boolean added = vehicleList.add(vehicleNumber);
            this.isFull();
            return added;
        }
        return false;
    }

    private void isFull() {
        if (vehicleList.size() == parkingLotCapacity ) {
            parkingLotOwner.full();
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.PARKING_LOT_IS_FULL,
                    "Parking lot is full");
        }
    }

    public boolean unParkVehicle(String vehicleNumber) {
        checkForException(vehicleNumber);
        if(!vehicleList.contains(vehicleNumber))
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.NOT_IN_THE_PARKED_LIST,
                                                "Not in the parked list");
        boolean removed = vehicleList.remove(vehicleNumber);
        int availableSpaces = parkingLotCapacity - vehicleList.size();
        System.out.println("Parking lot has"+ availableSpaces +" spaces");
        return removed;
    }

    private void checkForException(String vehicleNumber){
        if(vehicleNumber == null)
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.ENTERED_NULL,"Entered null");
        if(vehicleNumber.length() == 0)
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.ENTERED_EMPTY,"Entered empty");
    }
}
