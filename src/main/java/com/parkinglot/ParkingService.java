package com.parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingService {
    private static final int PARKING_LOT_CAPACITY = 5;
    List<String> vehicleList = new ArrayList<>(PARKING_LOT_CAPACITY);

    ParkingLotOwner parkingLotOwner= new ParkingLotOwner();

    public boolean parkVehicle(String vehicleNumber) {
        checkForException(vehicleNumber);
        if(vehicleList.size() < PARKING_LOT_CAPACITY) {
            boolean added = vehicleList.add(vehicleNumber);
            this.isFull();
            return added;
        }
        return false;
    }

    private void isFull() {
        if (vehicleList.size() == PARKING_LOT_CAPACITY ) {
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
        int availableSpaces = PARKING_LOT_CAPACITY - vehicleList.size();
        System.out.println("Parking lot has"+ availableSpaces +" spaces");
        return removed;
    }

    private void checkForException(String vehicleNumber){
        if(vehicleNumber == null)
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.ENTERED_NULL,"Entered null");
        if(vehicleNumber == "")
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.ENTERED_EMPTY,"Entered empty");
    }
}
