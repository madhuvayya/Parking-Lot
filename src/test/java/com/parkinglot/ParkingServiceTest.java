package com.parkinglot;

import org.junit.Assert;
import org.junit.Test;

public class ParkingServiceTest {

    ParkingService parkingService = new ParkingService(5);

    @Test
    public void givenVehicleNumberToPark_whenParked_shouldReturnTrue() {
        parkingService.parkVehicle(new Vehicle("TA07EC3633", "black"));
        int occupiedSpots = parkingService.getOccupiedSpots();
        Assert.assertEquals(1,occupiedSpots);
    }

    @Test
    public void givenVehicleNumbersToPark_whenParked_shouldReturnNumberOfVehiclesParked() {
        parkingService.parkVehicle(new Vehicle("TS08CV5421","white"));
        parkingService.parkVehicle(new Vehicle("TA07EC3633","black"));
        int numberOfVehicles = parkingService.vehicleMap.size();
        Assert.assertEquals(2,numberOfVehicles);
    }

    @Test
    public void givenVehicleNumberAsNull_whenEnteredNull_shouldThroughException() {
        try {
            parkingService.parkVehicle(null);
        } catch (ParkingServiceException e) {
            Assert.assertEquals(ParkingServiceException.ExceptionType.ENTERED_NULL, e.type);
        }
    }

    @Test
    public void givenVehicleNumberAsNullToUnPark_whenEnteredNull_shouldThroughException() {
        try {
            parkingService.unParkVehicle(null);
        } catch (ParkingServiceException e) {
            Assert.assertEquals(ParkingServiceException.ExceptionType.ENTERED_NULL, e.type);
        }
    }

    @Test
    public void givenVehicleNumberToUnPark_whenUnParked_shouldReturnTrue() {
        parkingService.parkVehicle(new Vehicle("TS08CV5421", "white"));
        Vehicle vehicle = new Vehicle("TA07EC3633", "black");
        parkingService.parkVehicle(vehicle);
        parkingService.unParkVehicle(vehicle);
        int occupiedSpots = parkingService.getOccupiedSpots();
        Assert.assertEquals(1,occupiedSpots);
    }

    @Test
    public void givenWrongVehicleNumberToUnPark_whenNot_shouldReturnTrue() {
        parkingService.parkVehicle(new Vehicle("TS08CV5421","white"));
        parkingService.parkVehicle(new Vehicle("TA07EC3633","black"));
        try {
            parkingService.unParkVehicle(new Vehicle("TA07TD8945","red"));
        } catch (ParkingServiceException e) {
            Assert.assertEquals(ParkingServiceException.ExceptionType.NOT_IN_THE_PARKED_LIST,e.type);
        }
    }

    @Test
    public void givenVehicleNumbersToPark_whenParkingLotIsFull_shouldNotThrowException() {
        parkingService.parkVehicle(new Vehicle("TS08CV5421","red"));
        parkingService.parkVehicle(new Vehicle("TA07EC3633","gray"));
        parkingService.parkVehicle(new Vehicle("AP24AC7684","brown"));
        parkingService.parkVehicle(new Vehicle("TN11WA4563","black"));
        parkingService.parkVehicle(new Vehicle("KA12TH4651","blue"));
    }

    @Test
    public void givenVehicleNumbersToPark_whenParkingLotIsFull_shouldThrowException() {
        try {
            parkingService.parkVehicle(new Vehicle("TS08CV5421","red"));
            parkingService.parkVehicle(new Vehicle("TA07EC3633","gray"));
            parkingService.parkVehicle(new Vehicle("AP24AC7684","brown"));
            parkingService.parkVehicle(new Vehicle("TN11WA4563","black"));
            parkingService.parkVehicle(new Vehicle("KA12TH4651","blue"));
            parkingService.parkVehicle(new Vehicle("TS35TV7684","green"));
        } catch (ParkingServiceException e){
            Assert.assertEquals(ParkingServiceException.ExceptionType.PARKING_LOT_IS_FULL,e.type);
        }
    }

    @Test
    public void givenSameVehicleNumberToPark_whenSameNumberEntered_shouldThrowException() {
        try {
            parkingService.parkVehicle(new Vehicle("TA07EC3633","green"));
            parkingService.parkVehicle(new Vehicle("TA07EC3633","green"));
        } catch (ParkingServiceException e) {
            Assert.assertEquals(ParkingServiceException.ExceptionType.EXISTING,e.type);
        }
    }

    @Test
    public void givenVehicleNumber_whenFound_shouldReturnParkedSpot() {
        Vehicle vehicle1 = new Vehicle("TA07EC3633", "green");
        parkingService.parkVehicle(vehicle1);
        Vehicle vehicle2 = new Vehicle("AP24AC7684", "brown");
        parkingService.parkVehicle(vehicle2);
        Vehicle vehicle3 = new Vehicle("TN11WA4563", "black");
        parkingService.parkVehicle(vehicle3);
        int parkedSpot = parkingService.getParkedSpot(vehicle2);
        Assert.assertEquals(2,parkedSpot);
    }

}

