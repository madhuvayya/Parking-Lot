package com.parkinglot;

import org.junit.Assert;
import org.junit.Test;

public class ParkingServiceTest {

    ParkingService parkingService = new ParkingService();

    @Test
    public void givenVehicleNumber_whenParked_shouldReturnTrue() {
        boolean isParked = parkingService.parkVehicle("TA07EC3633");
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenVehicleNumbers_whenParked_shouldReturnNumberOfVehiclesParked() {
        parkingService.parkVehicle("TS08CV5421");
        parkingService.parkVehicle("TS08CV5421");
        int numberOfVehicles = parkingService.vehicleList.size();
        Assert.assertEquals(2,numberOfVehicles);
    }
}

