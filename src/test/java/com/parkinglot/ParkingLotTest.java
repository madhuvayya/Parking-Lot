package com.parkinglot;

import com.parkinglot.controller.ParkingLot;
import com.parkinglot.controller.ParkingSlot;
import com.parkinglot.enums.Driver;
import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.model.ParkedDetails;
import com.parkinglot.model.Vehicle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
public class ParkingLotTest {

    Vehicle vehicle1;
    Vehicle vehicle2;
    Vehicle vehicle3;
    Vehicle vehicle4;
    Vehicle vehicle5;
    Vehicle vehicle6;
    Vehicle vehicle7;
    ParkingSlot parkingSlot1;
    ParkingSlot parkingSlot2;
    ParkingAttendant attendant1;
    ParkingAttendant attendant2;

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
        attendant1 = new ParkingAttendant("attendant1");
        attendant2 = new ParkingAttendant("attendant2");
        parkingSlot1 = new ParkingSlot(2);
        parkingSlot2 = new ParkingSlot(5);
    }

    @Test
    public void givenParkingSlots_whenCreated_shouldReturnNumberOfLots() {
        List<ParkingSlot> parkingSlots = new ArrayList<>();
        parkingSlots.add(parkingSlot1);
        parkingSlots.add(parkingSlot2);
        ParkingLot parkingLot = new ParkingLot(parkingSlots);
        int numberOfParkingSlots = parkingLot.parkingSlots.size();
        Assert.assertEquals(2,numberOfParkingSlots);
    }

    @Test
    public void givenVehicleNumberToPark_whenParked_shouldReturnOccupiedSpotsInALot() {
        ParkingSlot parkingSlot1 = new ParkingSlot(1);
        ParkingSlot parkingSlot2 = new ParkingSlot(3);
        List<ParkingSlot> parkingSlots = new ArrayList<>();
        parkingSlots.add(parkingSlot1);
        parkingSlots.add(parkingSlot2);
        ParkingLot parkingLot = new ParkingLot(parkingSlots);
        parkingLot.parkVehicle(vehicle1, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle2, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle3, Driver.ABLED,attendant1);
        int occupiedSpotsInASlot = parkingLot.getOccupiedSpotsInASlot(parkingSlot2);
        Assert.assertEquals(2,occupiedSpotsInASlot);
    }

    @Test
    public void givenVehicleNumberToPark_whenAllParkingLotsAreFull_shouldThrowException() {
        ParkingSlot parkingSlot1 = new ParkingSlot(1);
        ParkingSlot parkingSlot2 = new ParkingSlot(3);
        List<ParkingSlot> parkingSlots = new ArrayList<>();
        parkingSlots.add(parkingSlot1);
        parkingSlots.add(parkingSlot2);
        ParkingLot parkingLot = new ParkingLot(parkingSlots);
        try {
            parkingLot.parkVehicle(vehicle1, Driver.ABLED,attendant1);
            parkingLot.parkVehicle(vehicle2, Driver.ABLED,attendant2);
            parkingLot.parkVehicle(vehicle3, Driver.ABLED,attendant1);
            parkingLot.parkVehicle(vehicle4, Driver.ABLED,attendant2);
            parkingLot.parkVehicle(vehicle5, Driver.ABLED,attendant2);
        } catch(ParkingLotException e){
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOTS_ARE_FULL,e.type);
        }
    }

    @Test
    public void givenVehicleNumberToUnPark_whenUnParked_shouldReturnOccupiedSpots() {
        ParkingSlot parkingSlot1 = new ParkingSlot(2);
        ParkingSlot parkingSlot2 = new ParkingSlot(5);
        List<ParkingSlot> parkingSlots = new ArrayList<>();
        parkingSlots.add(parkingSlot1);
        parkingSlots.add(parkingSlot2);
        ParkingLot parkingLot = new ParkingLot(parkingSlots);
        parkingLot.parkVehicle(vehicle1, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle2, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle3, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle4, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle5, Driver.ABLED,attendant1);
        parkingLot.unParkVehicle(vehicle4);
        int occupiedSpotsInASlot = parkingLot.getOccupiedSpotsInASlot(parkingSlot2);
        Assert.assertEquals(2,occupiedSpotsInASlot);
    }

    @Test
    public void givenVehicleNumberToFindInSlot_whenFound_shouldReturnParkingSlot() {
        ParkingSlot parkingSlot1 = new ParkingSlot(2);
        ParkingSlot parkingSlot2 = new ParkingSlot(5);
        List<ParkingSlot> parkingSlots = new ArrayList<>();
        parkingSlots.add(parkingSlot1);
        parkingSlots.add(parkingSlot2);
        ParkingLot parkingLot = new ParkingLot(parkingSlots);
        parkingLot.parkVehicle(vehicle1, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle2, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle3, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle4, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle5, Driver.ABLED,attendant1);
        ParkingSlot parkedSlot = parkingLot.getParkedSlot( vehicle5);
        Assert.assertEquals(parkingSlot2,parkedSlot);
    }

    @Test
    public void givenVehicleNumberToFind_whenFound_shouldReturnParkingSlot() {
        ParkingSlot parkingSlot1 = new ParkingSlot(2);
        ParkingSlot parkingSlot2 = new ParkingSlot(5);
        List<ParkingSlot> parkingSlots = new ArrayList<>();
        parkingSlots.add(parkingSlot1);
        parkingSlots.add(parkingSlot2);
        ParkingLot parkingLot = new ParkingLot(parkingSlots);
        parkingLot.parkVehicle(vehicle1, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle2, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle3, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle4, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle5, Driver.ABLED,attendant1);
        ParkingSlot parkedSlot = parkingLot.getParkedSlot( vehicle3);
        Assert.assertNotEquals(parkingSlot2,parkedSlot);
    }

    @Test
    public void givenVehicleNumberToFind_whenFound_shouldReturnParkingSpot() {
        ParkingSlot parkingSlot1 = spy(new ParkingSlot(2));
        ParkingSlot parkingSlot2 = spy(new ParkingSlot(5));
        List<ParkingSlot> parkingLotList = new ArrayList<>();
        parkingLotList.add(parkingSlot1);
        parkingLotList.add(parkingSlot2);
        ParkingLot parkingLot = new ParkingLot(parkingLotList);
        parkingLot.parkVehicle(vehicle1, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle2, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle3, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle4, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle5, Driver.ABLED,attendant1);
        when(parkingSlot1.getParkedSpot(vehicle3)).thenReturn(4);
        int parkedSpot = parkingLot.getParkedSpot( vehicle3);
        Assert.assertEquals(4,parkedSpot);
    }

    @Test
    public void givenVehicleNumberToFind_whenFoundInParkingLot_shouldReturnParkingSpot() {
        List<ParkingSlot> parkingSlotList = new ArrayList<>();
        ParkingSlot parkingSlot3 = spy(new ParkingSlot(2));
        ParkingSlot parkingSlot4 = spy(new ParkingSlot(3));
        parkingSlotList.add(parkingSlot3);
        parkingSlotList.add(parkingSlot4);
        ParkingLot parkingLot = new ParkingLot(parkingSlotList);
        parkingLot.parkVehicle(vehicle1, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle2, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle3, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle4, Driver.ABLED,attendant2);
        doReturn(2).when(parkingSlot3).getParkedSpot(vehicle3);
        doReturn(2).when(parkingSlot4).getParkedSpot(vehicle4);
        Assert.assertEquals(2,parkingSlot3.getParkedSpot( vehicle3));
        Assert.assertEquals(2,parkingSlot4.getParkedSpot( vehicle4));
    }

    @Test
    public void givenVehicleNumber_whenParked_shouldReturnAvailableSpots() {
        List<ParkingSlot> parkingSlots = new ArrayList<>();
        parkingSlots.add(parkingSlot1);
        parkingSlots.add(parkingSlot2);
        ParkingLot parkingLot = new ParkingLot(parkingSlots);
        parkingLot.parkVehicle(vehicle1, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle2, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle3, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle4, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle5, Driver.ABLED,attendant1);
        int availableSpotsInASlot2 = parkingLot.getAvailableSpotsInASlot(parkingSlot2);
        int availableSpotsInASlot1 = parkingLot.getAvailableSpotsInASlot(parkingSlot1);
        Assert.assertEquals(2,availableSpotsInASlot2);
        Assert.assertEquals(0,availableSpotsInASlot1);
        Assert.assertNotEquals(3,availableSpotsInASlot2);
        Assert.assertNotEquals(2,availableSpotsInASlot1);
    }

    @Test
    public void givenVehicleNumbersToPark_whenParked_shouldReturnAllVehiclesInParkingLot() {
        List<ParkingSlot> parkingSlots = new ArrayList<>();
        parkingSlots.add(parkingSlot1);
        parkingSlots.add(parkingSlot2);
        ParkingLot parkingLot = new ParkingLot(parkingSlots);
        parkingLot.parkVehicle(vehicle1, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle2, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle3, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle4, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle5, Driver.ABLED,attendant1);
        int totalVehicles = parkingLot.getNumberOfVehiclesInParkingLot();
        Assert.assertEquals(5,totalVehicles);
    }

    @Test
    public void givenVehicleNumbersToPark_whenDriverDisAbled_shouldAllocateNearestSlot() {
        ParkingSlot parkingSlot1 = new ParkingSlot(2);
        ParkingSlot parkingSlot2 = new ParkingSlot(2);
        List<ParkingSlot> parkingSlots = new ArrayList<>();
        parkingSlots.add(parkingSlot1);
        parkingSlots.add(parkingSlot2);
        ParkingLot parkingLot = new ParkingLot(parkingSlots);
        parkingLot.parkVehicle(vehicle1, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle4, Driver.DISABLED,attendant2);
        ParkingSlot parkedSlot = parkingLot.getParkedSlot(vehicle4);
        Assert.assertEquals(parkingSlot1,parkedSlot);
        Assert.assertEquals(2,parkingLot.getParkedSpot(vehicle4));
    }

    @Test
    public void givenVehicleNumbersToPark_whenVehicleIsLarge_shouldAllocateRequiredSpace() {
        List<ParkingSlot> parkingSlots = new ArrayList<>();
        parkingSlots.add(parkingSlot1);
        parkingSlots.add(parkingSlot2);
        ParkingLot parkingLot = new ParkingLot(parkingSlots);
        parkingLot.parkVehicle(vehicle1, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle7, Driver.ABLED,attendant2);
        ParkingSlot parkedSlot = parkingLot.getParkedSlot(vehicle7);
        Assert.assertEquals(parkingSlot2,parkedSlot);
    }

    @Test
    public void givenVehicleNumbersToPark_whenWantWhiteColorVehicles_shouldReturnListOfVehicles() {
        List<ParkingSlot> parkingSlots = new ArrayList<>();
        parkingSlots.add(parkingSlot1);
        parkingSlots.add(parkingSlot2);
        ParkingLot parkingLot = new ParkingLot(parkingSlots);
        parkingLot.parkVehicle(vehicle1, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle2, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle3, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle4, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle5, Driver.ABLED,attendant1);
        List<ParkingSlot> vehicleSlots = Arrays.asList(parkingSlot1,parkingSlot1,parkingSlot2);
        List<ParkedDetails> allVehiclesBasedOnColor = parkingLot.getAllVehiclesBasedOnColor(Vehicle.VehicleProperty.WHITE);
        List<ParkingSlot> parkingSlotList = allVehiclesBasedOnColor.stream()
                .map(ParkedDetails::getParkedSlot)
                .collect(Collectors.toList());
        Assert.assertEquals(vehicleSlots,parkingSlotList);
    }

    @Test
    public void givenVehicleNumbersToPark_whenWantToyotaVehicles_shouldReturnListOfVehicles() {
        List<ParkingSlot> parkingSlots = new ArrayList<>();
        parkingSlots.add(parkingSlot1);
        parkingSlots.add(parkingSlot2);
        ParkingLot parkingLot = new ParkingLot(parkingSlots);
        parkingLot.parkVehicle(vehicle1, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle2, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle3, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle4, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle5, Driver.ABLED,attendant1);
        List<ParkedDetails> vehicleLocations = parkingLot.getAllVehicleBasedOnColorBrand(Vehicle.VehicleProperty.BLUE,
                Vehicle.VehicleProperty.TOYOTA);
        int spot = vehicleLocations.get(0).getSpot();
        Assert.assertEquals(2,spot);
        Assert.assertEquals("attendant2",vehicleLocations.get(0).getAttendant().getName());
    }

    @Test
    public void givenVehicleNumbersToPark_whenWantToyotaVehicles_shouldReturnListBMWVehicles() {
        List<ParkingSlot> parkingSlots = new ArrayList<>();
        parkingSlots.add(parkingSlot1);
        parkingSlots.add(parkingSlot2);
        ParkingLot parkingLot = new ParkingLot(parkingSlots);
        parkingLot.parkVehicle(vehicle1, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle2, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle3, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle4, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle5, Driver.ABLED,attendant1);
        List<ParkedDetails> vehicleLocations = parkingLot.getAllVehiclesBasedOnBrand(Vehicle.VehicleProperty.BMW);
        Assert.assertEquals(2,vehicleLocations.size());
    }

    @Test
    public void givenVehicleNumbersToPark_whenWantVehiclesParkedInLast30Minutes_shouldReturnListOfVehicles() {
        List<ParkingSlot> parkingSlots = new ArrayList<>();
        parkingSlots.add(parkingSlot1);
        parkingSlots.add(parkingSlot2);
        ParkingLot parkingLot = new ParkingLot(parkingSlots);
        parkingLot.parkVehicle(vehicle1, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle2, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle3, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle4, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle5, Driver.ABLED,attendant1);
        long duration = 30;
        List<Vehicle> vehicleList = parkingLot.getAllVehiclesBasedOnTime(duration);
        Assert.assertEquals(5,vehicleList.size());
    }

    @Test
    public void givenParingSlot_whenWantVehiclesParkedInASlot_shouldReturnListOfVehicles() {
        List<ParkingSlot> parkingSlots = new ArrayList<>();
        parkingSlots.add(parkingSlot1);
        parkingSlots.add(parkingSlot2);
        ParkingLot parkingLot = new ParkingLot(parkingSlots);
        Vehicle vehicle = new Vehicle("TS05GH6325", Vehicle.VehicleProperty.SMALL, Vehicle.VehicleProperty.WHITE,
                Vehicle.VehicleProperty.TOYOTA);
        Vehicle vehicle2 = new Vehicle("TS06JK4612", Vehicle.VehicleProperty.LARGE, Vehicle.VehicleProperty.WHITE,
                Vehicle.VehicleProperty.BMW);
        Vehicle vehicle3 = new Vehicle("TS11GS7135", Vehicle.VehicleProperty.LARGE, Vehicle.VehicleProperty.WHITE,
                Vehicle.VehicleProperty.TOYOTA);
        parkingLot.parkVehicle(vehicle1, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle2, Driver.DISABLED,attendant2);
        parkingLot.parkVehicle(vehicle, Driver.DISABLED,attendant1);
        parkingLot.parkVehicle(vehicle4, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle3, Driver.DISABLED,attendant1);
        List<Integer> vehiclesInASlot = parkingLot.getVehiclesInASlot(parkingSlot2);
        Assert.assertEquals(1,vehiclesInASlot.size());
    }

    @Test
    public void givenVehicleNumbersToPark_whenWantVehiclesNumbersParkedInLot_shouldReturnListOfVehicleNumbers() {
        List<ParkingSlot> parkingSlots = new ArrayList<>();
        parkingSlots.add(parkingSlot1);
        parkingSlots.add(parkingSlot2);
        ParkingLot parkingLot = new ParkingLot(parkingSlots);
        parkingLot.parkVehicle(vehicle1, Driver.ABLED,attendant1);
        parkingLot.parkVehicle(vehicle2, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle3, Driver.DISABLED,attendant1);
        parkingLot.parkVehicle(vehicle4, Driver.ABLED,attendant2);
        parkingLot.parkVehicle(vehicle5, Driver.ABLED,attendant1);
        List<String> vehicleNumbers = Arrays.asList("TA07EC3633","AP24AC7684","TN11WA4563","KA12TH4651","TS35TV7684");
        List<String> allVehicleNumbers = parkingLot.getAllVehicleNumbers();
        boolean containsAll = vehicleNumbers.containsAll(allVehicleNumbers);
        Assert.assertTrue(containsAll);
    }

}
