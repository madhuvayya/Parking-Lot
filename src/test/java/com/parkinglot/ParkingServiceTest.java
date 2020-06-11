package com.parkinglot;

import org.junit.Assert;
import org.junit.Test;

public class ParkingServiceTest {

    ParkingService parkingService = new ParkingService();

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
    public void givenVehicleNumberAsNull_whenNull_shouldThroughException() {
        try {
            parkingService.parkVehicle(null);
        } catch (ParkingServiceException e) {
            Assert.assertEquals(ParkingServiceException.ExceptionType.NULL, e.type);
        }
    }

    @Test
    public void givenVehicleNumberAsEmpty_whenEmpty_shouldThroughException() {
        try {
            parkingService.parkVehicle("");
        } catch (ParkingServiceException e) {
            Assert.assertEquals(ParkingServiceException.ExceptionType.EMPTY, e.type);
        }
    }

    @Test
    public void givenVehicleNumberToUnPark_whenUnParked_shouldReturnTrue() {
        parkingService.parkVehicle("TS08CV5421");
        parkingService.parkVehicle("TA07EC3633");
        boolean isParked = parkingService.unParkVehicle("TA07EC3633");
        Assert.assertTrue(isParked);
    }

}

