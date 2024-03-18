package org.example.ServerQueue.QueueCore;

import org.example.ClockSystem.TimeStamp;
import org.example.ServerQueue.ParkRides.AttractionValues;

import java.util.Random;
import java.util.UUID;

public class Job {
    //Made it UUID instead of Integer, so I won't have to worry about repeating ID values;
    private UUID jobID;
    //TimeStamp array is set to 10 as a default since 10 should be more than enough for any single job
    private TimeStamp[] timeStamp;
    private double flexibility;
    private Random random;
    private RideAffinity[] rideAffinities;

    public Job(AttractionValues[] rideValues){
        jobID = UUID.randomUUID();
        timeStamp = new TimeStamp[10];
        flexibility = random.nextDouble(0,1);
        rideAffinities = setRideAffinities(rideValues);
    }
    public UUID getJobID(){
        return jobID;
    }
    private RideAffinity[] setRideAffinities(AttractionValues[] rides){
        RideAffinity[] outputArray = new RideAffinity[rides.length];
        int arrayIndex = 0;
        for(AttractionValues ride : rides){
            double jobRideAffinity = random.nextDouble(0,1);
            if(ride.getPopularity() >= jobRideAffinity){
                outputArray[arrayIndex] = new RideAffinity(jobRideAffinity, ride.getRideName());
            }
            arrayIndex++;
        }
        return arrayTrim(outputArray);
    }
    public void addTimeStamp(double currentTime, String locationOfWait){ //adds a new timeStamp object to array list
        int TimeStampIndex = 0;
        int lengthOfArray = timeStamp.length - 1;
        if(timeStamp[lengthOfArray] != null){
            timeStamp = arrayExpansion(timeStamp);    //should be occasionally used, but necessary
        }
        while(timeStamp[TimeStampIndex] != null){
            TimeStampIndex++;
        }
        timeStamp[TimeStampIndex] = new TimeStamp(currentTime, locationOfWait);
    }

    public void completeTimeStamp(double currentTime, String locationOfWait){  //invoked when a job moves between queues/services
        int timeStampIndex = 0;
        while(timeStamp[timeStampIndex] != null){
            if(timeStamp[timeStampIndex].getLocationOfWait() == locationOfWait){
                break;
            }
            else
                timeStampIndex++;
        }
        timeStamp[timeStampIndex].setEndTime(currentTime);
    }

    public TimeStamp[] timeStampDump(){ //This method is to trim any extra space from timeStamp before passing forward
        int timeStampIndex = 0;
        while(timeStamp[timeStampIndex] != null){
            timeStampIndex++;
        }
        TimeStamp[] tempArray = new TimeStamp[timeStampIndex];
        for(int i = 0; i < tempArray.length; i++){
            tempArray[i] = timeStamp[i];
        }
        return tempArray;
    }

    private TimeStamp[] arrayExpansion(TimeStamp[] oldArray){  //expands any timeStamp arrays that need more room
        TimeStamp[] newArray = new TimeStamp[oldArray.length + 10];
        for(int i = 0; i < oldArray.length; i++){
            newArray[i] = oldArray[i];
        }
        return newArray;
    }

    private RideAffinity[] arrayTrim(RideAffinity[] inputArray){
        int index = 0;
        while(inputArray[index] != null){
            index++;
        }
        RideAffinity[] outputArray = new RideAffinity[index];
        for(int i =0; i < outputArray.length;i++){
            outputArray[i] = inputArray[i];
        }
        return outputArray;
    }

    @Override
    public String toString(){
            return "Job is named : "+ jobID.toString();
    }

    protected class RideAffinity{
        //this class is used to keep the affinity a particular person has for any given ride
        //holds the name of a ride and a persons affinity for that ride
        private double affinityToRide;
        private String rideName;

        protected RideAffinity(double affinityToRide, String rideName){
            this.affinityToRide = affinityToRide;
            this.rideName = rideName;
        }
        protected double getAffinityToRide(){return affinityToRide;}
        protected String getRideName(){return rideName;}
    }

}
