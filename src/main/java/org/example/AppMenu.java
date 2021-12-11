package org.example;


import java.awt.print.Book;
import java.io.IOException;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AppMenu {
        // Components (objects) used in this App
        PassengerStore passengerStore;  // encapsulates access to list of Passengers
        VehicleManager vehicleManager;  // encapsulates access to list of Vehicles
        BookingManager bookingManager;  // deals with all bookings

        public static void main(String[] args) {

        }

        public void start() {
            // create PassengerStore and load all passenger records from text file
            passengerStore = new PassengerStore("passengers.txt");

            // create VehicleManager, and load all vehicles from text file
            vehicleManager = new VehicleManager("vehicles.txt");

            //create BookingManager, and load all bookings from bookings.txt file
            bookingManager = new BookingManager("bookings.txt",passengerStore,vehicleManager);



            try {
                displayMainMenu();        // User Interface - Menu
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //Main Menu
        private void displayMainMenu() throws IOException {

            final String MENU_ITEMS = "\n*** MAIN MENU OF OPTIONS ***\n"
                    + "1. Passengers\n"
                    + "2. Vehicles\n"
                    + "3. Bookings\n"
                    + "4. Exit\n"
                    + "Enter Option [1,4]";

            final int PASSENGERS = 1;
            final int VEHICLES = 2;
            final int BOOKINGS = 3;
            final int EXIT = 4;

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
                        case PASSENGERS:
                            System.out.println("Passengers option chosen");
                            displayPassengerMenu();
                            break;
                        case VEHICLES:
                            System.out.println("Vehicles option chosen");
                            displayVehicleMenu();
                            break;
                        case BOOKINGS:
                            System.out.println("Bookings option chosen");
                            displayBookingMenu();
                            break;
                        case EXIT:
                            System.out.println("Exit Menu option chosen");
                            passengerStore.storeToFilePassenger();
                            vehicleManager.storeToFileVehicle();
                            bookingManager.storeToFileBooking();
                            break;
                        default:
                            System.out.print("Invalid option - please enter number in range");
                            break;
                    }

                } catch (InputMismatchException |NumberFormatException e)
                {
                    System.out.print("Invalid option - please enter number in range");
                }
            } while (option != EXIT);

            System.out.println("\nExiting Main Menu, goodbye.");

        }

        // Sub-Menu for Passenger operations

        private void displayPassengerMenu() {
            Scanner keyboard = new Scanner(System.in);
            Scanner kb = new Scanner(System.in);
            final String MENU_ITEMS = "\n*** PASSENGER MENU ***\n"
                    + "1. Show all Passengers\n"
                    + "2. Find Passenger by Name\n"
                    + "3. Add Passenger\n"
                    + "4. Sort Passenger by Name\n"
                    + "5. Edit Passenger\n"
                    + "6. Delete Passenger\n"
                    + "7. Exit\n"
                    + "Enter Option [1,7]";

            final int SHOW_ALL = 1;
            final int FIND_BY_NAME = 2;
            final int ADD_PASSENGER = 3;
            final int SORT_NAME = 4;
            final int EDIT_PASSENGER = 5;
            final int DELETE_PASSENGER = 6;
            final int EXIT = 7;

            int option = 0;
            do {
                System.out.println("\n" + MENU_ITEMS);
                try {
                    String usersInput = kb.nextLine();
                    option = Integer.parseInt(usersInput);
                    switch (option) {
                        case SHOW_ALL:
                            System.out.println("Display ALL Passengers");
                            passengerStore.displayAllPassengers();
                            break;
                        case FIND_BY_NAME:
                            System.out.println("Find Passenger by Name");
                            System.out.println("Enter passenger name: ");
                            String name = keyboard.nextLine();
                            ArrayList<Passenger> pByName = passengerStore.findPassengerByName(name);
                            for(Passenger p : pByName){
                                System.out.println(p);
                            }
                            if (pByName.size()==0)
                                System.out.println("No passenger matching the name \"" + name + "\"");
                            break;
                        case ADD_PASSENGER:
                            displayAddPassengerMenu();
                            break;
                        case SORT_NAME:
                            System.out.println("--Passengers Ordered By Name--");
                            ArrayList<Passenger> passengerSort = passengerStore.sortByName();
                            for(Passenger ps : passengerSort) {
                                System.out.println(ps.toString());
                            }
                            break;
                        case EDIT_PASSENGER:
                            passengerStore.editPassenger();
                            break;
                        case DELETE_PASSENGER:
                            try {
                                System.out.println("Enter Passenger Id");
                                int pId = keyboard.nextInt();
                                passengerStore.deletePassenger(pId);
                            }
                            catch (InputMismatchException e){
                                System.out.println("Id is not a Number");
                            }
                            break;
                        case EXIT:
                            System.out.println("Exit Menu option chosen");
                            break;
                        default:
                            System.out.print("Invalid option - please enter number in range");
                            break;
                    }

                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.print("Invalid option - please enter number in range");
                }
            } while (option != EXIT);

        }


        //sub menu to add passenger
        public void displayAddPassengerMenu(){
            Scanner keyboard = new Scanner(System.in);
                try {
                    System.out.println("Enter passenger details: ");
                    System.out.println("Passenger First Name");
                    String fName = keyboard.nextLine();
                    System.out.println("Passenger First Name");
                    String lName = keyboard.nextLine();
                    String pName = fName+" "+lName;
                    System.out.println("Passenger Email");
                    String pEmail = keyboard.nextLine();
                    System.out.println("Latitude");
                    double pLat = keyboard.nextDouble();
                    System.out.println("Longitude");
                    double pLong = keyboard.nextDouble();
                    System.out.println("Passenger Phone");
                    String pPhone = keyboard.next();
                    passengerStore.addPassenger(pName, pEmail, pPhone, pLat, pLong);
                } catch (InputMismatchException e) {
                    System.out.println("Invalid Input - Try Again");
                    System.out.println();
                }

        }


        //Vehicle Sub menu
        public void displayVehicleMenu(){

            final String MENU_ITEMS = "\n*** VEHICLE MENU ***\n"
                    + "1. Show all Vehicles\n"
                    + "2. Filter Vehicle by\n"
                    + "3. Add Vehicle\n"
                    + "4. Sort by Registration Number\n"
                    + "5. Delete Vehicle\n"
                    + "6. Exit\n"
                    + "Enter Option [1,8]";

            final int SHOW_ALL = 1;
            final int FILTER = 2;
            final int ADD_VEHICLE = 3;
            final int SORT_BY_REG_NUM = 4;
            final int DELETE_VEHICLE = 5;
            final int EXIT = 6;

            Scanner keyboard = new Scanner(System.in);
            Scanner kb= new Scanner(System.in);
            int option = 0;
            do {
                System.out.println("\n" + MENU_ITEMS);
                try {
                    String usersInput = kb.nextLine();
                    option = Integer.parseInt(usersInput);
                    switch (option) {
                        case SHOW_ALL:
                            System.out.println("Display ALL Vehicles");
                            vehicleManager.displayAllVehicles();
                            break;
                        case FILTER:
                            vehicleFilterSubMenu();
                            break;
                        case ADD_VEHICLE:
                            displayAddVehicleMenu();
                            break;
                        case SORT_BY_REG_NUM:
                            System.out.println("--Sorted By Registration Number--");
                            ArrayList<Vehicle> vehicleSortList = vehicleManager.sortByRegNum();
                            for(Vehicle vs : vehicleSortList) {
                                System.out.println(vs.toString());
                            }
                            break;
                        case DELETE_VEHICLE:
                            System.out.println("Enter Vehicle Id to be deleted");
                            try{
                            int vId = keyboard.nextInt();
                            vehicleManager.deleteVehicleById(vId);
                            }catch (InputMismatchException e){
                                System.out.println("Invalid - Id is not a Number");
                            }
                            break;
                        case EXIT:
                            System.out.println("Exit Menu option chosen");
                            break;
                        default:
                            System.out.print("Invalid option - please enter number in range");
                            break;
                    }

                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.print("Invalid option - please enter number in range");
                }
            } while (option != EXIT);
        }

        //filtering options
    public void vehicleFilterSubMenu(){
        Scanner kb = new Scanner(System.in);
        final String MENU_ITEMS = "\n*** VEHICLE FILTER MENU ***\n"
                + "1. Number Of Seats\n"
                + "2. Type\n"
                + "3. Load Space\n"
                + "4. Registration Number\n"
                + "5. Exit \n"
                + "Enter Option [1,5]";

        final int NUM_SEATS = 1;
        final int TYPE = 2;
        final int LOAD_SPACE = 3;
        final int REG_NUM = 4;
        final int EXIT = 5;
        Scanner keyB =new Scanner(System.in);
        int option = 0;
        do {
            System.out.println("\n" + MENU_ITEMS);
            try {
                String usersInput = keyB .nextLine();
                option = Integer.parseInt(usersInput);
                switch (option) {
                    case NUM_SEATS:
                        try{
                            System.out.println("Find Vehicle by Num Seats");
                            System.out.println("Enter Vehicle Number of Seats: ");
                            int numSeats = kb.nextInt();
                            System.out.println("--Filter by Seats--");
                            List<Vehicle> seatList = vehicleManager.filterBy(new NumberOfSeatFilter(numSeats));
                            if(seatList.size()==0){
                                System.out.println("No vehicles found");
                            }else{
                            for(Vehicle st:seatList) {
                                System.out.println(st);
                            }}
                        }catch (InputMismatchException e){
                            System.out.println("Invalid Seat Number");
                        }
                        break;
                    case TYPE:
                        System.out.println("Find Vehicle by Type");
                        System.out.println("Enter Vehicle Type: ");
                        String type = kb.nextLine();
                        System.out.println("--Filter by Type--");
                        List<Vehicle> typeList = vehicleManager.filterBy(new VehicleTypeFilter(type));
                        if(typeList.size()==0){
                            System.out.println("No vehicles found");
                        }else{
                        for(Vehicle vt:typeList) {
                            System.out.println(vt);
                        }}
                        break;
                    case LOAD_SPACE:
                        try{
                        System.out.println("Find Vehicle by Load Space");
                        System.out.println("Enter Vehicle Load Space: ");
                        double loadSpace = kb.nextDouble();
                        System.out.println("--Filter by Load Space--");
                        List<Vehicle> loadList = vehicleManager.filterBy(new LoadSpaceFilter(loadSpace));
                        if(loadList.size()==0){
                            System.out.println("No vehicles found");
                        }else{
                        for(Vehicle lt:loadList) {
                            System.out.println(lt);
                        }}
                        }catch (InputMismatchException e){
                            System.out.println("Invalid Load Space");
                        }
                        break;
                    case REG_NUM:
                        System.out.println("Find Vehicle by Registration Number");
                        System.out.println("Enter Vehicle Registration Number: ");
                        String regNum = kb.nextLine();
                        System.out.println("--Filter by Registration Number--");
                        List<Vehicle> regList = vehicleManager.filterBy(new RegistrationNumberFilter(regNum));
                        if(regList.size()==0){
                            System.out.println("No vehicles found");
                        }else{
                        for(Vehicle rg:regList) {
                            System.out.println(rg);
                        }}
                        break;
                    case EXIT:
                        System.out.println("Exit Menu option chosen");
                        break;
                    default:
                        System.out.print("Invalid option");
                        break;
                }

            } catch (InputMismatchException | NumberFormatException e) {
                System.out.print("Invalid input");
            }

        } while (option != EXIT);
    }

        //Vehicle sub menu for add vehicle
    public void displayAddVehicleMenu(){
        Scanner keyboard = new Scanner(System.in);
            try {
                System.out.println("Enter vehicle details");
                System.out.println("Enter vehicle type");
                String vType = keyboard.nextLine();
                if(vType.equalsIgnoreCase("van")||vType.equalsIgnoreCase("truck")||vType.equalsIgnoreCase("car")||vType.equalsIgnoreCase("4*4")||vType.equalsIgnoreCase("four by four")) {
                    System.out.println("Enter make");
                    String vMake = keyboard.nextLine();
                    System.out.println("Enter model");
                    String vModel = keyboard.nextLine();
                    System.out.println("Enter milesPerKwH");
                    double milesPerKwH = keyboard.nextDouble();
                    System.out.println("Enter registration number");
                    String vReg = keyboard.next();
                    System.out.println("Enter cost per mile");
                    double costPerMile = keyboard.nextDouble();
                    System.out.println("Enter last serviced Year");
                    int yr = keyboard.nextInt();
                    System.out.println("Enter last serviced Month");
                    int month = keyboard.nextInt();
                    System.out.println("Enter last serviced Day");
                    int day = keyboard.nextInt();
                    System.out.println("Latitude");
                    double vLat = keyboard.nextDouble();
                    System.out.println("Longitude");
                    double vLong = keyboard.nextDouble();
                    System.out.println("Enter mileage");
                    int mileage = keyboard.nextInt();
                    vehicleManager.addVehicle(vType, vMake, vModel, milesPerKwH, vReg, costPerMile, yr, month, day, mileage, vLat, vLong);
                }
                else{
                    System.out.println("Invalid Vehicle Type - Cannot be added");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input - Try Again");
                System.out.println();
            }catch (DateTimeException e){
                System.out.println("Invalid Date Entered - Try Again");
                System.out.println();
            }
    }

    //Booking Sub Menu
    public void displayBookingMenu(){
        Scanner kb = new Scanner(System.in);
        final String MENU_ITEMS = "\n*** BOOKING MENU ***\n"
                + "1. Show all bookings\n"
                + "2. Add Booking\n"
                + "3. Sort by Date Time\n"
                + "4. Sort by Cost\n"
                + "5. Edit Booking\n"
                + "6. Cancel Booking\n"
                + "7. view Future Bookings - Sorted by DateTime\n"
                + "8. view Bookings for a Passenger - Sorted by DateTime\n"
                + "9. Exit\n"
                + "Enter Option [1,9]";

        final int SHOW_ALL = 1;
        final int ADD_BOOKING = 2;
        final int SORT_DATE = 3;
        final int SORT_COST = 4;
        final int EDIT_BOOKING = 5;
        final int CANCEL_BOOKING = 6;
        final int FUTURE_BOOKING = 7;
        final int PASSENGER_BOOK = 8;
        final int EXIT = 9;

        Scanner keyboard = new Scanner(System.in);
        int option = 0;
        do {
            System.out.println("\n" + MENU_ITEMS);
            try {
                String usersInput = keyboard.nextLine();
                option = Integer.parseInt(usersInput);
                switch (option) {
                    case SHOW_ALL:
                        System.out.println("Display ALL bookings");
                        bookingManager.displayAllBookings();
                        break;
                    case ADD_BOOKING:
                        displayAddBookingMenu();
                        break;
                    case SORT_DATE:
                        System.out.println("--Bookings Ordered By Date--");
                        ArrayList<Booking> bookingSortDate = bookingManager.sortByDateTime();
                        for(Booking bs : bookingSortDate) {
                            System.out.println(bs.toString());
                        }
                        break;
                    case SORT_COST:
                        System.out.println("--Bookings Ordered By Cost--");
                        ArrayList<Booking> bookingSort = bookingManager.sortByCost();
                        for(Booking bs : bookingSort) {
                            System.out.println(bs.toString());
                        }
                        break;
                    case EDIT_BOOKING:
                        try{
                        bookingManager.editBooking();
                        }catch (InputMismatchException e){
                            System.out.println("Input invalid");
                        }
                        break;
                    case CANCEL_BOOKING:
                        System.out.println("Enter Booking ID to be deleted");
                        try {
                            int bId = kb.nextInt();
                            bookingManager.deleteBookingById(bId);
                        }catch(InputMismatchException e){
                            System.out.println("Invalid - Id is not a number");
                        }
                        break;
                    case FUTURE_BOOKING:
                        ArrayList<Booking> futureBook = bookingManager.displayFutureBookings();
                        for(Booking b: futureBook){
                            System.out.println(b);
                        }
                        break;
                    case PASSENGER_BOOK:
                        System.out.println("--BOOKING BY PASSENGER");
                        System.out.println("Enter Passenger Id");
                        int passId = kb.nextInt();
                        ArrayList<Booking> bookpass = bookingManager.displayBookingByPassenger(passId);
                        for(Booking b: bookpass){
                            System.out.println(b);
                        }
                        break;
                    case EXIT:
                        System.out.println("Exit Menu option chosen");
                        break;
                    default:
                        System.out.print("Invalid option - please enter number in range");
                        break;
                }

            } catch (InputMismatchException | NumberFormatException | IOException e) {
                System.out.print("Invalid option - please enter number in range");
            }
        } while (option != EXIT);

    }
    public void displayAddBookingMenu(){
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter passenger Id");
        int passengerId = kb.nextInt();
        System.out.println("Enter vehicle Id");
        int vehicleId = kb.nextInt();
        System.out.println("Enter Year");
        int year = kb.nextInt();
        System.out.println("Enter Month");
        int month = kb.nextInt();
        System.out.println("Enter Day");
        int day = kb.nextInt();
        System.out.println("Enter Hour");
        int hour= kb.nextInt();
        System.out.println("Enter Minutes");
        int min= kb.nextInt();
        System.out.println("Enter Seconds");
        int sec= kb.nextInt();
        System.out.println("Enter Start Latitude");
        double startLatitude = kb.nextDouble();
        System.out.println("Enter End Latitude");
        double endLatitude= kb.nextDouble();
        System.out.println("Enter Start Longitude");
        double startLongitude= kb.nextDouble();
        System.out.println("Enter End Longitude");
        double endLongitude= kb.nextDouble();

        bookingManager.addBooking(passengerId,vehicleId,year,month,day,hour,min,sec,startLatitude,endLatitude,startLongitude,endLongitude);

    }

}
