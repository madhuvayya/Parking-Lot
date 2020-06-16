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
        Vehicle vehicle1 = new Vehicle("TA07EC3633", Vehicle.VehicleSize.SMALL);
        Vehicle vehicle2 = new Vehicle("AP24AC7684", Vehicle.VehicleSize.SMALL);
        Vehicle vehicle3 = new Vehicle("TN11WA4563", Vehicle.VehicleSize.SMALL);
        Vehicle vehicle4 = new Vehicle("KA12TH4651", Vehicle.VehicleSize.SMALL);
        Vehicle vehicle5 = new Vehicle("TS35TV7684", Vehicle.VehicleSize.SMALL);
        Vehicle vehicle6 = new Vehicle("TS08CV5421", Vehicle.VehicleSize.SMALL);
    }

    @Test
    public void givenVehicleNumberToPark_whenParked_shouldReturnTrue() {
        parkingSlot.parkVehicle(, Driver.ABLED);
        int occupiedSpots = parkingSlot.getOccupiedSpots();
        Assert.assertEquals(1,occupiedSpots);
    }

    @Test
    public void givenVehicleNumbersToPark_whenParked_shouldReturnNumberOfVehiclesParked() {
        parkingSlot.parkVehicle(new Vehicle("TS08CV5421", Vehicle.VehicleSize.SMALL),Driver.ABLED);
        parkingSlot.parkVehicle(new Vehicle("TA07EC3633", Vehicle.VehicleSize.SMALL),Driver.ABLED);
        int numberOfVehicles = parkingSlot.getOccupiedSpots();
        Assert.assertEquals(2,numberOfVehicles);
    }

    @Test
    public void givenVehicleNumberToUnPark_whenUnParked_shouldReturnTrue() {
        parkingSlot.parkVehicle(new Vehicle("TS08CV5421", Vehicle.VehicleSize.SMALL),Driver.ABLED);
        Vehicle vehicle = new Vehicle("TA07EC3633", Vehicle.VehicleSize.SMALL);
        parkingSlot.parkVehicle(vehicle,Driver.ABLED);
        parkingSlot.unParkVehicle(vehicle);
        int occupiedSpots = parkingSlot.getOccupiedSpots();
        Assert.assertEquals(1,occupiedSpots);
    }

    @Test
    public void givenWrongVehicleNumberToUnPark_whenNot_shouldReturnTrue() {
        parkingSlot.parkVehicle(new Vehicle("TS08CV5421", Vehicle.VehicleSize.SMALL),Driver.ABLED);
        parkingSlot.parkVehicle(new Vehicle("TA07EC3633", Vehicle.VehicleSize.SMALL),Driver.ABLED);
        try {
            parkingSlot.unParkVehicle(new Vehicle("TA07TD8945", Vehicle.VehicleSize.SMALL));
        } catch (ParkingServiceException e) {
            Assert.assertEquals(ParkingServiceException.ExceptionType.NOT_IN_THE_PARKED_LIST,e.type);
        }
    }

    @Test
    public void givenVehicleNumbersToPark_whenParkingLotIsFull_shouldNotThrowException() {
        parkingSlot.parkVehicle(new Vehicle("TS08CV5421", Vehicle.VehicleSize.SMALL),Driver.ABLED);
        parkingSlot.parkVehicle(new Vehicle("TA07EC3633", Vehicle.VehicleSize.SMALL),Driver.ABLED);
        parkingSlot.parkVehicle(new Vehicle("AP24AC7684", Vehicle.VehicleSize.SMALL),Driver.ABLED);
        parkingSlot.parkVehicle(new Vehicle("TN11WA4563", Vehicle.VehicleSize.SMALL),Driver.ABLED);
        parkingSlot.parkVehicle(new Vehicle("KA12TH4651", Vehicle.VehicleSize.SMALL),Driver.ABLED);
    }

    @Test
    public void givenVehicleNumbersToPark_whenParkingLotIsFull_shouldThrowException() {
        try {
            parkingSlot.parkVehicle(new Vehicle("TS08CV5421", Vehicle.VehicleSize.SMALL),Driver.ABLED);
            parkingSlot.parkVehicle(new Vehicle("TA07EC3633", Vehicle.VehicleSize.SMALL),Driver.ABLED);
            parkingSlot.parkVehicle(new Vehicle("AP24AC7684", Vehicle.VehicleSize.SMALL),Driver.ABLED);
            parkingSlot.parkVehicle(new Vehicle("TN11WA4563", Vehicle.VehicleSize.SMALL),Driver.ABLED);
            parkingSlot.parkVehicle(new Vehicle("KA12TH4651", Vehicle.VehicleSize.SMALL),Driver.ABLED);
            parkingSlot.parkVehicle(new Vehicle("TS35TV7684", Vehicle.VehicleSize.SMALL),Driver.ABLED);
        } catch (ParkingServiceException e){
            Assert.assertEquals(ParkingServiceException.ExceptionType.PARKING_LOT_IS_FULL,e.type);
        }
    }

    @Test
    public void givenSameVehicleNumberToPark_whenSameNumberEntered_shouldThrowException() {
        try {
            Vehicle vehicle = new Vehicle("TA07EC3633");
            parkingSlot.parkVehicle(vehicle,Driver.ABLED);
            parkingSlot.parkVehicle(vehicle,Driver.ABLED);
        } catch (ParkingServiceException e) {
            Assert.assertEquals(ParkingServiceException.ExceptionType.EXISTING,e.type);
        }
    }

    @Test
    public void givenVehicleNumber_whenFound_shouldReturnParkedSpot() {
        Vehicle vehicle1 = new Vehicle("TA07EC3633");
        parkingSlot.parkVehicle(vehicle1,Driver.ABLED);
        Vehicle vehicle2 = new Vehicle("AP24AC7684");
        parkingSlot.parkVehicle(vehicle2,Driver.ABLED);
        Vehicle vehicle3 = new Vehicle("TN11WA4563");
        parkingSlot.parkVehicle(vehicle3,Driver.ABLED);
        int parkedSpot = parkingSlot.getParkedSpot(vehicle2);
        Assert.assertEquals(2,parkedSpot);
    }

    @Test
    public void givenVehicleNumbersToUnPark_whenUnPark_shouldReturnParkedTime() {
        parkingSlot.parkVehicle(new Vehicle("TS08CV5421"),Driver.ABLED);
        long parkedTime = parkingSlot.getParkedTime(new Vehicle("TS08CV5421"));
        Assert.assertEquals(0,parkedTime);
    }

    @Test
    public void givenVehicleNumbersToPark_whenDriverDisabledPerson_shouldParkInFirstSpot() {
        Vehicle vehicle1 = new Vehicle("TN11WA4563");
        parkingSlot.parkVehicle(vehicle1, Driver.ABLED);
        parkingSlot.parkVehicle(new Vehicle("KA12TH4651"),Driver.ABLED);
        parkingSlot.parkVehicle(new Vehicle("TS35TV7684"),Driver.ABLED);
        Vehicle vehicle = new Vehicle("TS08CV5421");
        parkingSlot.parkVehicle(vehicle, Driver.DISABLED);
        Assert.assertEquals(4, parkingSlot.getParkedSpot(vehicle1));
    }

    @Test
    public void givenVehicleNumbersToPark_whenDriverSecondDisabledPerson_shouldParkInSecondSpot() {
        Vehicle vehicle1 = new Vehicle("TN11WA4563");
        parkingSlot.parkVehicle(vehicle1, Driver.ABLED);
        parkingSlot.parkVehicle(new Vehicle("KA12TH4651"),Driver.ABLED);
        parkingSlot.parkVehicle(new Vehicle("TS35TV7684"),Driver.ABLED);
        parkingSlot.parkVehicle(new Vehicle("TS08CV5421"), Driver.DISABLED);
        parkingSlot.parkVehicle(new Vehicle("TS23CV7894"), Driver.DISABLED);
        Assert.assertEquals(2, parkingSlot.getParkedSpot(new Vehicle("TS23CV7894")));
    }

}

