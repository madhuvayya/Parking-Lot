package com.parkinglot.service;

import com.parkinglot.*;
import com.parkinglot.enums.Driver;
import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.model.ParkedDetails;
import com.parkinglot.model.Vehicle;
import com.parkinglot.observers.AirportSecurity;
import com.parkinglot.observers.ParkingLotOwner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingLot {

    private int numberOfParkingSlots;
    public List<ParkingSlot> parkingSlots;

    ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
    AirportSecurity airportSecurity = new AirportSecurity();
    ParkingStrategy parkingStrategy = new ParkingStrategy();

    public ParkingLot() {
    }

    public ParkingLot(List<ParkingSlot> parkingSlots) {
        this.parkingSlots = parkingSlots;
        this.numberOfParkingSlots = parkingSlots.size();
    }

    public void parkVehicle(Vehicle vehicle, Driver driver, ParkingAttendant attendant){
        if(vehicle == null && driver == null)
            throw new ParkingLotException(ParkingLotException.ExceptionType.ENTERED_NULL,"Entered null");
        if(this.checkParkingSlotsFull())
            throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_LOTS_ARE_FULL,
                    "All lots are full");
        if(this.checkVehicleExistenceInParking(vehicle))
            throw new ParkingLotException(ParkingLotException.ExceptionType.EXISTING,"exiting in list");
        parkingStrategy.parkVehicle(parkingSlots,vehicle,driver,attendant);
        this.checkParkingSlotsFull();
    }

    private boolean checkParkingSlotsFull() {
        int numberOfSlotsFull = 0;
        for (ParkingSlot parkingSlot: parkingSlots) {
            if(parkingSlot.getOccupiedSpots() == parkingSlot.parkingSlotCapacity)
                numberOfSlotsFull++;
        }
        if(numberOfSlotsFull == numberOfParkingSlots) {
            parkingLotOwner.fullOrNot(true);
            airportSecurity.fullOrNot(true);
            return true;
        }
        return false;
    }

    public boolean checkVehicleExistenceInParking(Vehicle vehicle){
        List<Vehicle> vehicleList = this.getAllParkedVehicleDetails().stream()
                .map(ParkedDetails::getVehicle)
                .collect(Collectors.toList());
        return vehicleList.contains(vehicle);
    }

    public int getOccupiedSpotsInASlot(ParkingSlot parkingLot) {
        for (int i = 0; i < numberOfParkingSlots; i++) {
            if (parkingSlots.get(i).equals(parkingLot))
                return parkingSlots.get(i).getOccupiedSpots();
        }
        throw new ParkingLotException(ParkingLotException.ExceptionType.NOT_IN_SLOTS_LIST,"Not in the lots list");
    }

    public void unParkVehicle(Vehicle vehicle) {
        if(vehicle == null)
            throw new ParkingLotException(ParkingLotException.ExceptionType.ENTERED_NULL,"Entered null");
        if(!this.checkVehicleExistenceInParking(vehicle))
            throw new ParkingLotException(ParkingLotException.ExceptionType.EXISTING,"Not exiting in list");
        ParkingSlot parkingSlot = this.getParkedSlot(vehicle);
        if(this.checkParkingSlotsFull())
            parkingLotOwner.fullOrNot(false);
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

    private List<Vehicle> getListOfVehiclesInParkingLots() {
          return this.getAllParkedVehicleDetails().stream()
                  .map(ParkedDetails::getVehicle)
                  .collect(Collectors.toList());
    }

    public int getNumberOfVehiclesInParkingLot(){
        return this.getListOfVehiclesInParkingLots().size();
    }

    private List<ParkedDetails> getAllParkedVehicleDetails(){
        List<ParkedDetails> parkedDetailsList = new ArrayList<>();
        for (ParkingSlot parkingSlot : parkingSlots) {
            Collection<ParkedDetails> parkedDetailsListInASlot = parkingSlot.vehicleParkedDetailsMap.values();
            parkedDetailsList.addAll(parkedDetailsListInASlot);
        }
        return parkedDetailsList;
    }

    public List<ParkedDetails> getAllVehiclesBasedOnColor(Vehicle.VehicleProperty property) {
        return this.getAllParkedVehicleDetails().stream()
                .filter(parkedDetails -> parkedDetails.getVehicle().getVehicleColor().equals(property))
                .collect(Collectors.toList());
    }

    public List<ParkedDetails> getAllVehicleBasedOnColorBrand(Vehicle.VehicleProperty ...properties) {
        return this.getAllParkedVehicleDetails().stream()
                .filter(parkedDetails -> parkedDetails.getVehicle().getVehicleColor().equals(properties[0]))
                .filter(parkedDetails -> parkedDetails.getVehicle().getVehicleBrand().equals(properties[1]))
                .collect(Collectors.toList());
    }

    public List<ParkedDetails> getAllVehiclesBasedOnBrand(Vehicle.VehicleProperty property) {
        return this.getAllParkedVehicleDetails().stream()
                .filter(parkedDetails -> parkedDetails.getVehicle().getVehicleBrand().equals(property))
                .collect(Collectors.toList());
    }

    public List<Vehicle> getAllVehiclesBasedOnTime(long duration) {
        return this.getAllParkedVehicleDetails().stream()
                .filter(parkedDetails -> (parkedDetails.getParkedTime() - System.currentTimeMillis()) * 0.000016667 <= duration)
                .map(ParkedDetails::getVehicle)
                .collect(Collectors.toList());
    }

    public List<Integer> getVehiclesInASlot(ParkingSlot parkingSlot) {
        return this.getAllParkedVehicleDetails().stream()
                .filter(parkedDetails -> parkedDetails.getParkedSlot().equals(parkingSlot))
                .filter(parkedDetails -> parkedDetails.getVehicle().getVehicleSize().equals(Vehicle.VehicleProperty.SMALL))
                .filter(parkedDetails -> parkedDetails.getDriver().equals(Driver.DISABLED))
                .map(ParkedDetails::getSpot)
                .collect(Collectors.toList());
    }

    public List<String> getAllVehicleNumbers() {
        return this.getAllParkedVehicleDetails().stream()
                .map(parkedDetails -> parkedDetails.getVehicle().getVehicleNumber())
                .collect(Collectors.toList());
    }
}
