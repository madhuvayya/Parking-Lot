package com.parkinglot;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotServiceTest {

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
        parkingLotService.parkVehicle(new Vehicle("TS01AB1234"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS02AB5678"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS03AB1234"), Driver.ABLED);
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
            parkingLotService.parkVehicle(new Vehicle("TS01AB1234"), Driver.ABLED);
            parkingLotService.parkVehicle(new Vehicle("TS02AB5678"), Driver.ABLED);
            parkingLotService.parkVehicle(new Vehicle("TS03AB1234"), Driver.ABLED);
            parkingLotService.parkVehicle(new Vehicle("TS04AB5678"), Driver.ABLED);
            parkingLotService.parkVehicle(new Vehicle("TS05AB4567"), Driver.ABLED);
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
        parkingLotService.parkVehicle(new Vehicle("TS01AB1234"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS02AB5678"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS03AB1234"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS04AB5678"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS05AB4567"), Driver.ABLED);
        parkingLotService.unParkVehicle(new Vehicle("TS04AB5678"));
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
        parkingLotService.parkVehicle(new Vehicle("TS01AB1234"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS02AB5678"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS03AB1234"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS04AB5678"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS05AB4567"), Driver.ABLED);
        ParkingSlot slot = parkingLotService.getParkedSlot( new Vehicle("TS04AB5678"));
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
        parkingLotService.parkVehicle(new Vehicle("TS01AB1234"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS02AB5678"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS03AB1234"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS04AB5678"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS05AB4567"), Driver.ABLED);
        ParkingSlot slot = parkingLotService.getParkedSlot( new Vehicle("TS04AB5678"));
        Assert.assertNotEquals(parkingSlot1,slot);
    }

    @Test
    public void givenVehicleNumberToFind_whenFound_shouldReturnParkingSpot() {
        ParkingSlot parkingSlot1 = new ParkingSlot(2);
        ParkingSlot parkingSlot2 = new ParkingSlot(7);
        List<ParkingSlot> parkingLotList = new ArrayList<>();
        parkingLotList.add(parkingSlot1);
        parkingLotList.add(parkingSlot2);
        ParkingLotService parkingLotService = new ParkingLotService(parkingLotList);
        parkingLotService.parkVehicle(new Vehicle("TS01AB1234"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS02AB5678"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS03AB1234"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS04AB5678"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS05AB4567"), Driver.ABLED);
        int spot = parkingLotService.getParkedSpot( new Vehicle("TS04AB5678"));
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
        parkingLotService.parkVehicle(new Vehicle("TS01AB1234"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS02AB5678"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS03AB1234"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS04AB5678"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS05AB4567"), Driver.ABLED);
        int spot = parkingLotService.getParkedSpot( new Vehicle("TS04AB5678"));
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
        parkingLotService.parkVehicle(new Vehicle("TS01AB1234"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS02AB5678"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS03AB1234"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS04AB5678"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS05AB4567"), Driver.ABLED);
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
        parkingLotService.parkVehicle(new Vehicle("TS01AB1234"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS02AB5678"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS03AB1234"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS04AB5678"), Driver.ABLED);
        parkingLotService.parkVehicle(new Vehicle("TS05AB4567"), Driver.ABLED);
        int totalVehicles = parkingLotService.getNumberOfVehiclesInParkingLot();
        Assert.assertEquals(5,totalVehicles);
    }
}
