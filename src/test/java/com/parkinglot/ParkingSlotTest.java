package com.parkinglot;

import com.parkinglot.service.ParkingSlot;
import com.parkinglot.enums.Driver;
import com.parkinglot.exception.ParkingLotException;
import com.parkinglot.model.Vehicle;
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
    Vehicle vehicle7;
    ParkingAttendant attendant1;
    ParkingAttendant attendant2;

    @Before
    public void setUp() throws Exception {
        parkingSlot = new ParkingSlot(5);
        vehicle1 = new Vehicle("TA07EC3633", Vehicle.VehicleProperty.SMALL, Vehicle.VehicleProperty.WHITE,
                Vehicle.VehicleProperty.TOYOTA);
        vehicle2 = new Vehicle("AP24AC7684", Vehicle.VehicleProperty.SMALL, Vehicle.VehicleProperty.BLACK,
                Vehicle.VehicleProperty.BMW);
        vehicle3 = new Vehicle("TN11WA4563", Vehicle.VehicleProperty.SMALL, Vehicle.VehicleProperty.WHITE,
                Vehicle.VehicleProperty.TOYOTA);
        vehicle4 = new Vehicle("KA12TH4651", Vehicle.VehicleProperty.SMALL, Vehicle.VehicleProperty.BLUE,
                Vehicle.VehicleProperty.BMW);
        vehicle5 = new Vehicle("TS35TV7684", Vehicle.VehicleProperty.SMALL, Vehicle.VehicleProperty.WHITE,
                Vehicle.VehicleProperty.TOYOTA);
        vehicle6 = new Vehicle("TS08CV5421", Vehicle.VehicleProperty.SMALL, Vehicle.VehicleProperty.RED,
                Vehicle.VehicleProperty.TOYOTA);
        vehicle7 = new Vehicle("KA42HM4651", Vehicle.VehicleProperty.LARGE, Vehicle.VehicleProperty.WHITE,
                Vehicle.VehicleProperty.BMW);
        attendant1 = new ParkingAttendant("attendant1");
        attendant2 = new ParkingAttendant("attendant2");
    }

    @Test
    public void givenVehicleNumberToPark_whenParked_shouldReturnTrue() {
        parkingSlot.parkVehicle(vehicle1, Driver.ABLED,parkingSlot,attendant1);
        int occupiedSpots = parkingSlot.getOccupiedSpots();
        Assert.assertEquals(1,occupiedSpots);
    }

    @Test
    public void givenVehicleNumbersToPark_whenParked_shouldReturnNumberOfVehiclesParked() {
        parkingSlot.parkVehicle(vehicle2,Driver.ABLED,parkingSlot,attendant2);
        int numberOfVehicles = parkingSlot.getOccupiedSpots();
        Assert.assertEquals(1,numberOfVehicles);
    }

    @Test
    public void givenVehicleNumberToUnPark_whenUnParked_shouldReturnTrue() {
        parkingSlot.parkVehicle(vehicle1,Driver.ABLED,parkingSlot,attendant1);
        parkingSlot.parkVehicle(vehicle2,Driver.ABLED,parkingSlot,attendant2);
        parkingSlot.unParkVehicle(vehicle2);
        int occupiedSpots = parkingSlot.getOccupiedSpots();
        Assert.assertEquals(1,occupiedSpots);
    }

    @Test
    public void givenWrongVehicleNumberToUnPark_whenNot_shouldReturnTrue() {
        parkingSlot.parkVehicle(vehicle1,Driver.ABLED,parkingSlot,attendant1);
        parkingSlot.parkVehicle(vehicle2,Driver.ABLED,parkingSlot,attendant2);
        try {
            parkingSlot.unParkVehicle(new Vehicle("TS08CV5421", Vehicle.VehicleProperty.SMALL,
                    Vehicle.VehicleProperty.WHITE, Vehicle.VehicleProperty.TOYOTA));
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NOT_IN_SLOTS_LIST,e.type);
        }
    }

    @Test
    public void givenVehicleNumbersToPark_whenParkingLotIsFull_shouldNotThrowException() {
        parkingSlot.parkVehicle(vehicle1,Driver.ABLED,parkingSlot,attendant1);
        parkingSlot.parkVehicle(vehicle2,Driver.ABLED,parkingSlot,attendant2);
        parkingSlot.parkVehicle(vehicle3,Driver.ABLED,parkingSlot,attendant1);
        parkingSlot.parkVehicle(vehicle4,Driver.ABLED,parkingSlot,attendant2);
        parkingSlot.parkVehicle(vehicle5,Driver.ABLED,parkingSlot,attendant1);
    }

    @Test
    public void givenVehicleNumbersToPark_whenParkingLotIsFull_shouldThrowException() {
        try {
            parkingSlot.parkVehicle(vehicle1,Driver.ABLED,parkingSlot,attendant1);
            parkingSlot.parkVehicle(vehicle2,Driver.ABLED,parkingSlot,attendant2);
            parkingSlot.parkVehicle(vehicle3,Driver.ABLED,parkingSlot,attendant1);
            parkingSlot.parkVehicle(vehicle4,Driver.ABLED,parkingSlot,attendant2);
            parkingSlot.parkVehicle(vehicle5,Driver.ABLED,parkingSlot,attendant1);
            parkingSlot.parkVehicle(vehicle6,Driver.ABLED,parkingSlot,attendant2);
        } catch (ParkingLotException e){
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL,e.type);
        }
    }

    @Test
    public void givenSameVehicleNumberToPark_whenSameNumberEntered_shouldThrowException() {
        try {
            parkingSlot.parkVehicle(vehicle1,Driver.ABLED,parkingSlot,attendant1);
            parkingSlot.parkVehicle(vehicle1,Driver.ABLED,parkingSlot,attendant2);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.EXISTING,e.type);
        }
    }

    @Test
    public void givenVehicleNumber_whenFound_shouldReturnParkedSpot() {
        parkingSlot.parkVehicle(vehicle1,Driver.ABLED,parkingSlot,attendant1);
        parkingSlot.parkVehicle(vehicle2,Driver.ABLED,parkingSlot,attendant2);
        parkingSlot.parkVehicle(vehicle3,Driver.ABLED,parkingSlot,attendant1);
        int parkedSpot = parkingSlot.getParkedSpot(vehicle2);
        Assert.assertEquals(2,parkedSpot);
    }

    @Test
    public void givenVehicleNumbersToUnPark_whenUnPark_shouldReturnParkedTime() {
        parkingSlot.parkVehicle(vehicle1,Driver.ABLED,parkingSlot,attendant1);
        long parkedTime = parkingSlot.getParkedTime(vehicle1);
        Assert.assertEquals(0,parkedTime);
    }

    @Test
    public void givenVehicleNumbersToPark_whenDriverDisabledPerson_shouldParkInFirstSpot() {
        parkingSlot.parkVehicle(vehicle1,Driver.ABLED,parkingSlot,attendant1);
        parkingSlot.parkVehicle(vehicle2,Driver.ABLED,parkingSlot,attendant2);
        parkingSlot.parkVehicle(vehicle3,Driver.ABLED,parkingSlot,attendant1);
        parkingSlot.parkVehicle(vehicle4,Driver.DISABLED,parkingSlot,attendant2);
        Assert.assertEquals(1, parkingSlot.getParkedSpot(vehicle1));
    }

    @Test
    public void givenVehicleNumbersToPark_whenDriverSecondDisabledPerson_shouldParkInSecondSpot() {
        parkingSlot.parkVehicle(vehicle1, Driver.ABLED,parkingSlot,attendant1);
        parkingSlot.parkVehicle(vehicle2, Driver.ABLED,parkingSlot,attendant2);
        parkingSlot.parkVehicle(vehicle3, Driver.ABLED,parkingSlot,attendant1);
        parkingSlot.parkVehicle(vehicle4, Driver.DISABLED,parkingSlot,attendant2);
        parkingSlot.parkVehicle(vehicle5, Driver.DISABLED,parkingSlot,attendant1);
        Assert.assertEquals(2, parkingSlot.getParkedSpot(vehicle2));
    }

}

