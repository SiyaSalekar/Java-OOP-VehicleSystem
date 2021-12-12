package org.example;

import java.util.Comparator;

public class VehicleRegistrationNumberComparator implements Comparator<Vehicle> {
    @Override
    public int compare(Vehicle p1, Vehicle p2) {
        return p1.getRegistration().compareTo(p2.getRegistration());
    }
}
