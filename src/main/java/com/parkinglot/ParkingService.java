package com.parkinglot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingService {

    private final int PARKING_LOT_CAPACITY = 5;
    Map<Vehicle,ParkedDetails> vehicleParkedDetailsMap;
    List<ParkedDetails> parkedDetailsList;
    boolean[] spots;

    ParkingLotOwner parkingLotOwner= new ParkingLotOwner();
    AirportSecurity airportSecurity = new AirportSecurity();

    public ParkingService() {
        vehicleParkedDetailsMap = new HashMap<>();
        parkedDetailsList = new ArrayList<>();
        spots = new boolean[PARKING_LOT_CAPACITY];
        for(int i=0; i < PARKING_LOT_CAPACITY;i++){
            spots[i] = false;
        }
    }

    public void parkVehicle(Vehicle vehicle) {
        checkForException(vehicle);
        if (vehicleParkedDetailsMap.size() == PARKING_LOT_CAPACITY)
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.PARKING_LOT_IS_FULL,
                    "Parking lot is full");
        if(vehicleParkedDetailsMap.containsKey(vehicle))
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.EXISTING,
                                                "Entered vehicle number existing in the list");
        int spot = this.getParkingSpot(vehicle);
        ParkedDetails parkedDetails = new ParkedDetails(vehicle,spot, System.currentTimeMillis());
        vehicleParkedDetailsMap.put(vehicle,parkedDetails);
        parkedDetailsList.add(parkedDetails);
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
        this.setParkedSpot(vehicle);
        vehicleParkedDetailsMap.remove(vehicle);
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

    private int getParkingSpot(Vehicle vehicle){
       for(int i = 0; i < PARKING_LOT_CAPACITY ;i++){
           if(!spots[i]) {
               spots[i] = true;
               if(vehicle.getDriver().equals(Driver.DISABLED))
                   return this.getParkingSpotForDisabled(i+1);
               return i + 1;
           }
       }
       return -1;
    }

    private int getParkingSpotForDisabled(int spot) {
        parkedDetailsList.get(0).setSpot(spot);
        spots[spot-1] = true;
        return 1;
    }

    private void setParkedSpot(Vehicle vehicle) {
        int parkedSpot = this.getParkedSpot(vehicle);
        spots[parkedSpot-1] = false;
    }
}
