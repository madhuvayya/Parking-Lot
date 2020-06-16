package com.parkinglot;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ParkingLotService {

    private final int numberOfParkingSlots;
    List<ParkingSlot> parkingSlots;
    List<Vehicle> totalVehicles = new ArrayList<>();
    int currentSlot = 0;
    ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
    AirportSecurity airportSecurity = new AirportSecurity();
    ParkingStrategy parkingStrategy = new ParkingStrategy();

    public ParkingLotService(List<ParkingSlot> parkingLots) {
        this.parkingSlots = parkingLots;
        this.numberOfParkingSlots = parkingLots.size();
    }

    public int getNumberOfParkingSlots(){
        return parkingSlots.size();
    }

    public void parkVehicle(Vehicle vehicle,Driver driver){
        if(vehicle == null && driver == null)
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.ENTERED_NULL,"Entered null");
        parkingStrategy.parkVehicle(parkingSlots,vehicle,driver);
        //this.directVehicle(vehicle,driver);
        this.checkParkingSlotsFull();
    }

//    private void directVehicle(Vehicle vehicle,Driver driver) {
//        for (int slot = 0; slot <= numberOfParkingSlots; slot++) {
//            if(currentSlot == numberOfParkingSlots)
//                currentSlot = 0;
//           if (parkingSlots.get(currentSlot).getOccupiedSpots() < parkingSlots.get(currentSlot).parkingSlotCapacity){
//               parkingSlots.get(currentSlot).parkVehicle(vehicle, driver);
//               currentSlot++;
//               return;
//           }
//           currentSlot++;
//           this.checkParkingSlotsFull();
//       }
//    }

    private void checkParkingSlotsFull() {
        int numberOfLotsFull = 0;
        for (ParkingSlot parkingLot: parkingSlots) {
            if(parkingLot.getOccupiedSpots() == parkingLot.parkingSlotCapacity)
                numberOfLotsFull++;
        }
        if(numberOfLotsFull == numberOfParkingSlots) {
            parkingLotOwner.full(true);
            airportSecurity.full(true);
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.PARKING_LOTS_ARE_FULL,
                    "All lots are full");
        }
    }

    public int getOccupiedSpotsInASlot(ParkingSlot parkingLot) {
        for (int i = 0; i < numberOfParkingSlots; i++) {
            if (parkingSlots.get(i).equals(parkingLot))
                return parkingSlots.get(i).getOccupiedSpots();
        }
        throw new ParkingServiceException(ParkingServiceException.ExceptionType.NOT_IN_SLOTS_LIST,"Not in the lots list");
    }

    public void unParkVehicle(Vehicle vehicle) {
        if(vehicle == null)
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.ENTERED_NULL,"Entered null");
        ParkingSlot parkingSlot = this.getParkedSlot(vehicle);
        if(parkingSlot == null)
            throw new ParkingServiceException(ParkingServiceException.ExceptionType.NOT_IN_SLOTS_LIST,
                                                "Entered vehicle not in list");
        parkingSlot.unParkVehicle(vehicle);
    }

    public ParkingSlot getParkedSlot(Vehicle vehicle) {
        for(ParkingSlot parkingSlot: parkingSlots)
            if(parkingSlot.vehicleParkedDetailsMap.containsKey(vehicle))
                return parkingSlot;
        return null;
    }

    public int getParkedSpot(Vehicle vehicle){
        ParkingSlot parkingSlot = this.getParkedSlot(vehicle);
        return parkingSlot.getParkedSpot(vehicle);
    }

    public int getAvailableSpotsInASlot(ParkingSlot parkingSlot) {
        return parkingSlot.parkingSlotCapacity - parkingSlot.vehicleParkedDetailsMap.size();
    }

    private List<Vehicle> getListOfVehiclesInParkingLot() {
        for(int i = 0;i<numberOfParkingSlots;i++){
            Set<Vehicle> vehicles = parkingSlots.get(i).vehicleParkedDetailsMap.keySet();
            List<ParkedDetails> parkedDetailsList = parkingSlots.get(i).parkedDetailsList;
            List<Vehicle> collect = parkedDetailsList.stream().map(ParkedDetails::getVehicle).collect(Collectors.toList());
            totalVehicles.addAll(collect);
        }
        return totalVehicles;
    }

    public int getNumberOfVehiclesInParkingLot(){
        return this.getListOfVehiclesInParkingLot().size();
    }
}
