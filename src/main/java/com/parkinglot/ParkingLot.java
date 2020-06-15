package com.parkinglot;

import java.util.*;

public class ParkingLot {

    public final int PARKING_LOT_CAPACITY;
    Map<Vehicle,ParkedDetails> vehicleParkedDetailsMap;
    List<ParkedDetails> parkedDetailsList;
    boolean[] spots;

    ParkingLotOwner parkingLotOwner= new ParkingLotOwner();
    AirportSecurity airportSecurity = new AirportSecurity();

    public ParkingLot(int parkingLotCapacity) {
        this.PARKING_LOT_CAPACITY = parkingLotCapacity;
        vehicleParkedDetailsMap = new HashMap<>();
        parkedDetailsList = new ArrayList<>();
        spots = new boolean[PARKING_LOT_CAPACITY];
        for(int i=0; i < PARKING_LOT_CAPACITY;i++){
            spots[i] = false;
        }
    }

    public void parkVehicle(Vehicle vehicle,Driver driver) {
        if(vehicle == null && driver == null)
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.ENTERED_NULL,"Entered null");
        if (parkedDetailsList.size() == PARKING_LOT_CAPACITY)
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.PARKING_LOT_IS_FULL,
                    "Parking lot is full");
        if(vehicleParkedDetailsMap.containsKey(vehicle))
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.EXISTING,
                                                "Entered vehicle number existing in the list");
        vehicleDistribution(vehicle,driver);
    }

    private void vehicleDistribution(Vehicle vehicle, Driver driver) {
        int spot = 0;
        for(int i = 0; i < PARKING_LOT_CAPACITY ;i++){
            if(!spots[i]) {
                spots[i] = true;
                spot = i+1;
                break;
            }
        }

        ParkedDetails parkedDetails = new ParkedDetails(vehicle,driver,spot, System.currentTimeMillis());
        parkedDetailsList.add(parkedDetails);
        int swapSpot = 0;
        if(driver.equals(Driver.DISABLED)) {
            for (int i = 0; i < parkedDetailsList.size(); i++) {
                if (parkedDetailsList.get(i).getDriver().equals(Driver.ABLED)) {
                    swapSpot = i+1;
                    break;
                }
            }
            parkedDetailsList.get(swapSpot-1).setSpot(spot);
            parkedDetailsList.get(spot-1).setSpot(swapSpot);
            Collections.swap(parkedDetailsList, swapSpot-1, spot-1);
        }
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
        if(vehicle == null)
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.ENTERED_NULL,"Entered null");
        if(!vehicleParkedDetailsMap.containsKey(vehicle))
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.NOT_IN_THE_PARKED_LIST,
                                                "Not in the parked list");
        this.setParkedSpot(vehicle);
        vehicleParkedDetailsMap.remove(vehicle);
        parkingLotOwner.availableSpace(PARKING_LOT_CAPACITY - vehicleParkedDetailsMap.size());
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

    private void setParkedSpot(Vehicle vehicle) {
        int parkedSpot = this.getParkedSpot(vehicle);
        spots[parkedSpot-1] = false;
    }
}
