package org.example;

import java.awt.print.Book;
import java.time.LocalDateTime;

class Booking
{
    private int bookingId;
    private int passengerId;
    private int vehicleId;
    private LocalDateTime bookingDateTime;
    private LocationGPS startLocation;
    private LocationGPS endLocation;

    private double cost;  //Calculated at booking time
    private IdGenerator idGenerator = IdGenerator.getInstance("next-id-store.txt");  // get access to the id Generator

    //TODO - see specification

    public Booking(int bookingId, int passengerId, int vehicleId, LocalDateTime bookingDateTime, LocationGPS startLocation, LocationGPS endLocation, double cost, IdGenerator idGenerator) {
        this.bookingId = bookingId;
        this.passengerId = passengerId;
        this.vehicleId = vehicleId;
        this.bookingDateTime = bookingDateTime;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.cost = cost;
        this.idGenerator = idGenerator;
    }

    public Booking(int passengerId, int vehicleId, LocalDateTime bookingDateTime, LocationGPS startLocation, LocationGPS endLocation, double cost, IdGenerator idGenerator) {
        this.bookingId = idGenerator.getNextId();;
        this.passengerId = passengerId;
        this.vehicleId = vehicleId;
        this.bookingDateTime = bookingDateTime;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.cost = cost;
        this.idGenerator = idGenerator;
    }



}
