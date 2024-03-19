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

    public void action(double currentTime, WanderingQueue queueReference, Queue attractionQueue){
        while(currentTime >= nextEndOfService){
            offLoad(queueReference);
            if(!attractionQueue.isQueueEmpty()){
            onLoad(attractionQueue,rideName);
            setNextEndOfService();
            }
            else
                break;
        }
    }

    protected void offLoad(WanderingQueue queueReference){
        int arrayIndex = 0;
        while(riders[arrayIndex] != null && arrayIndex < rideCapacity){
            queueReference.enqueue(riders[arrayIndex]);
            arrayIndex++;
        }
        riders = new Job[rideCapacity]; //used to clean out all stale Job references from riders array
    }
    protected void onLoad(Queue attractionQueue, String rideName){
        int ridersIndex = 0;
        while(riders[rideCapacity-1] == null && !attractionQueue.isQueueEmpty()){
            riders[ridersIndex] = attractionQueue.dequeue();
            riders[ridersIndex].completeTimeStamp(nextEndOfService,rideName);
            riders[ridersIndex].reduceNumberOfRides();
        }
    }

    private void setNextEndOfService(){
        nextEndOfService = nextEndOfService + rideLength;
    }
    public double getRideCapacity(){return rideCapacity;}
    public double getRideLength(){return rideLength;}
}
