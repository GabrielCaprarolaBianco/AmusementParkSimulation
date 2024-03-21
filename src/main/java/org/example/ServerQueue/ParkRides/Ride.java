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
    //main method that drives the ride object
    //checks to see if the current time has reached or exceeded the required time for an action to take place
    public void action(double currentTime, WanderingQueue queueReference, Queue attractionQueue){
        while(currentTime >= nextEndOfService){
            offLoad(queueReference);
            if(!attractionQueue.isQueueEmpty()){
            onLoad(attractionQueue,rideName, currentTime);
            setNextEndOfService();
            }
            else
                break;
        }
    }

    protected void offLoad(WanderingQueue queueReference){ //pushes all jobs from ride into wandering queue
        int arrayIndex = 0;
        while(arrayIndex < rideCapacity && riders[arrayIndex] != null){
            queueReference.enqueue(riders[arrayIndex]);
            arrayIndex++;
        }
        riders = new Job[rideCapacity]; //used to clean out all stale Job references from riders array
    }
    protected void onLoad(Queue attractionQueue, String rideName, double currentTime){ //pulls job from attraction queue for service
        int ridersIndex = 0;
        while(riders[rideCapacity-1] == null && !attractionQueue.isQueueEmpty()){
            riders[ridersIndex] = attractionQueue.dequeue();
            if(currentTime > nextEndOfService){
                riders[ridersIndex].completeTimeStamp(currentTime,rideName);
            }
            else {
                riders[ridersIndex].completeTimeStamp(nextEndOfService, rideName);
            }
            riders[ridersIndex].reduceNumberOfRides();
            ridersIndex++;
        }
    }

    private void setNextEndOfService(){
        nextEndOfService = nextEndOfService + rideLength;
    }
    public double getRideCapacity(){return rideCapacity;}
    public double getRideLength(){return rideLength;}
}
