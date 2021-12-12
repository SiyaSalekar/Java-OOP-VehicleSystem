package org.example;

import java.util.Comparator;

public class BookCostComparator  implements Comparator<Booking> {

    @Override
    public int compare(Booking b1, Booking b2)
    {
        return Double.compare(b1.getCost(),b2.getCost());
    }

}
