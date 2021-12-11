package org.example;


public class RegistrationNumberFilter implements IFilter {
    private String regNum;

    public RegistrationNumberFilter(String regNum) {
        this.regNum = regNum;
    }

    @Override
    public boolean matches(Object other) {
        Vehicle v= (Vehicle) other;        // cast from Object to Vehicle
        return v.getRegistration().equalsIgnoreCase(regNum);
    }
}
