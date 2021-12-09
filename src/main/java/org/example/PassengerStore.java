package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DateTimeException;
import java.util.*;

public class PassengerStore {

    private final ArrayList<Passenger> passengerList;

    public PassengerStore(String fileName) {
        this.passengerList = new ArrayList<>();
        loadPassengerDataFromFile(fileName);
    }

    public ArrayList<Passenger> getAllPassengers() {
        return this.passengerList;
    }

    public void displayAllPassengers() {
        for (Passenger p : this.passengerList) {
            System.out.println(p.toString());
        }
    }


    /**
     * Read Passenger records from a text file and create and add Passenger
     * objects to the PassengerStore.
     */
    private void loadPassengerDataFromFile(String filename) {

        try {
            Scanner sc = new Scanner(new File(filename));
//           Delimiter: set the delimiter to be a comma character ","
//                    or a carriage-return '\r', or a newline '\n'
            sc.useDelimiter("[,\r\n]+");

            while (sc.hasNext()) {
                int id = sc.nextInt();
                String name = sc.next();
                String email = sc.next();
                String phone = sc.next();
                double latitude = sc.nextDouble();
                double longitude = sc.nextDouble();

                // construct a Passenger object and add it to the passenger list
                passengerList.add(new Passenger(id, name, email, phone, latitude, longitude));
            }
            sc.close();

        } catch (IOException e) {
            System.out.println("Exception thrown. " + e);
        }
    }

    // TODO - see functional spec for details of code to add

    public ArrayList<Passenger> findPassengerByName(String name){
        ArrayList<Passenger> passengerName = new ArrayList<>();
        for(Passenger p : passengerList){
            String fName = p.getName().substring(0,p.getName().indexOf(" "));
            String lName = p.getName().substring(p.getName().indexOf(" ")+1,p.getName().length());
            if(p.getName().equalsIgnoreCase(name)||fName.equalsIgnoreCase(name)||lName.equalsIgnoreCase(name)){
                passengerName.add(p);
            }
        }
        return passengerName;
    }
    public void addPassenger(String name, String email, String phone, double latitude, double longitude){
        IdGenerator idGenerator = IdGenerator.getInstance("next-id-store.txt");
        int id = idGenerator.getNextId();
        Passenger newPassenger = new Passenger(id,name, email, phone,latitude, longitude);
        boolean found = false;
        for(Passenger p: this.passengerList){
            if(newPassenger.equals(p)){
                found = true;
                System.out.println("Cannot add passenger - Duplicate exists");
                break;
            }
        }
        if(found == false){
            passengerList.add(newPassenger);
            System.out.println("Passenger added");
        }
    }
    public Passenger findPassengerById(int id){
        for(Passenger p: passengerList) {
            if (id==p.getId()){
                return p;
            }
        }
        return null;
    }

    public ArrayList<Passenger> sortByName(){
        ArrayList<Passenger> passengerSort = new ArrayList<>();
        passengerSort.addAll(passengerList);
        Collections.sort(passengerSort);
        return passengerSort;
    }


    public void storeToFilePassenger() throws IOException {

        FileWriter fWriter = new FileWriter("passengers.txt");
        String output = "";

        for(Passenger p : passengerList) {

            int id = p.getId();
            String name = p.getName();
            String email = p.getEmail();
            String phone =p.getPhone();
            double latitude = p.getLocation().getLatitude();
            double longitude =p.getLocation().getLongitude();

            output = id+","+name+"," +email+"," +phone+"," +latitude+"," +longitude;

            fWriter.append(output+"\n");
        }
        fWriter.close();

        System.out.println("Changes applied to the passenger File");
    }
    public void editPassenger(){
        System.out.println("Enter your passenger Id");
        Scanner kb = new Scanner(System.in);
        int passengerId = kb.nextInt();
        Passenger p = findPassengerById(passengerId);
        if(p!=null){
            System.out.println("Passenger Before Edit");
            System.out.println(p);
            final String MENU_ITEMS = "\n*** EDIT OPTIONS ***\n"
                    + "1. Passenger Name\n"     //what if a passenger is married?
                    + "2. Passenger Email\n"    //want to enter some other family member's email
                    + "3. Passenger Phone\n"
                    + "4. Passenger Location\n"
                    + "5. Exit\n"
                    + "Enter Option [1,5]";


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
                            System.out.println("Passenger First Name");
                            String FirstName = kb.next();
                            System.out.println("Passenger Last Name");
                            String LastName = kb.next();
                            String newName = FirstName+" "+LastName;
                            p.setName(newName);
                            System.out.println("Changes applied");
                            break;
                        case 2:
                            System.out.println("Enter passenger new Email");
                            String newEmail = kb.next();
                            p.setEmail(newEmail);
                            System.out.println("Changes applied");
                            break;
                        case 3:
                            System.out.println("Enter passenger new Phone Number");
                            String newPhone = kb.next();
                            if(newPhone.length()>=10&&newPhone.length()<=15){
                            p.setPhone(newPhone);
                            System.out.println("Changes applied");
                            }
                            else{
                                System.out.println("Invalid Phone");
                            }
                            break;
                        case 4:
                            System.out.println("New Location");
                            System.out.println("Enter latitude");
                            double plat = kb.nextDouble();
                            System.out.println("Enter longitude");
                            double plong = kb.nextDouble();
                            p.setLocation(plat,plong);
                            System.out.println("Changes applied");
                            break;
                        case 5:
                            System.out.println("Exiting Edit Menu");
                            break;
                        default:
                            System.out.print("Invalid option - please enter number in range");
                            break;
                    }

                } catch (InputMismatchException |NumberFormatException e)
                {
                    System.out.print("Invalid Number");
                }
            } while (option != 5);
        }
        else{
            System.out.println("Passenger "+passengerId+" doesn't exist");
        }
    }

    public void deletePassenger(int id){
        boolean found = false;
        for(Passenger p : passengerList) {
            if (id == p.getId()) {
                passengerList.remove(p);
                System.out.println("Vehicle Deleted");
                found = true;
                break;
            }
        }
        if(found==false){
            System.out.println("Cannot be Deleted");
        }
    }

} // end class
