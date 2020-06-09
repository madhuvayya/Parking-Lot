package com.parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingService {
    private static final int LOT_CAPACITY = 100;
    List<String> vehicleList = new ArrayList<>(LOT_CAPACITY);

    public boolean parkVehicle(String vehicleNumber) {
        if(vehicleNumber == null)
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.NULL,"Entered null");
        if(vehicleNumber == "")
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.EMPTY,"Entered empty");
        if(vehicleList.size() < LOT_CAPACITY) {
            vehicleList.add(vehicleNumber);
            return true;
        }
        return false;
    }
}
