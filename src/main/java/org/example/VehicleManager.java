package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class VehicleManager {
    private final ArrayList<Vehicle> vehicleList;  // for Car and Van objects

    public ArrayList<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public VehicleManager(String fileName) {
        this.vehicleList = new ArrayList<>();
        loadVehiclesFromFile(fileName);
    }

    public void displayAllVehicles() {
        for (Vehicle v : vehicleList)
            System.out.println(v.toString());
    }

    public void loadVehiclesFromFile(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
//           Delimiter: set the delimiter to be a comma character ","
//                    or a carriage-return '\r', or a newline '\n'
            sc.useDelimiter("[,\r\n]+");

            while (sc.hasNext()) {
                int id = sc.nextInt();
                String type = sc.next();  // vehicle type
                String make = sc.next();
                String model = sc.next();
                double milesPerKwH = sc.nextDouble();
                String registration = sc.next();
                double costPerMile = sc.nextDouble();
                int year = sc.nextInt();   // last service date
                int month = sc.nextInt();
                int day = sc.nextInt();
                int mileage = sc.nextInt();
                double latitude = sc.nextDouble();  // Depot GPS location
                double longitude = sc.nextDouble();


                if (type.equalsIgnoreCase("Van") ||
                        type.equalsIgnoreCase("Truck")) {
                    double loadSpace = sc.nextDouble();
                    // construct a Van object and add it to the Vehicle list
                    vehicleList.add(new Van(id, type, make, model, milesPerKwH,
                            registration, costPerMile,
                            year, month, day,
                            mileage, latitude, longitude,
                            loadSpace));
                }

                else if (type.equalsIgnoreCase("Car") ||
                        type.equalsIgnoreCase("4*4")) {
                    int numSeats = sc.nextInt();
                    // construct a Car object and add it to the Vehicle list
                    vehicleList.add(new Car(id, type, make, model, milesPerKwH,
                            registration, costPerMile,
                            year, month, day,
                            mileage, latitude, longitude,
                            numSeats));
                }

            }
sc.close();

        } catch (IOException e) {
            System.out.println("Exception thrown. " + e);
        }
    }

    //TODO add more functionality as per spec.

    public Vehicle findByRegistrationNumber(String reg){
        for(Vehicle v: vehicleList) {
            if (reg.equals(v.getRegistration())){
                return v;
            }
        }
        return null;
    }
    public ArrayList<Vehicle> findByType(String type){
        ArrayList<Vehicle> vehicleTypeList = new ArrayList<>();
        for(Vehicle v: vehicleList) {
            if (type.equalsIgnoreCase(v.getType())){
                vehicleTypeList.add(v);
            }

        }
        if(vehicleTypeList.size()!=0){
            System.out.println("Found Vehicle/s");
        }
        else{
            System.out.println("Vehicle of type "+type+" Not found");
        }
        return vehicleTypeList;
    }

    public Vehicle findByVehicleId(int id){
        for(Vehicle v: vehicleList) {
            if (id==v.getId()){
                return v;
            }
        }
        return null;
    }

    public List<Vehicle> filterBy(IFilter filter)            // I stands for Interface
    {
        List<Vehicle> filteredList = new ArrayList<>();
        for (Vehicle v : this.vehicleList) {
            if (filter.matches(v))    // use matches() method of the filter to match products
                filteredList.add(v);
        }

        return filteredList;
    }

    public ArrayList<Vehicle> sortByRegNum() {
        ArrayList<Vehicle> vehicleSortList = new ArrayList<>();
        vehicleSortList.addAll(vehicleList);
        Collections.sort(vehicleSortList);
        return vehicleSortList;
    }



    public void addVehicle(String type, String make, String model, double milesPerKwH,
                           String registration, double costPerMile,
                           int year, int month, int day,
                           int mileage, double latitude, double longitude) {
        IdGenerator idGenerator = IdGenerator.getInstance("next-id-store.txt");
        int id = idGenerator.getNextId();
        Scanner kb = new Scanner(System.in);
        if(type.equalsIgnoreCase("Van")||type.equalsIgnoreCase("Truck")) {
            System.out.println("Enter Load Space");
            double loadSpace = kb.nextDouble();
            Van van = new Van(id, type, make, model, milesPerKwH, registration, costPerMile, year, month, day, mileage, latitude, longitude, loadSpace);

            boolean found = false;
            for(Vehicle v: this.vehicleList){
                if(van.equals(v)){
                    found = true;
                    System.out.println("Cannot add vehicle - Duplicate exists");
                    break;
                }
            }
            if(found == false){
                vehicleList.add(van);
                System.out.println("Vehicle added");
            }
        }
        if(type.equalsIgnoreCase("Car")||type.equalsIgnoreCase("4*4")||type.equalsIgnoreCase("four by four")) {
            System.out.println("Enter Number of Seats");
            int numSeats = kb.nextInt();
            Car car = new Car(id, type, make, model, milesPerKwH, registration, costPerMile, year, month, day, mileage, latitude, longitude, numSeats);

            boolean found = false;
            for(Vehicle c: this.vehicleList){
                if(car.equals(c)){
                    found = true;
                    System.out.println("Cannot add vehicle - Duplicate exists");
                    break;
                }
            }
            if(found == false){
                vehicleList.add(car);
                System.out.println("Vehicle added");
            }
        }


    }

    public void deleteVehicleById(int id){
        boolean found = false;
        for(Vehicle v : vehicleList) {
            if (id == v.getId()) {
                vehicleList.remove(v);
                System.out.println("Vehicle Deleted");
                found = true;
                break;
            }
        }
        if(found==false){
            System.out.println("Cannot be Deleted");
        }
    }


    public void storeToFileVehicle() throws IOException {

        FileWriter fWriter = new FileWriter("vehicles.txt");
        String output = "";

            for(Vehicle v : vehicleList) {

                int id = v.getId();
                String type = v.getType();  // vehicle type
                String make = v.getMake();
                String model = v.getModel();
                double milesPerKwH = v.getMilesPerKm();
                String registration = v.getRegistration();
                double costPerMile = v.getCostPerMile();
                int year = v.getLastServicedDate().getYear();
                int month = v.getLastServicedDate().getMonthValue();
                int day = v.getLastServicedDate().getDayOfMonth();
                int mileage =v.getMileage();
                double latitude = v.getDepotGPSLocation().getLatitude();
                double longitude = v.getDepotGPSLocation().getLongitude();
                output = id+","+type+"," +make+"," +model+"," +milesPerKwH+"," +registration+"," +costPerMile+"," +year+"," +month+"," +day+"," +mileage+"," +latitude+"," +longitude;

                 if(v instanceof Car){
                  output+="," +((Car) v).getNumberOfSeats();
                 }
                 if(v instanceof Van){
                     output+="," +((Van) v).getLoadSpace();
                 }
                fWriter.append(output+"\n");
            }
            fWriter.close();

            System.out.println("Changes applied to the vehicle" +
                    " File");
        }

}
