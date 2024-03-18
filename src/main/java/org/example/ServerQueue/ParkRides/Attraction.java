package org.example.ServerQueue.ParkRides;

import org.example.ServerQueue.QueueCore.Job;
import org.example.ServerQueue.QueueCore.Queue;

public class Attraction {
    private AttractionValues namePopularity;
    protected Queue mainQueue;
    private Ride[] rides;
    private int lineSize;
    public Attraction(String rideName, double popularity, int rideCapacity, double lengthOfRide, int numberOfRides){
        namePopularity = new AttractionValues(rideName,popularity);
        mainQueue = new Queue();
        rides = initializeRides(rideCapacity,lengthOfRide,numberOfRides, rideName);
        lineSize = 0;
    }

    private Ride[] initializeRides(int rideCapacity, double LengthOfRide, int numberOfRides, String rideName){
        Ride[] outputArray = new Ride[numberOfRides];
        for(int i = 0; i < numberOfRides; i++){
            outputArray[i] = new Ride(rideCapacity,LengthOfRide,0, rideName);
        }
        return outputArray;
    }

    public void enqueue(Job job, double currentTime){
        job.addTimeStamp(currentTime, namePopularity.getRideName());
        mainQueue.enqueue(job);
        lineSize++;
    }
    public String getRideName(){
        return namePopularity.getRideName();
    }

}
