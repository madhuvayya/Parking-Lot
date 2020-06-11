package com.parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingService {
    private static final int LOT_CAPACITY = 100;
    List<String> vehicleList = new ArrayList<>(LOT_CAPACITY);

    public boolean parkVehicle(String vehicleNumber) {
        checkForException(vehicleNumber);
        if(vehicleList.size() < LOT_CAPACITY)
            return vehicleList.add(vehicleNumber);
        return false;
    }

    public boolean unParkVehicle(String vehicleNumber) {
        checkForException(vehicleNumber);
        return vehicleList.remove(vehicleNumber);
    }

    private void checkForException(String vehicleNumber){
        if(vehicleNumber == null)
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.NULL,"Entered null");
        if(vehicleNumber == "")
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.EMPTY,"Entered empty");
    }
}
