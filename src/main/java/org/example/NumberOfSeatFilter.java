package org.example;

public class NumberOfSeatFilter implements IFilter {

    private int seats;

    public NumberOfSeatFilter(int seats) {
        this.seats = seats;
    }

    @Override
    public boolean matches(Object o) {
        Vehicle v = (Vehicle) o;
        if(v instanceof Car){
            return (((Car) v).getNumberOfSeats()==seats)?true:false;
        }
        return false;
    }

}