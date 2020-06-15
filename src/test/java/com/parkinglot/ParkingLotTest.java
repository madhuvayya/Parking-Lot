package com.parkinglot;

import org.junit.Assert;
import org.junit.Test;

public class ParkingLotTest {

    ParkingLot parkingLot = new ParkingLot(5);

    @Test
    public void givenVehicleNumberToPark_whenParked_shouldReturnTrue() {
        parkingLot.parkVehicle(new Vehicle("TA07EC3633"), Driver.ABLED);
        int occupiedSpots = parkingLot.getOccupiedSpots();
        Assert.assertEquals(1,occupiedSpots);
    }

    @Test
    public void givenVehicleNumbersToPark_whenParked_shouldReturnNumberOfVehiclesParked() {
        parkingLot.parkVehicle(new Vehicle("TS08CV5421"),Driver.ABLED);
        parkingLot.parkVehicle(new Vehicle("TA07EC3633"),Driver.ABLED);
        int numberOfVehicles = parkingLot.getOccupiedSpots();
        Assert.assertEquals(2,numberOfVehicles);
    }

    @Test
    public void givenVehicleNumberAsNull_whenEnteredNull_shouldThroughException() {
        try {
            parkingLot.parkVehicle(null,Driver.ABLED);
        } catch (ParkingServiceException e) {
            Assert.assertEquals(ParkingServiceException.ExceptionType.ENTERED_NULL, e.type);
        }
    }

    @Test
    public void givenVehicleNumberAsNullToUnPark_whenEnteredNull_shouldThroughException() {
        try {
            parkingLot.unParkVehicle(null);
        } catch (ParkingServiceException e) {
            Assert.assertEquals(ParkingServiceException.ExceptionType.ENTERED_NULL, e.type);
        }
    }

    @Test
    public void givenVehicleNumberToUnPark_whenUnParked_shouldReturnTrue() {
        parkingLot.parkVehicle(new Vehicle("TS08CV5421"),Driver.ABLED);
        Vehicle vehicle = new Vehicle("TA07EC3633");
        parkingLot.parkVehicle(vehicle,Driver.ABLED);
        parkingLot.unParkVehicle(vehicle);
        int occupiedSpots = parkingLot.getOccupiedSpots();
        Assert.assertEquals(1,occupiedSpots);
    }

    @Test
    public void givenWrongVehicleNumberToUnPark_whenNot_shouldReturnTrue() {
        parkingLot.parkVehicle(new Vehicle("TS08CV5421"),Driver.ABLED);
        parkingLot.parkVehicle(new Vehicle("TA07EC3633"),Driver.ABLED);
        try {
            parkingLot.unParkVehicle(new Vehicle("TA07TD8945"));
        } catch (ParkingServiceException e) {
            Assert.assertEquals(ParkingServiceException.ExceptionType.NOT_IN_THE_PARKED_LIST,e.type);
        }
    }

    @Test
    public void givenVehicleNumbersToPark_whenParkingLotIsFull_shouldNotThrowException() {
        parkingLot.parkVehicle(new Vehicle("TS08CV5421"),Driver.ABLED);
        parkingLot.parkVehicle(new Vehicle("TA07EC3633"),Driver.ABLED);
        parkingLot.parkVehicle(new Vehicle("AP24AC7684"),Driver.ABLED);
        parkingLot.parkVehicle(new Vehicle("TN11WA4563"),Driver.ABLED);
        parkingLot.parkVehicle(new Vehicle("KA12TH4651"),Driver.ABLED);
    }

    @Test
    public void givenVehicleNumbersToPark_whenParkingLotIsFull_shouldThrowException() {
        try {
            parkingLot.parkVehicle(new Vehicle("TS08CV5421"),Driver.ABLED);
            parkingLot.parkVehicle(new Vehicle("TA07EC3633"),Driver.ABLED);
            parkingLot.parkVehicle(new Vehicle("AP24AC7684"),Driver.ABLED);
            parkingLot.parkVehicle(new Vehicle("TN11WA4563"),Driver.ABLED);
            parkingLot.parkVehicle(new Vehicle("KA12TH4651"),Driver.ABLED);
            parkingLot.parkVehicle(new Vehicle("TS35TV7684"),Driver.ABLED);
        } catch (ParkingServiceException e){
            Assert.assertEquals(ParkingServiceException.ExceptionType.PARKING_LOT_IS_FULL,e.type);
        }
    }

    @Test
    public void givenSameVehicleNumberToPark_whenSameNumberEntered_shouldThrowException() {
        try {
            Vehicle vehicle = new Vehicle("TA07EC3633");
            parkingLot.parkVehicle(vehicle,Driver.ABLED);
            parkingLot.parkVehicle(vehicle,Driver.ABLED);
        } catch (ParkingServiceException e) {
            Assert.assertEquals(ParkingServiceException.ExceptionType.EXISTING,e.type);
        }
    }

    @Test
    public void givenVehicleNumber_whenFound_shouldReturnParkedSpot() {
        Vehicle vehicle1 = new Vehicle("TA07EC3633");
        parkingLot.parkVehicle(vehicle1,Driver.ABLED);
        Vehicle vehicle2 = new Vehicle("AP24AC7684");
        parkingLot.parkVehicle(vehicle2,Driver.ABLED);
        Vehicle vehicle3 = new Vehicle("TN11WA4563");
        parkingLot.parkVehicle(vehicle3,Driver.ABLED);
        int parkedSpot = parkingLot.getParkedSpot(vehicle2);
        Assert.assertEquals(2,parkedSpot);
    }

    @Test
    public void givenVehicleNumbersToUnPark_whenUnPark_shouldReturnParkedTime() {
        parkingLot.parkVehicle(new Vehicle("TS08CV5421"),Driver.ABLED);
        long parkedTime = parkingLot.getParkedTime(new Vehicle("TS08CV5421"));
        Assert.assertEquals(0,parkedTime);
    }

    @Test
    public void givenVehicleNumbersToPark_whenDriverDisabledPerson_shouldParkInFirstSpot() {
        Vehicle vehicle1 = new Vehicle("TN11WA4563");
        parkingLot.parkVehicle(vehicle1, Driver.ABLED);
        parkingLot.parkVehicle(new Vehicle("KA12TH4651"),Driver.ABLED);
        parkingLot.parkVehicle(new Vehicle("TS35TV7684"),Driver.ABLED);
        Vehicle vehicle = new Vehicle("TS08CV5421");
        parkingLot.parkVehicle(vehicle, Driver.DISABLED);
        Assert.assertEquals(4, parkingLot.getParkedSpot(vehicle1));
    }

    @Test
    public void givenVehicleNumbersToPark_whenDriverSecondDisabledPerson_shouldParkInSecondSpot() {
        Vehicle vehicle1 = new Vehicle("TN11WA4563");
        parkingLot.parkVehicle(vehicle1, Driver.ABLED);
        parkingLot.parkVehicle(new Vehicle("KA12TH4651"),Driver.ABLED);
        parkingLot.parkVehicle(new Vehicle("TS35TV7684"),Driver.ABLED);
        parkingLot.parkVehicle(new Vehicle("TS08CV5421"), Driver.DISABLED);
        parkingLot.parkVehicle(new Vehicle("TS23CV7894"), Driver.DISABLED);
        Assert.assertEquals(2, parkingLot.getParkedSpot(new Vehicle("TS23CV7894")));
    }

}

