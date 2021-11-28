package org.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class BookingManager
{
    private final ArrayList<Booking> bookingList;
    private PassengerStore passengerStore;
    private VehicleManager vehicleManager;

    // Constructor
    public BookingManager(String filename) {
        this.bookingList = new ArrayList<>();
        loadBookings(filename);
    }


    public ArrayList<Booking> getBookingList() {
        return bookingList;
    }


    //TODO implement functionality as per specification

    private void loadBookings(String filename) {

        try {
            Scanner sc = new Scanner(new File(filename));
            sc.useDelimiter("[,\r\n]+");

            while (sc.hasNext()) {
                int bookingId = sc.nextInt();
                int passengerId = sc.nextInt();
                int vehicleId= sc.nextInt();
                int year = sc.nextInt();
                int month = sc.nextInt();
                int day= sc.nextInt();
                int hour= sc.nextInt();
                int min= sc.nextInt();
                int sec= sc.nextInt();
                double startLatitude = sc.nextDouble();
                double endLatitude= sc.nextDouble();
                double startLongitude= sc.nextDouble();
                double endLongitude= sc.nextDouble();
                double cost= sc.nextDouble();
                bookingList.add(new Booking(bookingId,passengerId,vehicleId,
                        year,month,day,hour,min,sec,startLatitude,endLatitude,startLongitude,endLongitude,cost));
            }
            sc.close();

        } catch (IOException e) {
            System.out.println("Exception thrown. " + e);
        }
    }

    public void displayAllBookings() {
        for (Booking b : bookingList)
            System.out.println(b.toString());
    }

    public ArrayList<Booking> sortByCost(){
        ArrayList<Booking> bookingSort = new ArrayList<>();
        bookingSort.addAll(bookingList);
        Collections.sort(bookingSort);
        return bookingSort;
    }

    @Override
    public String toString() {
        return "BookingManager{" +
                "bookingList=" + bookingList +
                '}';
    }

}
