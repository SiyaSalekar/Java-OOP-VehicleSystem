package org.example;


import java.awt.print.Book;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.DateTimeException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BookingManager implements Serializable
{
    private final ArrayList<Booking> bookingList;
    private final PassengerStore passengerStore;
    private final VehicleManager vehicleManager;

    // Constructor
    public BookingManager(String filename,PassengerStore passengerStore, VehicleManager vehicleManager) {
        this.bookingList = new ArrayList<>();
        this.passengerStore = passengerStore;
        this.vehicleManager = vehicleManager;
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
    public void addBooking(int passengerId, int vehicleId, int year, int month, int day, int hour, int min, int sec, double startLatitude, double endLatitude, double startLongitude, double endLongitude){
        Vehicle v = vehicleManager.findByVehicleId(vehicleId);
        Passenger p = passengerStore.findPassengerById(passengerId);
        if(v!=null && p!=null) {
            if(checkAvailability(vehicleId,year,month,day)) {

                //cost calculation
                double depotLat = v.getDepotGPSLocation().getLatitude();
                double depotLong= v.getDepotGPSLocation().getLongitude();
                double dist = distance(depotLat,startLatitude,depotLong,startLongitude);
                dist+=distance(startLatitude,endLatitude,startLongitude, endLongitude);
                dist+=distance(endLatitude,depotLat,endLongitude,depotLong);

                //total cost - distance in miles
                double cost = dist*v.getCostPerMile();

                //add booking
                bookingList.add(new Booking(passengerId, vehicleId,
                        year, month, day, hour, min, sec, startLatitude, endLatitude, startLongitude, endLongitude, cost));
                System.out.println("Booked Successfully");
            }
            else{
                //if null
                System.out.println("Vehicle already booked for the day - Booking failed");
            }

        }
        else{
            if(p==null) {
                System.out.println("Passenger to be booked does not exist - Cannot be booked");
            }
            if (v == null) {
                System.out.println("Vehicle to be booked does not exist - Cannot be booked");
            }
        }

    }
    public void displayIDList(){
        ArrayList <Passenger> passengerList= passengerStore.getAllPassengers();
        ArrayList <Vehicle> vehicleList= vehicleManager.getVehicleList();
        System.out.println();
        System.out.println("--Choose From--");
        System.out.println();
        System.out.println("PASSENGERS");
        System.out.printf("%-5s %-6s","ID","Name");
        System.out.println();
        System.out.println("-------------------");
        for(Passenger p : passengerList){
            System.out.printf("%-5d %-6s",p.getId(),p.getName());
            System.out.println();
        }
        System.out.println();
        System.out.println("VEHICLES");
        System.out.printf("%-5s %-6s %-6s","ID","Type","Model");
        System.out.println();
        System.out.println("---------------------");
        for(Vehicle v : vehicleList){
            System.out.printf("%-5d %-6s %-6s",v.getId(),v.getType(),v.getModel());
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    public double getDist(int vehicleId, int passengerId, int bookingId){
        Vehicle v = vehicleManager.findByVehicleId(vehicleId);
        Passenger p = passengerStore.findPassengerById(passengerId);
        Booking b = this.findBookingById(bookingId);
        double dist=0;
        if(v!=null && p!=null) {
            double depotLat = v.getDepotGPSLocation().getLatitude();
            double depotLong = v.getDepotGPSLocation().getLongitude();
            double StartLat = b.getStartLocation().getLatitude();
            double StartLong = b.getStartLocation().getLongitude();
            double EndLat = b.getEndLocation().getLatitude();
            double EndLong = b.getEndLocation().getLongitude();
            dist += distance(depotLat, StartLat, depotLong, StartLong);
            dist += distance(StartLat, EndLat, StartLong, EndLong);
            dist += distance(EndLat, depotLat, EndLong, depotLong);
        }else{
            System.out.println("Vehicle not found");
        }
        return dist;
    }

    public Booking findBookingById(int id){
        for(Booking b: bookingList) {
            if (id==b.getBookingId()){
                return b;
            }
        }
        return null;
    }

    public double averageLengthJourney(){
        double count =0;
        double total=0;
        for(Booking b: bookingList){
            total+=getDist(b.getVehicleId(),b.getPassengerId(),b.getBookingId());
            count++;
        }
        return total/count;
    }

    public ArrayList<Booking> displayBookingByPassenger(int passengerId){
        ArrayList<Booking> bookingByPassenger = new ArrayList<>();
        Passenger p = passengerStore.findPassengerById(passengerId);
        if(p!=null) {
            for (Booking b : bookingList) {
                if (b.getPassengerId() == p.getId()) {
                    bookingByPassenger.add(b);
                }
            }
        }
        else{
            System.out.println("Passenger does not exist");
        }
        BookingDateComparator bComp = new BookingDateComparator();
        Collections.sort(bookingByPassenger, bComp);
        return bookingByPassenger;
    }

    public ArrayList<Booking> sortByDateTime(){
        ArrayList<Booking> bookingSort = new ArrayList<>();
        bookingSort.addAll(bookingList);
        BookingDateComparator bComp = new BookingDateComparator();
        Collections.sort(bookingSort, bComp);
        return bookingSort;
    }

    public void deleteBookingById(int id){
        boolean found = false;
        for(Booking b : bookingList) {
            if (id == b.getBookingId()) {
                bookingList.remove(b);
                System.out.println("Booking Cancelled");
                found = true;
                break;
            }
        }
        if (found==false){
            System.out.println("Booking Cannot be Cancelled - Not found");
        }
    }

    public ArrayList<Booking> displayFutureBookings(){
        ArrayList<Booking> currentBooking = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for(Booking b:bookingList){
            if(b.getBookingDateTime().isAfter(now)){
                currentBooking.add(b);
            }
        }
        BookingDateComparator bComp = new BookingDateComparator();
        Collections.sort(currentBooking, bComp);
        return currentBooking;
    }



    public Booking findBookingByPassengerId(int id){
        for(Booking b: bookingList) {
            if (id==b.getPassengerId()){
                return b;
            }
        }
        return null;
    }


    public double distance(double lat1, double lat2, double lon1, double lon2)
    {
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

       //in miles
        double r = 3956;

        return(c * r);
    }




   //need for add
    public boolean checkAvailability(int vehicleId, int year, int month, int day){
        for(Booking b: bookingList){
            if(b.getVehicleId()==vehicleId && b.getBookingDateTime().getYear()==year && b.getBookingDateTime().getMonthValue()==month && b.getBookingDateTime().getDayOfMonth()==day){
                return false;
            }

        }
        return true;
    }

    public void storeToFileBooking() throws IOException {

        FileWriter fWriter = new FileWriter("bookings.txt");
        String output = "";

        for(Booking b : bookingList) {

            int bookingId = b.getBookingId();
            int passengerId = b.getPassengerId();
            int vehicleId= b.getVehicleId();
            int year =b.getBookingDateTime().getYear();
            int month = b.getBookingDateTime().getMonthValue();
            int day= b.getBookingDateTime().getDayOfMonth();
            int hour=b.getBookingDateTime().getHour();
            int min= b.getBookingDateTime().getMinute();
            int sec= b.getBookingDateTime().getSecond();
            double startLatitude = b.getStartLocation().getLatitude();
            double endLatitude= b.getEndLocation().getLatitude();
            double startLongitude= b.getStartLocation().getLongitude();
            double endLongitude= b.getEndLocation().getLongitude();
            double cost= b.getCost();

            output = bookingId+","+passengerId+"," +vehicleId+"," +year+"," +month+"," +day+"," +hour+"," +min+"," +sec+"," +startLatitude+"," +endLatitude+"," +startLongitude+"," +endLongitude+","+cost;

            fWriter.append(output+"\n");
        }
        fWriter.close();

        System.out.println("Changes applied to the booking File");
    }


    public void editBooking() throws IOException {
        System.out.println("Enter your passenger Id");
        Scanner kb = new Scanner(System.in);
        int passengerId = kb.nextInt();
        Booking b = findBookingByPassengerId(passengerId);
        if(b!=null){
        System.out.println("Your booking Before Edit");
        System.out.println(b);
        final String MENU_ITEMS = "\n*** EDIT OPTIONS ***\n"
                + "1. Vehicle Id\n"
                + "2. Date\n"
                + "3. Time\n"
                + "4. start location\n"
                + "5. end location\n"
                + "6. Exit\n"
                + "Enter Option [1,6]";

        Scanner keyboard = new Scanner(System.in);
        int option = 0;
        do
        {
            System.out.println("\n"+MENU_ITEMS);
            try
            {
                String usersInput = keyboard.nextLine();
                option = Integer.parseInt(usersInput);
                switch (option)
                {
                    case 1:
                        System.out.println("Enter new Vehicle id");
                        int vehicleId = kb.nextInt();
                        Vehicle v = vehicleManager.findByVehicleId(vehicleId);
                        if(v!=null && checkAvailability(vehicleId,b.getBookingDateTime().getYear(),b.getBookingDateTime().getMonthValue(),b.getBookingDateTime().getDayOfMonth())){
                            b.setVehicleId(vehicleId);
                            System.out.println("Changes applied");
                        }
                        else{
                            if(v==null) {
                                System.out.println("Vehicle doesn't exist");
                            }
                            if(!checkAvailability(vehicleId,b.getBookingDateTime().getYear(),b.getBookingDateTime().getMonthValue(),b.getBookingDateTime().getDayOfMonth())){
                                System.out.println("Vehicle is Not Available for the requested Date");
                            }
                        }
                        break;
                    case 2:
                        System.out.println("New Date");
                        System.out.println("Enter Year");
                        int yearf = kb.nextInt();
                        System.out.println("Enter Month");
                        int monthf= kb.nextInt();
                        System.out.println("Enter Day");
                        int dayf = kb.nextInt();
                        int hourf=b.getBookingDateTime().getHour();
                        int minf=b.getBookingDateTime().getMinute();
                        int secf=b.getBookingDateTime().getSecond();
                        if(checkAvailability(b.getVehicleId(),yearf,monthf,dayf)) {
                            b.setBookingDateTime(LocalDateTime.of(yearf, monthf, dayf, hourf, minf, secf));
                            System.out.println("Changes applied");
                        }
                        else{
                            System.out.println("Vehicle Not Available for the date entered");
                        }
                        break;
                    case 3:
                        System.out.println("New Time");
                        int year = b.getBookingDateTime().getYear();
                        int month= b.getBookingDateTime().getMonthValue();;
                        int day = b.getBookingDateTime().getDayOfMonth();
                        System.out.println("Enter Hours");
                        int hour=kb.nextInt();
                        System.out.println("Enter Minutes");
                        int min=kb.nextInt();
                        System.out.println("Enter Seconds");
                        int sec=kb.nextInt();
                        b.setBookingDateTime(LocalDateTime.of(year, month, day,hour, min, sec));
                        System.out.println("Changes applied");
                        break;
                    case 4:
                        System.out.println("New Start Location");
                        System.out.println("Enter start latitude");
                        double slat = kb.nextDouble();
                        System.out.println("Enter start longitude");
                        double slong = kb.nextDouble();
                        LocationGPS newLoc = new LocationGPS(slat, slong);
                        b.setStartLocation(newLoc);
                        System.out.println("Changes applied");
                        break;
                    case 5:
                        System.out.println("New End Location");
                        System.out.println("Enter End latitude");
                        double elat = kb.nextDouble();
                        System.out.println("Enter End longitude");
                        double elong = kb.nextDouble();
                        LocationGPS newLocEnd = new LocationGPS(elat, elong);
                        b.setStartLocation(newLocEnd);
                        System.out.println("Changes applied");
                        break;
                    case 6:
                        System.out.println("Exiting Edit Menu");
                        break;
                    default:
                        System.out.print("Invalid Number");
                        break;
                }

            } catch (InputMismatchException |NumberFormatException e)
            {
                System.out.print("Invalid option - please enter number in range");
            }
            catch (DateTimeException r){
                System.out.println("Invalid Date/Time");
            }
        } while (option != 6);
        }
        else{
            System.out.println("No Bookings exist for passenger "+passengerId);
        }
    }




        @Override
    public String toString() {
        return "BookingManager{" +
                "bookingList=" + bookingList +
                '}';
    }

}
