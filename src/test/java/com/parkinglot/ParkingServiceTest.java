package com.parkinglot;

import org.junit.Assert;
import org.junit.Test;

public class ParkingServiceTest {


    @Test
    public void givenVehicleNumberToPark_whenParked_shouldReturnTrue() {
        ParkingService parkingService = new ParkingService();
        parkingService.parkVehicle(new Vehicle("TA07EC3633", Driver.ABLED));
        int occupiedSpots = parkingService.getOccupiedSpots();
        Assert.assertEquals(1,occupiedSpots);
    }

    @Test
    public void givenVehicleNumbersToPark_whenParked_shouldReturnNumberOfVehiclesParked() {
        ParkingService parkingService = new ParkingService();
        parkingService.parkVehicle(new Vehicle("TS08CV5421",Driver.ABLED));
        parkingService.parkVehicle(new Vehicle("TA07EC3633",Driver.ABLED));
        int numberOfVehicles = parkingService.getOccupiedSpots();
        Assert.assertEquals(2,numberOfVehicles);
    }

    @Test
    public void givenVehicleNumberAsNull_whenEnteredNull_shouldThroughException() {
        ParkingService parkingService = new ParkingService();
        try {
            parkingService.parkVehicle(null);
        } catch (ParkingServiceException e) {
            Assert.assertEquals(ParkingServiceException.ExceptionType.ENTERED_NULL, e.type);
        }
    }

    @Test
    public void givenVehicleNumberAsNullToUnPark_whenEnteredNull_shouldThroughException() {
        ParkingService parkingService = new ParkingService();
        try {
            parkingService.unParkVehicle(null);
        } catch (ParkingServiceException e) {
            Assert.assertEquals(ParkingServiceException.ExceptionType.ENTERED_NULL, e.type);
        }
    }

    @Test
    public void givenVehicleNumberToUnPark_whenUnParked_shouldReturnTrue() {
        ParkingService parkingService = new ParkingService();
        parkingService.parkVehicle(new Vehicle("TS08CV5421",Driver.ABLED));
        Vehicle vehicle = new Vehicle("TA07EC3633",Driver.ABLED);
        parkingService.parkVehicle(vehicle);
        parkingService.unParkVehicle(vehicle);
        int occupiedSpots = parkingService.getOccupiedSpots();
        Assert.assertEquals(1,occupiedSpots);
    }

    @Test
    public void givenWrongVehicleNumberToUnPark_whenNot_shouldReturnTrue() {
        ParkingService parkingService = new ParkingService();
        parkingService.parkVehicle(new Vehicle("TS08CV5421",Driver.ABLED));
        parkingService.parkVehicle(new Vehicle("TA07EC3633",Driver.ABLED));
        try {
            parkingService.unParkVehicle(new Vehicle("TA07TD8945",Driver.ABLED));
        } catch (ParkingServiceException e) {
            Assert.assertEquals(ParkingServiceException.ExceptionType.NOT_IN_THE_PARKED_LIST,e.type);
        }
    }

    @Test
    public void givenVehicleNumbersToPark_whenParkingLotIsFull_shouldNotThrowException() {
        ParkingService parkingService = new ParkingService();
        parkingService.parkVehicle(new Vehicle("TS08CV5421",Driver.ABLED));
        parkingService.parkVehicle(new Vehicle("TA07EC3633",Driver.ABLED));
        parkingService.parkVehicle(new Vehicle("AP24AC7684",Driver.ABLED));
        parkingService.parkVehicle(new Vehicle("TN11WA4563",Driver.ABLED));
        parkingService.parkVehicle(new Vehicle("KA12TH4651",Driver.ABLED));
    }

    @Test
    public void givenVehicleNumbersToPark_whenParkingLotIsFull_shouldThrowException() {
        ParkingService parkingService = new ParkingService();
        try {
            parkingService.parkVehicle(new Vehicle("TS08CV5421",Driver.ABLED));
            parkingService.parkVehicle(new Vehicle("TA07EC3633",Driver.ABLED));
            parkingService.parkVehicle(new Vehicle("AP24AC7684",Driver.ABLED));
            parkingService.parkVehicle(new Vehicle("TN11WA4563",Driver.ABLED));
            parkingService.parkVehicle(new Vehicle("KA12TH4651",Driver.ABLED));
            parkingService.parkVehicle(new Vehicle("TS35TV7684",Driver.ABLED));
        } catch (ParkingServiceException e){
            Assert.assertEquals(ParkingServiceException.ExceptionType.PARKING_LOT_IS_FULL,e.type);
        }
    }

    @Test
    public void givenSameVehicleNumberToPark_whenSameNumberEntered_shouldThrowException() {
        ParkingService parkingService = new ParkingService();
        try {
            Vehicle vehicle = new Vehicle("TA07EC3633",Driver.ABLED);
            parkingService.parkVehicle(vehicle);
            parkingService.parkVehicle(vehicle);
        } catch (ParkingServiceException e) {
            Assert.assertEquals(ParkingServiceException.ExceptionType.EXISTING,e.type);
        }
    }

    @Test
    public void givenVehicleNumber_whenFound_shouldReturnParkedSpot() {
        ParkingService parkingService = new ParkingService();
        Vehicle vehicle1 = new Vehicle("TA07EC3633",Driver.ABLED);
        parkingService.parkVehicle(vehicle1);
        Vehicle vehicle2 = new Vehicle("AP24AC7684",Driver.ABLED);
        parkingService.parkVehicle(vehicle2);
        Vehicle vehicle3 = new Vehicle("TN11WA4563",Driver.ABLED);
        parkingService.parkVehicle(vehicle3);
        int parkedSpot = parkingService.getParkedSpot(vehicle2);
        Assert.assertEquals(2,parkedSpot);
    }

    @Test
    public void givenVehicleNumbersToUnPark_whenUnPark_shouldReturnParkedTime() {
        ParkingService parkingService = new ParkingService();
        Vehicle vehicle = new Vehicle("TS08CV5421",Driver.ABLED);
        parkingService.parkVehicle(vehicle);
        long parkedTime = parkingService.getParkedTime(vehicle);
        Assert.assertEquals(0,parkedTime);
    }

    @Test
    public void givenVehicleNumbersToUnPark_whenDriverDisabledPerson_shouldParkInFirstSpot() {
        ParkingService parkingService = new ParkingService();
        Vehicle vehicle1 = new Vehicle("TN11WA4563", Driver.ABLED);
        parkingService.parkVehicle(vehicle1);
        parkingService.parkVehicle(new Vehicle("KA12TH4651",Driver.ABLED));
        parkingService.parkVehicle(new Vehicle("TS35TV7684",Driver.ABLED));
        Vehicle vehicle = new Vehicle("TS08CV5421", Driver.DISABLED);
        parkingService.parkVehicle(vehicle);
        Assert.assertEquals(4,parkingService.getParkedSpot(vehicle1));
    }

}

