package org.example;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;
        Booking booking = (Booking) o;
        return getBookingId() == booking.getBookingId() && getPassengerId() == booking.getPassengerId() && getVehicleId() == booking.getVehicleId() && Objects.equals(getBookingDateTime(), booking.getBookingDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookingId(), getPassengerId(), getVehicleId(), getBookingDateTime());
    }

    public Booking(int bookingId, int passengerId, int vehicleId, int year, int month, int day, int hour, int min, int sec, double startLatitude, double endLatitude, double startLongitude, double endLongitude, double cost) {
        this.bookingId = bookingId;
        this.passengerId = passengerId;
        this.vehicleId = vehicleId;
        this.bookingDateTime = LocalDateTime.of(year,month,day,hour,min,sec);
        this.startLocation = new LocationGPS(startLatitude,startLongitude);
        this.endLocation = new LocationGPS(endLatitude,endLongitude);
        this.cost = cost;
    }


    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", passengerId=" + passengerId +
                ", vehicleId=" + vehicleId +
                ", bookingDateTime=" + bookingDateTime +
                ", startLocation=" + startLocation +
                ", endLocation=" + endLocation +
                ", cost=" + cost +
                ", idGenerator=" + idGenerator +
                '}';
    }

    public Booking(int passengerId, int vehicleId, int year, int month, int day, int hour, int min, int sec, double startLatitude, double endLatitude, double startLongitude, double endLongitude, double cost) {
        this.bookingId = idGenerator.getNextId();;
        this.passengerId = passengerId;
        this.vehicleId = vehicleId;
        this.bookingDateTime = LocalDateTime.of(year,month,day,hour,min,sec);
        this.startLocation = new LocationGPS(startLatitude,startLongitude);
        this.endLocation = new LocationGPS(endLatitude,endLongitude);
        this.cost = cost;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(LocalDateTime bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    public LocationGPS getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LocationGPS startLocation) {
        this.startLocation = startLocation;
    }

    public LocationGPS getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LocationGPS endLocation) {
        this.endLocation = endLocation;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public IdGenerator getIdGenerator() {
        return idGenerator;
    }

    public void setIdGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }
}
