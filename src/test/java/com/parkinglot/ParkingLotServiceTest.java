package com.parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class ParkingLotServiceTest {

    Vehicle vehicle1;
    Vehicle vehicle2;
    Vehicle vehicle3;
    Vehicle vehicle4;
    Vehicle vehicle5;
    Vehicle vehicle6;
    Vehicle vehicle7;

    @Before
    public void setUp(){
        vehicle1 = new Vehicle("TA07EC3633", Vehicle.VehicleProperty.SMALL, Vehicle.VehicleProperty.WHITE,
                Vehicle.VehicleProperty.TOYOTA);
        vehicle2 = new Vehicle("AP24AC7684", Vehicle.VehicleProperty.SMALL, Vehicle.VehicleProperty.BLUE,
                Vehicle.VehicleProperty.BMW);
        vehicle3 = new Vehicle("TN11WA4563", Vehicle.VehicleProperty.SMALL, Vehicle.VehicleProperty.WHITE,
                Vehicle.VehicleProperty.TOYOTA);
        vehicle4 = new Vehicle("KA12TH4651", Vehicle.VehicleProperty.SMALL, Vehicle.VehicleProperty.BLUE,
                Vehicle.VehicleProperty.TOYOTA);
        vehicle5 = new Vehicle("TS35TV7684", Vehicle.VehicleProperty.SMALL, Vehicle.VehicleProperty.WHITE,
                Vehicle.VehicleProperty.BMW);
        vehicle6 = new Vehicle("TS08CV5421", Vehicle.VehicleProperty.SMALL, Vehicle.VehicleProperty.RED,
                Vehicle.VehicleProperty.TOYOTA);
        vehicle7 = new Vehicle("KA42HM4651", Vehicle.VehicleProperty.LARGE, Vehicle.VehicleProperty.WHITE,
                Vehicle.VehicleProperty.BMW);
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
        parkingLotService.parkVehicle(vehicle1, Driver.ABLED,new ParkingAttendant("attendant1"));
        parkingLotService.parkVehicle(vehicle2, Driver.ABLED,new ParkingAttendant("attendant2"));
        parkingLotService.parkVehicle(vehicle3, Driver.ABLED,new ParkingAttendant("attendant1"));
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
            parkingLotService.parkVehicle(vehicle1, Driver.ABLED,new ParkingAttendant("attendant1"));
            parkingLotService.parkVehicle(vehicle2, Driver.ABLED,new ParkingAttendant("attendant2"));
            parkingLotService.parkVehicle(vehicle3, Driver.ABLED,new ParkingAttendant("attendant1"));
            parkingLotService.parkVehicle(vehicle4, Driver.ABLED,new ParkingAttendant("attendant2"));
            parkingLotService.parkVehicle(vehicle5, Driver.ABLED,new ParkingAttendant("attendant1"));
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
        parkingLotService.parkVehicle(vehicle1, Driver.ABLED,new ParkingAttendant("attendant1"));
        parkingLotService.parkVehicle(vehicle2, Driver.ABLED,new ParkingAttendant("attendant2"));
        parkingLotService.parkVehicle(vehicle3, Driver.ABLED,new ParkingAttendant("attendant1"));
        parkingLotService.parkVehicle(vehicle4, Driver.ABLED,new ParkingAttendant("attendant2"));
        parkingLotService.parkVehicle(vehicle5, Driver.ABLED,new ParkingAttendant("attendant1"));
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
        parkingLotService.parkVehicle(vehicle1, Driver.ABLED,new ParkingAttendant("attendant1"));
        parkingLotService.parkVehicle(vehicle2, Driver.ABLED,new ParkingAttendant("attendant2"));
        parkingLotService.parkVehicle(vehicle3, Driver.ABLED,new ParkingAttendant("attendant1"));
        parkingLotService.parkVehicle(vehicle4, Driver.ABLED,new ParkingAttendant("attendant2"));
        parkingLotService.parkVehicle(vehicle5, Driver.ABLED,new ParkingAttendant("attendant1"));
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
        parkingLotService.parkVehicle(vehicle1, Driver.ABLED,new ParkingAttendant("attendant1"));
        parkingLotService.parkVehicle(vehicle2, Driver.ABLED,new ParkingAttendant("attendant2"));
        parkingLotService.parkVehicle(vehicle3, Driver.ABLED,new ParkingAttendant("attendant1"));
        parkingLotService.parkVehicle(vehicle4, Driver.ABLED,new ParkingAttendant("attendant2"));
        parkingLotService.parkVehicle(vehicle5, Driver.ABLED,new ParkingAttendant("attendant1"));
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
        parkingLotService.parkVehicle(vehicle1, Driver.ABLED,new ParkingAttendant("attendant1"));
        parkingLotService.parkVehicle(vehicle2, Driver.ABLED,new ParkingAttendant("attendant2"));
        parkingLotService.parkVehicle(vehicle3, Driver.ABLED,new ParkingAttendant("attendant1"));
        parkingLotService.parkVehicle(vehicle4, Driver.ABLED,new ParkingAttendant("attendant2"));
        parkingLotService.parkVehicle(vehicle5, Driver.ABLED,new ParkingAttendant("attendant1"));
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
        parkingLotService.parkVehicle(vehicle1, Driver.ABLED,new ParkingAttendant("attendant1"));
        parkingLotService.parkVehicle(vehicle2, Driver.ABLED,new ParkingAttendant("attendant2"));
        parkingLotService.parkVehicle(vehicle3, Driver.ABLED,new ParkingAttendant("attendant1"));
        parkingLotService.parkVehicle(vehicle4, Driver.ABLED,new ParkingAttendant("attendant2"));
        parkingLotService.parkVehicle(vehicle5, Driver.ABLED,new ParkingAttendant("attendant1"));
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
        parkingLotService.parkVehicle(vehicle1, Driver.ABLED,new ParkingAttendant("attendant1"));
        parkingLotService.parkVehicle(vehicle2, Driver.ABLED,new ParkingAttendant("attendant2"));
        parkingLotService.parkVehicle(vehicle3, Driver.ABLED,new ParkingAttendant("attendant1"));
        parkingLotService.parkVehicle(vehicle4, Driver.ABLED,new ParkingAttendant("attendant2"));
        parkingLotService.parkVehicle(vehicle5, Driver.ABLED,new ParkingAttendant("attendant1"));
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
        parkingLotService.parkVehicle(vehicle1, Driver.ABLED,new ParkingAttendant("attendant1"));
        parkingLotService.parkVehicle(vehicle2, Driver.ABLED,new ParkingAttendant("attendant2"));
        parkingLotService.parkVehicle(vehicle3, Driver.ABLED,new ParkingAttendant("attendant1"));
        parkingLotService.parkVehicle(vehicle4, Driver.ABLED,new ParkingAttendant("attendant2"));
        parkingLotService.parkVehicle(vehicle5, Driver.ABLED,new ParkingAttendant("attendant1"));
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
        parkingLotService.parkVehicle(vehicle1, Driver.ABLED,new ParkingAttendant("attendant1"));
        parkingLotService.parkVehicle(vehicle4, Driver.DISABLED,new ParkingAttendant("attendant2"));
        ParkingSlot parkedSlot = parkingLotService.getParkedSlot(vehicle4);
        Assert.assertEquals(parkingSlot1,parkedSlot);
        Assert.assertEquals(2,parkingLotService.getParkedSpot(vehicle4));
    }

    @Test
    public void givenVehicleNumbersToPark_whenVehicleIsLarge_shouldAllocateRequiredSpace() {
        ParkingSlot parkingSlot1 = new ParkingSlot(2);
        ParkingSlot parkingSlot2 = new ParkingSlot(5);
        List<ParkingSlot> parkingLotList = new ArrayList<>();
        parkingLotList.add(parkingSlot1);
        parkingLotList.add(parkingSlot2);
        ParkingLotService parkingLotService = new ParkingLotService(parkingLotList);
        parkingLotService.parkVehicle(vehicle1, Driver.ABLED,new ParkingAttendant("attendant1"));
        parkingLotService.parkVehicle(vehicle7, Driver.ABLED,new ParkingAttendant("attendant2"));
        ParkingSlot parkedSlot = parkingLotService.getParkedSlot(vehicle7);
        Assert.assertEquals(parkingSlot2,parkedSlot);
    }

    @Test
    public void givenVehicleNumbersToPark_whenWantWhiteColorVehicles_shouldReturnListOfVehicles() {
        ParkingSlot parkingSlot1 = new ParkingSlot(2);
        ParkingSlot parkingSlot2 = new ParkingSlot(5);
        List<ParkingSlot> parkingSlots = new ArrayList<>();
        parkingSlots.add(parkingSlot1);
        parkingSlots.add(parkingSlot2);
        ParkingLotService parkingLotService = new ParkingLotService(parkingSlots);
        parkingLotService.parkVehicle(vehicle1, Driver.ABLED,new ParkingAttendant("attendant1"));
        parkingLotService.parkVehicle(vehicle2, Driver.ABLED,new ParkingAttendant("attendant2"));
        parkingLotService.parkVehicle(vehicle3, Driver.ABLED,new ParkingAttendant("attendant1"));
        parkingLotService.parkVehicle(vehicle4, Driver.ABLED,new ParkingAttendant("attendant2"));
        parkingLotService.parkVehicle(vehicle5, Driver.ABLED,new ParkingAttendant("attendant1"));
        List<ParkingSlot> vehicleSlots = Arrays.asList(parkingSlot1,parkingSlot1,parkingSlot2);
        List<ParkedDetails> allVehiclesBasedOnColor = parkingLotService.getAllVehiclesBasedOnProperty(Vehicle.VehicleProperty.WHITE);
        List<ParkingSlot> parkingSlotList = allVehiclesBasedOnColor.stream().map(ParkedDetails::getParkedSlot).collect(Collectors.toList());
        Assert.assertEquals(vehicleSlots,parkingSlotList);
    }

    @Test
    public void givenVehicleNumbersToPark_whenWantToyotaVehicles_shouldReturnListOfVehicles() {
        ParkingSlot parkingSlot1 = new ParkingSlot(2);
        ParkingSlot parkingSlot2 = new ParkingSlot(5);
        List<ParkingSlot> parkingSlots = new ArrayList<>();
        parkingSlots.add(parkingSlot1);
        parkingSlots.add(parkingSlot2);
        ParkingLotService parkingLotService = new ParkingLotService(parkingSlots);
        parkingLotService.parkVehicle(vehicle1, Driver.ABLED,new ParkingAttendant("attendant1"));
        parkingLotService.parkVehicle(vehicle2, Driver.ABLED,new ParkingAttendant("attendant2"));
        parkingLotService.parkVehicle(vehicle3, Driver.ABLED,new ParkingAttendant("attendant1"));
        parkingLotService.parkVehicle(vehicle4, Driver.ABLED,new ParkingAttendant("attendant2"));
        parkingLotService.parkVehicle(vehicle5, Driver.ABLED,new ParkingAttendant("attendant1"));
        List<ParkedDetails> vehicleLocations = parkingLotService.getAllVehicleBasedOnProperties(Vehicle.VehicleProperty.BLUE,
                Vehicle.VehicleProperty.TOYOTA);
        int spot = vehicleLocations.get(0).getSpot();
        Assert.assertEquals(2,spot);
        Assert.assertEquals("attendant2",vehicleLocations.get(0).getAttendant().getName());
    }

}
