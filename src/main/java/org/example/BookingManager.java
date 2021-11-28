package org.example;

import java.util.ArrayList;

public class BookingManager
{
    private final ArrayList<Booking> bookingList;
    private PassengerStore passengerStore;
    private VehicleManager vehicleManager;

    // Constructor
    public BookingManager() {
        this.bookingList = new ArrayList<>();
        ArrayList<Passenger> passengerList = new ArrayList<>();
        passengerList = passengerStore.getAllPassengers();
        ArrayList<Vehicle> vehicleList = new ArrayList<>();
        vehicleList = vehicleManager.getVehicleList();
    }



    public ArrayList<Booking> getBookingList() {
        return bookingList;
    }

    //TODO implement functionality as per specification

    @Override
    public String toString() {
        return "BookingManager{" +
                "bookingList=" + bookingList +
                '}';
    }

}
