package com.parkinglot;

import org.junit.Assert;
import org.junit.Test;

public class ParkingServiceTest {

    ParkingService parkingService = new ParkingService(5);

    @Test
    public void givenVehicleNumberToPark_whenParked_shouldReturnTrue() {
        boolean isParked = parkingService.parkVehicle("TA07EC3633");
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenVehicleNumbersToPark_whenParked_shouldReturnNumberOfVehiclesParked() {
        parkingService.parkVehicle("TS08CV5421");
        parkingService.parkVehicle("TA07EC3633");
        int numberOfVehicles = parkingService.vehicleList.size();
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
    public void givenVehicleNumberAsEmpty_whenEnteredEmpty_shouldThroughException() {
        try {
            parkingService.parkVehicle("");
        } catch (ParkingServiceException e) {
            Assert.assertEquals(ParkingServiceException.ExceptionType.ENTERED_EMPTY, e.type);
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
    public void givenVehicleNumberAsEmptyToUnPark_whenEnteredEmpty_shouldThroughException() {
        try {
            parkingService.unParkVehicle("");
        } catch (ParkingServiceException e) {
            Assert.assertEquals(ParkingServiceException.ExceptionType.ENTERED_EMPTY, e.type);
        }
    }

    @Test
    public void givenVehicleNumberToUnPark_whenUnParked_shouldReturnTrue() {
        parkingService.parkVehicle("TS08CV5421");
        parkingService.parkVehicle("TA07EC3633");
        boolean isParked = parkingService.unParkVehicle("TA07EC3633");
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenWrongVehicleNumberToUnPark_whenNot_shouldReturnTrue() {
        parkingService.parkVehicle("TS08CV5421");
        parkingService.parkVehicle("TA07EC3633");
        try {
            parkingService.unParkVehicle("TA07TD8945");
        } catch (ParkingServiceException e) {
            Assert.assertEquals(ParkingServiceException.ExceptionType.NOT_IN_THE_PARKED_LIST,e.type);
        }
    }

    @Test
    public void givenVehicleNumbersToPark_whenParkingLotIsFull_shouldNotThrowException() {
        parkingService.parkVehicle("TS08CV5421");
        parkingService.parkVehicle("TA07EC3633");
        parkingService.parkVehicle("AP24AC7684");
        parkingService.parkVehicle("TN11WA4563");
        parkingService.parkVehicle("KA12TH4651");
    }

    @Test
    public void givenVehicleNumbersToPark_whenParkingLotIsFull_shouldThrowException() {
        try {
            parkingService.parkVehicle("TS08CV5421");
            parkingService.parkVehicle("TA07EC3633");
            parkingService.parkVehicle("AP24AC7684");
            parkingService.parkVehicle("TN11WA4563");
            parkingService.parkVehicle("TS13VF9876");
            parkingService.parkVehicle("TS35TV7684");
        } catch (ParkingServiceException e){
            Assert.assertEquals(ParkingServiceException.ExceptionType.PARKING_LOT_IS_FULL,e.type);
        }
    }

    @Test
    public void givenSameVehicleNumberToPark_whenSameNumberEntered_shouldThrowException() {
        try {
            parkingService.parkVehicle("TA07EC3633");
            parkingService.parkVehicle("TA07EC3633");
        } catch (ParkingServiceException e) {
            Assert.assertEquals(ParkingServiceException.ExceptionType.EXISTING,e.type);
        }
    }
}

