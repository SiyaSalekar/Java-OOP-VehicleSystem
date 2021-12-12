package org.example;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Unit test for Vehicles App.
 */
public class AppTest 
{

    // Test construction of Van object.
    @Test
    public void createVan()
    {
        Van van = new Van("Truck","Nissan","Urvan",4,"181MN6538107",
                6.00,2021,05,24,126000,53.2543,-6.4444,240);
        assertEquals("Nissan", van.getMake());
        assertEquals("Urvan", van.getModel());
        assertEquals("181MN6538107", van.getRegistration());
        assertEquals(6.00, van.getCostPerMile(),0.005);
        assertEquals(2021, van.getLastServicedDate().getYear());
        assertEquals(5, van.getLastServicedDate().getMonthValue());
        assertEquals(24, van.getLastServicedDate().getDayOfMonth());
        assertEquals(126000,van.getMileage());
        assertEquals(53.2543, van.getDepotGPSLocation().getLatitude(),0.00005);
        assertEquals(-6.4444, van.getDepotGPSLocation().getLongitude(),0.00005);
        assertEquals(240,van.getLoadSpace(),0.05);
    }

    //Tests for Add Booking

    //Duplicate Exists
    @Test
    public void addBookingTest1()
    {
        PassengerStore passengerStore = new PassengerStore("passengers.txt");
        VehicleManager vehicleManager = new VehicleManager("vehicles.txt");
        BookingManager bookingManager = new BookingManager("bookings.txt",passengerStore,vehicleManager);
        String add = bookingManager.addBooking(101,107,2002,12,12,12,12,12,2.340,12.0,83.33,3.4545);
        assertEquals("Vehicle already booked for the day - Booking failed",add);

    }
    //Successfully Booked
    @Test
    public void addBookingTest2()
    {
        PassengerStore passengerStore = new PassengerStore("passengers.txt");
        VehicleManager vehicleManager = new VehicleManager("vehicles.txt");
        BookingManager bookingManager = new BookingManager("bookings.txt",passengerStore,vehicleManager);
        String add = bookingManager.addBooking(109,107,2004,12,12,12,12,12,2.340,12.0,83.33,3.4545);
        assertEquals("booked successfully",add);

    }
    //when passenger doesn't exist / Passenger = Null
    @Test
    public void addBookingTest3()
    {
        PassengerStore passengerStore = new PassengerStore("passengers.txt");
        VehicleManager vehicleManager = new VehicleManager("vehicles.txt");
        BookingManager bookingManager = new BookingManager("bookings.txt",passengerStore,vehicleManager);
        String add = bookingManager.addBooking(10,107,2004,12,12,12,12,12,2.340,12.0,83.33,3.4545);
        assertEquals("Passenger to be booked does not exist - Cannot be booked",add);

    }
    //when Vehicle doesn't exist / Vehicle = Null
    @Test
    public void addBookingTest4()
    {
        PassengerStore passengerStore = new PassengerStore("passengers.txt");
        VehicleManager vehicleManager = new VehicleManager("vehicles.txt");
        BookingManager bookingManager = new BookingManager("bookings.txt",passengerStore,vehicleManager);
        String add = bookingManager.addBooking(101,10,2004,12,12,12,12,12,2.340,12.0,83.33,3.4545);
        assertEquals("Vehicle to be booked does not exist - Cannot be booked",add);

    }

    //Test for Passenger
    @Test
    public void createPassengerTest()
    {
        Passenger p = new Passenger("Siya Salekar","siyasalekar@gmail.com","0927892789",0.9897,9.32424);
        assertEquals("Siya Salekar", p.getName());
        assertEquals("siyasalekar@gmail.com", p.getEmail());
        assertEquals("0927892789", p.getPhone());
        assertEquals(0.9897, p.getLocation().getLatitude(),0.00005);
        assertEquals(9.32424, p.getLocation().getLongitude(),0.00005);

    }

    //Average Length of Journey Bookings
    //Changes as Program saves new data
    //So might fail if Data changed in Files
    //Therefore Please test this before any edits!
    @Test
    public void AvgLengthJourneyTest()
    {
        PassengerStore passengerStore = new PassengerStore("passengers.txt");
        VehicleManager vehicleManager = new VehicleManager("vehicles.txt");
        BookingManager bookingManager = new BookingManager("bookings.txt",passengerStore,vehicleManager);
        assertEquals(11716.8627,bookingManager.averageLengthJourney(),0.00005);
    }


}
