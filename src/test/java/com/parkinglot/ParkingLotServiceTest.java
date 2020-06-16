package com.parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotServiceTest {

    Vehicle vehicle1;
    Vehicle vehicle2;
    Vehicle vehicle3;
    Vehicle vehicle4;
    Vehicle vehicle5;
    Vehicle vehicle6;
    Vehicle vehicle7;

    @Before
    public void setUp() throws Exception {
        vehicle1 = new Vehicle("TA07EC3633", Vehicle.VehicleSize.SMALL, Vehicle.VehicleColor.WHITE);
        vehicle2 = new Vehicle("AP24AC7684", Vehicle.VehicleSize.SMALL, Vehicle.VehicleColor.BLACK);
        vehicle3 = new Vehicle("TN11WA4563", Vehicle.VehicleSize.SMALL, Vehicle.VehicleColor.WHITE);
        vehicle4 = new Vehicle("KA12TH4651", Vehicle.VehicleSize.SMALL, Vehicle.VehicleColor.WHITE);
        vehicle5 = new Vehicle("TS35TV7684", Vehicle.VehicleSize.SMALL, Vehicle.VehicleColor.WHITE);
        vehicle6 = new Vehicle("TS08CV5421", Vehicle.VehicleSize.SMALL, Vehicle.VehicleColor.WHITE);
        vehicle7 = new Vehicle("KA42HM4651", Vehicle.VehicleSize.LARGE, Vehicle.VehicleColor.WHITE);
    }

    @Test
    public void givenNumberOfParkingLots_whenCreated_shouldReturnNumberOfLots() {
        ParkingSlot parkingSlot1 = new ParkingSlot(5);
        ParkingSlot parkingSlot2 = new ParkingSlot(7);
        List<ParkingSlot> parkingLotList = new ArrayList<>();
        parkingLotList.add(parkingSlot1);
        parkingLotList.add(parkingSlot2);
        ParkingLotService parkingLotService = new ParkingLotService(parkingLotList);
        int numberOfParkingLots = parkingLotService.getNumberOfParkingSlots();
        Assert.assertEquals(2,numberOfParkingLots);
    }

    @Test
    public void givenVehicleNumberToPark_whenParked_shouldReturnOccupiedSpotsInALot() {
        ParkingSlot parkingSlot1 = new ParkingSlot(1);
        ParkingSlot parkingSlot2 = new ParkingSlot(3);
        List<ParkingSlot> parkingLotList = new ArrayList<>();
        parkingLotList.add(parkingSlot1);
        parkingLotList.add(parkingSlot2);
        ParkingLotService parkingLotService = new ParkingLotService(parkingLotList);
        parkingLotService.parkVehicle(vehicle1, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle2, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle3, Driver.ABLED);
        int occupiedSpotsInALot = parkingLotService.getOccupiedSpotsInASlot(parkingSlot2);
        Assert.assertEquals(2,occupiedSpotsInALot);
    }

    @Test
    public void givenVehicleNumberToPark_whenAllParkingLotsAreFull_shouldThrowException() {
        ParkingSlot parkingSlot1 = new ParkingSlot(1);
        ParkingSlot parkingSlot2 = new ParkingSlot(3);
        List<ParkingSlot> parkingLotList = new ArrayList<>();
        parkingLotList.add(parkingSlot1);
        parkingLotList.add(parkingSlot2);
        ParkingLotService parkingLotService = new ParkingLotService(parkingLotList);
        try {
            parkingLotService.parkVehicle(vehicle1, Driver.ABLED);
            parkingLotService.parkVehicle(vehicle2, Driver.ABLED);
            parkingLotService.parkVehicle(vehicle3, Driver.ABLED);
            parkingLotService.parkVehicle(vehicle4, Driver.ABLED);
            parkingLotService.parkVehicle(vehicle5, Driver.ABLED);
        } catch(ParkingServiceException e){
            Assert.assertEquals(ParkingServiceException.ExceptionType.PARKING_LOTS_ARE_FULL,e.type);
        }
    }

    @Test
    public void givenVehicleNumberToUnPark_whenUnParked_shouldReturnOccupiedSpots() {
        ParkingSlot parkingSlot1 = new ParkingSlot(2);
        ParkingSlot parkingSlot2 = new ParkingSlot(7);
        List<ParkingSlot> parkingLotList = new ArrayList<>();
        parkingLotList.add(parkingSlot1);
        parkingLotList.add(parkingSlot2);
        ParkingLotService parkingLotService = new ParkingLotService(parkingLotList);
        parkingLotService.parkVehicle(vehicle1, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle2, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle3, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle4, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle5, Driver.ABLED);
        parkingLotService.unParkVehicle(vehicle4);
        int occupiedSpotsInALot = parkingLotService.getOccupiedSpotsInASlot(parkingSlot2);
        Assert.assertEquals(2,occupiedSpotsInALot);
    }

    @Test
    public void givenVehicleNumberToFindInSlot_whenFound_shouldReturnParkingSlot() {
        ParkingSlot parkingSlot1 = new ParkingSlot(2);
        ParkingSlot parkingSlot2 = new ParkingSlot(7);
        List<ParkingSlot> parkingLotList = new ArrayList<>();
        parkingLotList.add(parkingSlot1);
        parkingLotList.add(parkingSlot2);
        ParkingLotService parkingLotService = new ParkingLotService(parkingLotList);
        parkingLotService.parkVehicle(vehicle1, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle2, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle3, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle4, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle5, Driver.ABLED);
        ParkingSlot slot = parkingLotService.getParkedSlot( vehicle5);
        Assert.assertEquals(parkingSlot2,slot);
    }

    @Test
    public void givenVehicleNumberToFind_whenFound_shouldReturnParkingSlot() {
        ParkingSlot parkingSlot1 = new ParkingSlot(2);
        ParkingSlot parkingSlot2 = new ParkingSlot(7);
        List<ParkingSlot> parkingLotList = new ArrayList<>();
        parkingLotList.add(parkingSlot1);
        parkingLotList.add(parkingSlot2);
        ParkingLotService parkingLotService = new ParkingLotService(parkingLotList);
        parkingLotService.parkVehicle(vehicle1, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle2, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle3, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle4, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle5, Driver.ABLED);
        ParkingSlot slot = parkingLotService.getParkedSlot( vehicle3);
        Assert.assertNotEquals(parkingSlot2,slot);
    }

    @Test
    public void givenVehicleNumberToFind_whenFound_shouldReturnParkingSpot() {
        ParkingSlot parkingSlot1 = new ParkingSlot(2);
        ParkingSlot parkingSlot2 = new ParkingSlot(7);
        List<ParkingSlot> parkingLotList = new ArrayList<>();
        parkingLotList.add(parkingSlot1);
        parkingLotList.add(parkingSlot2);
        ParkingLotService parkingLotService = new ParkingLotService(parkingLotList);
        parkingLotService.parkVehicle(vehicle1, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle2, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle3, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle4, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle5, Driver.ABLED);
        int spot = parkingLotService.getParkedSpot( vehicle3);
        Assert.assertEquals(2,spot);
    }

    @Test
    public void givenVehicleNumberToFind_whenFoundInParkingLot_shouldReturnParkingSpot() {
        ParkingSlot parkingSlot1 = new ParkingSlot(2);
        ParkingSlot parkingSlot2 = new ParkingSlot(7);
        List<ParkingSlot> parkingLotList = new ArrayList<>();
        parkingLotList.add(parkingSlot1);
        parkingLotList.add(parkingSlot2);
        ParkingLotService parkingLotService = new ParkingLotService(parkingLotList);
        parkingLotService.parkVehicle(vehicle1, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle2, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle3, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle4, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle5, Driver.ABLED);
        int spot = parkingLotService.getParkedSpot( vehicle5);
        Assert.assertNotEquals(1,spot);
    }

    @Test
    public void givenVehicleNumber_whenParked_shouldReturnAvailableSpots() {
        ParkingSlot parkingSlot1 = new ParkingSlot(2);
        ParkingSlot parkingSlot2 = new ParkingSlot(7);
        List<ParkingSlot> parkingLotList = new ArrayList<>();
        parkingLotList.add(parkingSlot1);
        parkingLotList.add(parkingSlot2);
        ParkingLotService parkingLotService = new ParkingLotService(parkingLotList);
        parkingLotService.parkVehicle(vehicle1, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle2, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle3, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle4, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle5, Driver.ABLED);
        int availableSpotsInASlot = parkingLotService.getAvailableSpotsInASlot(parkingSlot1);
        Assert.assertEquals(0,availableSpotsInASlot);
    }

    @Test
    public void givenVehicleNumbersToPark_whenParked_shouldReturnAllVehiclesInParkingLot() {
        ParkingSlot parkingSlot1 = new ParkingSlot(2);
        ParkingSlot parkingSlot2 = new ParkingSlot(7);
        List<ParkingSlot> parkingLotList = new ArrayList<>();
        parkingLotList.add(parkingSlot1);
        parkingLotList.add(parkingSlot2);
        ParkingLotService parkingLotService = new ParkingLotService(parkingLotList);
        parkingLotService.parkVehicle(vehicle1, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle2, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle3, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle4, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle5, Driver.ABLED);
        int totalVehicles = parkingLotService.getNumberOfVehiclesInParkingLot();
        Assert.assertEquals(5,totalVehicles);
    }

    @Test
    public void givenVehicleNumbersToPark_whenDriverDisAbled_shouldAllocateNearestSlot() {
        ParkingSlot parkingSlot1 = new ParkingSlot(2);
        ParkingSlot parkingSlot2 = new ParkingSlot(2);
        List<ParkingSlot> parkingLotList = new ArrayList<>();
        parkingLotList.add(parkingSlot1);
        parkingLotList.add(parkingSlot2);
        ParkingLotService parkingLotService = new ParkingLotService(parkingLotList);
        parkingLotService.parkVehicle(vehicle1, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle4, Driver.DISABLED);
        ParkingSlot parkedSlot = parkingLotService.getParkedSlot(vehicle4);
        Assert.assertEquals(parkingSlot1,parkedSlot);
        Assert.assertEquals(1,parkingLotService.getParkedSpot(vehicle4));
    }

    @Test
    public void givenVehicleNumbersToPark_whenVehicleIsLarge_shouldAllocateRequiredSpace() {
        ParkingSlot parkingSlot1 = new ParkingSlot(2);
        ParkingSlot parkingSlot2 = new ParkingSlot(5);
        List<ParkingSlot> parkingLotList = new ArrayList<>();
        parkingLotList.add(parkingSlot1);
        parkingLotList.add(parkingSlot2);
        ParkingLotService parkingLotService = new ParkingLotService(parkingLotList);
        parkingLotService.parkVehicle(vehicle1, Driver.ABLED);
        parkingLotService.parkVehicle(vehicle7, Driver.ABLED);
        ParkingSlot parkedSlot = parkingLotService.getParkedSlot(vehicle7);
        Assert.assertEquals(parkingSlot2,parkedSlot);
    }
}
