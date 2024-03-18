package org.example.ServerQueue.ParkRides;

public class AttractionValues {
    //this class is for bundling highly used values from attractions into a single object
    private String rideName;
    private double popularity;

    protected AttractionValues(String rideName, double popularity){
        this.rideName = rideName;
        this.popularity = popularity;
    }
    public String getRideName() {return rideName;}
    public double getPopularity() {return popularity;}
}
