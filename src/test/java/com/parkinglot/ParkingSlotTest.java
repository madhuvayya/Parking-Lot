package com.parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingSlotTest {

    ParkingSlot parkingSlot;
    Vehicle vehicle1;
    Vehicle vehicle2;
    Vehicle vehicle3;
    Vehicle vehicle4;
    Vehicle vehicle5;
    Vehicle vehicle6;

    @Before
    public void setUp() throws Exception {
        parkingSlot = new ParkingSlot(5);
        vehicle1 = new Vehicle("TA07EC3633", Vehicle.VehicleSize.SMALL, Vehicle.VehicleColor.WHITE);
        vehicle2 = new Vehicle("AP24AC7684", Vehicle.VehicleSize.SMALL, Vehicle.VehicleColor.BLACK);
        vehicle3 = new Vehicle("TN11WA4563", Vehicle.VehicleSize.SMALL, Vehicle.VehicleColor.WHITE);
        vehicle4 = new Vehicle("KA12TH4651", Vehicle.VehicleSize.SMALL, Vehicle.VehicleColor.WHITE);
        vehicle5 = new Vehicle("TS35TV7684", Vehicle.VehicleSize.SMALL, Vehicle.VehicleColor.WHITE);
        vehicle6 = new Vehicle("TS08CV5421", Vehicle.VehicleSize.SMALL, Vehicle.VehicleColor.WHITE);
    }

    @Test
    public void givenVehicleNumberToPark_whenParked_shouldReturnTrue() {
        parkingSlot.parkVehicle(vehicle1, Driver.ABLED);
        int occupiedSpots = parkingSlot.getOccupiedSpots();
        Assert.assertEquals(1,occupiedSpots);
    }

    @Test
    public void givenVehicleNumbersToPark_whenParked_shouldReturnNumberOfVehiclesParked() {
        parkingSlot.parkVehicle(vehicle2,Driver.ABLED);
        int numberOfVehicles = parkingSlot.getOccupiedSpots();
        Assert.assertEquals(1,numberOfVehicles);
    }

    @Test
    public void givenVehicleNumberToUnPark_whenUnParked_shouldReturnTrue() {
        parkingSlot.parkVehicle(vehicle1,Driver.ABLED);
        parkingSlot.parkVehicle(vehicle2,Driver.ABLED);
        parkingSlot.unParkVehicle(vehicle2);
        int occupiedSpots = parkingSlot.getOccupiedSpots();
        Assert.assertEquals(1,occupiedSpots);
    }

    @Test
    public void givenWrongVehicleNumberToUnPark_whenNot_shouldReturnTrue() {
        parkingSlot.parkVehicle(vehicle1,Driver.ABLED);
        parkingSlot.parkVehicle(vehicle2,Driver.ABLED);
        try {
            parkingSlot.unParkVehicle(new Vehicle("TS08CV5421", Vehicle.VehicleSize.SMALL, Vehicle.VehicleColor.WHITE));
        } catch (ParkingServiceException e) {
            Assert.assertEquals(ParkingServiceException.ExceptionType.NOT_IN_THE_PARKED_LIST,e.type);
        }
    }

    @Test
    public void givenVehicleNumbersToPark_whenParkingLotIsFull_shouldNotThrowException() {
        parkingSlot.parkVehicle(vehicle1,Driver.ABLED);
        parkingSlot.parkVehicle(vehicle2,Driver.ABLED);
        parkingSlot.parkVehicle(vehicle3,Driver.ABLED);
        parkingSlot.parkVehicle(vehicle4,Driver.ABLED);
        parkingSlot.parkVehicle(vehicle5,Driver.ABLED);
    }

    @Test
    public void givenVehicleNumbersToPark_whenParkingLotIsFull_shouldThrowException() {
        try {
            parkingSlot.parkVehicle(vehicle1,Driver.ABLED);
            parkingSlot.parkVehicle(vehicle2,Driver.ABLED);
            parkingSlot.parkVehicle(vehicle3,Driver.ABLED);
            parkingSlot.parkVehicle(vehicle4,Driver.ABLED);
            parkingSlot.parkVehicle(vehicle5,Driver.ABLED);
            parkingSlot.parkVehicle(vehicle6,Driver.ABLED);
        } catch (ParkingServiceException e){
            Assert.assertEquals(ParkingServiceException.ExceptionType.PARKING_LOT_IS_FULL,e.type);
        }
    }

    @Test
    public void givenSameVehicleNumberToPark_whenSameNumberEntered_shouldThrowException() {
        try {
            parkingSlot.parkVehicle(vehicle1,Driver.ABLED);
            parkingSlot.parkVehicle(vehicle1,Driver.ABLED);
        } catch (ParkingServiceException e) {
            Assert.assertEquals(ParkingServiceException.ExceptionType.EXISTING,e.type);
        }
    }

    @Test
    public void givenVehicleNumber_whenFound_shouldReturnParkedSpot() {
        parkingSlot.parkVehicle(vehicle1,Driver.ABLED);
        parkingSlot.parkVehicle(vehicle2,Driver.ABLED);
        parkingSlot.parkVehicle(vehicle3,Driver.ABLED);
        int parkedSpot = parkingSlot.getParkedSpot(vehicle2);
        Assert.assertEquals(2,parkedSpot);
    }

    @Test
    public void givenVehicleNumbersToUnPark_whenUnPark_shouldReturnParkedTime() {
        parkingSlot.parkVehicle(vehicle1,Driver.ABLED);
        long parkedTime = parkingSlot.getParkedTime(vehicle1);
        Assert.assertEquals(0,parkedTime);
    }

    @Test
    public void givenVehicleNumbersToPark_whenDriverDisabledPerson_shouldParkInFirstSpot() {
        parkingSlot.parkVehicle(vehicle1, Driver.ABLED);
        parkingSlot.parkVehicle(vehicle2,Driver.ABLED);
        parkingSlot.parkVehicle(vehicle3,Driver.ABLED);
        parkingSlot.parkVehicle(vehicle4, Driver.DISABLED);
        Assert.assertEquals(4, parkingSlot.getParkedSpot(vehicle1));
    }

    @Test
    public void givenVehicleNumbersToPark_whenDriverSecondDisabledPerson_shouldParkInSecondSpot() {
        parkingSlot.parkVehicle(vehicle1, Driver.ABLED);
        parkingSlot.parkVehicle(vehicle2,Driver.ABLED);
        parkingSlot.parkVehicle(vehicle3,Driver.ABLED);
        parkingSlot.parkVehicle(vehicle4, Driver.DISABLED);
        parkingSlot.parkVehicle(vehicle5, Driver.DISABLED);
        Assert.assertEquals(5, parkingSlot.getParkedSpot(vehicle2));
    }

}

