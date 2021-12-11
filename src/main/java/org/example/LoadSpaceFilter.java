package org.example;

public class LoadSpaceFilter implements IFilter {

    private double loadSpace;

    public LoadSpaceFilter(double loadSpace) {
        this.loadSpace = loadSpace;
    }

    @Override
    public boolean matches(Object o) {
        Vehicle v = (Vehicle) o;
        if(v instanceof Van){
            return (((Van) v).getLoadSpace()==loadSpace)?true:false;
        }
        return false;
    }

}