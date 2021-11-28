package org.example;

import javax.security.auth.kerberos.KerberosKey;
import java.io.IOException;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AppMenu {
        // Components (objects) used in this App
        PassengerStore passengerStore;  // encapsulates access to list of Passengers
        VehicleManager vehicleManager;  // encapsulates access to list of Vehicles
        BookingManager bookingManager;  // deals with all bookings

        public static void main(String[] args) {
            AppMenu app = new AppMenu();
            app.start();
        }

        public void start() {
            // create PassengerStore and load all passenger records from text file
            passengerStore = new PassengerStore("passengers.txt");

            // create VehicleManager, and load all vehicles from text file
            vehicleManager = new VehicleManager("vehicles.txt");

            try {
                displayMainMenu();        // User Interface - Menu
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

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
                            break;
                        case EXIT:
                            System.out.println("Exit Menu option chosen");
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
        //
        private void displayPassengerMenu() {
            final String MENU_ITEMS = "\n*** PASSENGER MENU ***\n"
                    + "1. Show all Passengers\n"
                    + "2. Find Passenger by Name\n"
                    + "3. Add Passenger\n"
                    + "4. Sort Passenger by Name\n"
                    + "5. Exit\n"
                    + "Enter Option [1,4]";

            final int SHOW_ALL = 1;
            final int FIND_BY_NAME = 2;
            final int ADD_PASSENGER = 3;
            final int SORT_NAME = 4;
            final int EXIT = 5;

            Scanner keyboard = new Scanner(System.in);
            int option = 0;
            do {
                System.out.println("\n" + MENU_ITEMS);
                try {
                    String usersInput = keyboard.nextLine();
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
                            Passenger p = passengerStore.findPassengerByName(name);
                            if (p == null)
                                System.out.println("No passenger matching the name \"" + name + "\"");
                            else
                                System.out.println("Found passenger: \n" + p.toString());
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
        public void displayAddPassengerMenu(){
            Scanner keyboard = new Scanner(System.in);
                try {
                    System.out.println("Enter passenger details: ");
                    System.out.println("Passenger Name");
                    String pName = keyboard.nextLine();
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

        public void displayVehicleMenu(){
            final String MENU_ITEMS = "\n*** VEHICLE MENU ***\n"
                    + "1. Show all Vehicles\n"
                    + "2. Find by Registration Number\n"
                    + "3. Display Vehicle by Type\n"
                    + "4. Add Vehicle\n"
                    + "5. Sort by Registration Number\n"
                    + "6. Delete Vehicle by - Registration Number\n"
                    + "7. Exit\n"
                    + "Enter Option [1,3]";

            final int SHOW_ALL = 1;
            final int FIND_BY_REGNUM = 2;
            final int FIND_BY_TYPE = 3;
            final int ADD_VEHICLE = 4;
            final int SORT_BY_REG_NUM = 5;
            final int DELETE_VEHICLE = 6;
            final int EXIT = 7;

            Scanner keyboard = new Scanner(System.in);
            int option = 0;
            do {
                System.out.println("\n" + MENU_ITEMS);
                try {
                    String usersInput = keyboard.nextLine();
                    option = Integer.parseInt(usersInput);
                    switch (option) {
                        case SHOW_ALL:
                            System.out.println("Display ALL Vehicles");
                            vehicleManager.displayAllVehicles();
                            break;
                        case FIND_BY_REGNUM:
                            System.out.println("Find Vehicle by registration number");
                            System.out.println("Enter Vehicle registration number: ");
                            String regNum = keyboard.nextLine();
                            Vehicle v = vehicleManager.findByRegistrationNumber(regNum);
                            if (v == null)
                                System.out.println("No vehicle matching the number \"" + regNum + "\"");
                            else
                                System.out.println("Found Vehicle: \n" + v.toString());
                            break;
                        case FIND_BY_TYPE:
                            System.out.println("Find Vehicle by Type");
                            System.out.println("Enter Vehicle Type: ");
                            String type = keyboard.nextLine();
                            ArrayList<Vehicle> vehicleTypeList =vehicleManager.findByType(type);
                            for(Vehicle vType: vehicleTypeList){
                                System.out.println(vType.toString());
                            }
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
                            displayDeleteMenu();
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
    public void displayDeleteMenu(){
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter ID of Vehicle to be Deleted");
        try {
            int id = kb.nextInt();
            vehicleManager.deleteVehicleById(id);
        }catch (InputMismatchException e){
            System.out.println("Invalid ID");
        }

    }
}
