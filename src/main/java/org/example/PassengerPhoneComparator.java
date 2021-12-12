package org.example;

import java.util.Comparator;

public class PassengerPhoneComparator implements Comparator<Passenger> {
    @Override
    public int compare(Passenger p1, Passenger p2) {
        return p1.getPhone().compareTo(p2.getPhone());
    }
}