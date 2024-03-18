package org.example.ServerQueue.ParkRides;

import org.example.ServerQueue.QueueCore.Job;
import org.example.ServerQueue.QueueCore.Queue;
import org.example.ServerQueue.WanderingQueue;

public class Ride {
    private int rideCapacity;
    private double rideLength;
    private Job[] riders;
    private double nextEndOfService;
    private String rideName;

    protected Ride(int rideCapacity,double rideLength, double currentTime, String rideName){
        this.rideCapacity = rideCapacity;
        this.rideLength = rideLength;
        riders = new Job[rideCapacity];
        nextEndOfService = currentTime;
        this.rideName = rideName;
    }

    public void rideAction(double currentTime, WanderingQueue queueReference, String rideName, Queue attractionQueue){
        while(currentTime >= nextEndOfService){
            offLoad(queueReference, rideName);
            onLoad(attractionQueue,nextEndOfService,rideName);
            setNextEndOfService(nextEndOfService);
        }
    }

    protected void offLoad(WanderingQueue queueReference, String rideName){
        int arrayIndex = 0;
        while(riders[arrayIndex] != null && arrayIndex < rideCapacity){
            queueReference.enqueue(riders[arrayIndex]);
            arrayIndex++;
        }
        riders = new Job[rideCapacity]; //used to clean out all stale Job references from riders array
    }
    protected void onLoad(Queue attractionQueue, double currentTime, String rideName){
        int ridersIndex = 0;
        while(riders[rideCapacity-1] == null && !attractionQueue.isQueueEmpty()){
            riders[ridersIndex] = attractionQueue.dequeue();
            riders[ridersIndex].completeTimeStamp(currentTime,rideName);
        }
    }

    private void setNextEndOfService(double currentTime){
        nextEndOfService = nextEndOfService + rideLength;
    }
    private double getNextEndOfService(){return nextEndOfService;}

}
