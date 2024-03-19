package org.example.ServerQueue.ParkRides;

import org.example.ServerQueue.QueueCore.Job;
import org.example.ServerQueue.QueueCore.Queue;
import org.example.ServerQueue.WanderingQueue;

public class Attraction {
    private AttractionValues namePopularity;
    protected Queue mainQueue;
    private Ride[] rides;
    private int lineSize;
    private WanderingQueue wanderingQueueReference; //has to be made later due to issues with wandering queue needing attractions to be initalized too
    public Attraction(String rideName, double popularity, int rideCapacity, double lengthOfRide, int numberOfRides){
        namePopularity = new AttractionValues(rideName,popularity);
        mainQueue = new Queue();
        rides = initializeRides(rideCapacity,lengthOfRide,numberOfRides, rideName);
        lineSize = 0;
        wanderingQueueReference = null;
    }

    private Ride[] initializeRides(int rideCapacity, double LengthOfRide, int numberOfRides, String rideName){
        Ride[] outputArray = new Ride[numberOfRides];
        for(int i = 0; i < numberOfRides; i++){
            outputArray[i] = new Ride(rideCapacity,LengthOfRide,0, rideName);
        }
        return outputArray;
    }

    public void action(double currentTime){
        for(Ride ride : rides){
            ride.action(currentTime, wanderingQueueReference, mainQueue);
        }
    }

    public double waitTimeCalculation(){
        return Math.ceil(((lineSize/rides[0].getRideCapacity())*(rides[0].getRideLength()/ rides.length)));
    }

    public void enqueue(Job job, double currentTime){
        job.addTimeStamp(currentTime, namePopularity.getRideName());
        mainQueue.enqueue(job);
        lineSize++;
    }
    public String getRideName(){
        return namePopularity.getRideName();
    }
    public AttractionValues getNamePopularity(){return namePopularity;}
    public void setWanderingQueueReference(WanderingQueue wanderingQueueReference){this.wanderingQueueReference = wanderingQueueReference;}
}
