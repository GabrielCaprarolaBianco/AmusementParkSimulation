package org.example.ServerQueue.QueueCore;

import org.example.ClockSystem.TimeStamp;
import org.example.ServerQueue.ParkRides.Attraction;
import org.example.ServerQueue.ParkRides.AttractionValues;
import org.example.ServerQueue.ParkRides.Ride;

import java.util.Random;
import java.util.UUID;

public class Job {
    //Made it UUID instead of Integer, so I won't have to worry about repeating ID values;
    private UUID jobID;
    //TimeStamp array is set to 10 as a default since 10 should be more than enough for any single job
    private TimeStamp[] timeStamp;
    private double flexibility;
    private Random random = new Random();
    private RideAffinity[] rideAffinities;
    private int numberOfRides;
    private RideAffinity[] completedRides;

    public Job(AttractionValues[] rideValues){
        jobID = UUID.randomUUID();
        timeStamp = new TimeStamp[10];
        flexibility = random.nextDouble(0,1);
        rideAffinities = setRideAffinities(rideValues);
        numberOfRides = setNumberOfRides();
        completedRides = new RideAffinity[rideAffinities.length];
    }
    public String pathingDecisionEquation(Attraction[] attractionsReference){
        RideAffinity[] potentialAttractions = remainingDestinations();
        if(potentialAttractions.length == 1){
            return potentialAttractions[0].getRideName();
        }
        RideAffinity output = potentialAttractions[0];
        for(int i = 1; i < potentialAttractions.length; i++){
            //All method calls could be put into one equation, but that would be incredibly long and hard to read
            //The main equation within the if statement follows this format
            //Decision = (Values/incentives of ride 1) - (Values/incentives of ride 2)
            //values/incentives = (ride affinity X flexibility or 1 - flexibility(stubbornness) X 1 - (wait time / 90))
            //if a single wait time goes above 90 it will guarantee that ride will not be picked
            //but if both wait times are above 90 the equation will still weigh all values to make a decision since both values will invert
            //This equation is responsible allowing the jobs to choose their own desired destination
            //if the equation produces a positive number the first option is preferred
            //if the equation produces a negative number the second option is preferred
            double waitTime1 = getWaitTime(attractionsReference, output.getRideName());
            double waitTime2 = getWaitTime(attractionsReference, potentialAttractions[i].getRideName());
            double rideAffinity1 = output.getAffinityToRide();
            double rideAffinity2 = potentialAttractions[i].getAffinityToRide();
            if(((rideAffinity1*(1-flexibility)*(1-(waitTime1/90)))-(rideAffinity2*(flexibility)*(1-(waitTime2/90)))) <= 0){
                output = potentialAttractions[i];
            }
        }
        addCompletedRide(output);
        return output.getRideName();
    }

    private RideAffinity[] setRideAffinities(AttractionValues[] rides){
        RideAffinity[] outputArray = new RideAffinity[rides.length];
        int arrayIndex = 0;
        for(AttractionValues ride : rides){
            double jobRideAffinity = random.nextDouble(0,1);
            if(ride.getPopularity() <= jobRideAffinity){
                outputArray[arrayIndex] = new RideAffinity(jobRideAffinity, ride.getRideName());
                arrayIndex++;
            }
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
                break;}
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

    private RideAffinity[] remainingDestinations(){
        RideAffinity[] output = new RideAffinity[rideAffinities.length];
        int arrayIndex = 0;
        for(RideAffinity rides : rideAffinities){
            if(!isPartOfCompletedRides(rides)){
                output[arrayIndex] = rides;
                arrayIndex++;
            }
        }
        return arrayTrim(output);
    }
    private boolean isPartOfCompletedRides(RideAffinity input){
        int arrayIndex = 0;
        while(completedRides[arrayIndex] != null){
            if(input.getRideName() == completedRides[arrayIndex].getRideName())
                return true;
            arrayIndex++;
        }
        return false;
    }
    private void addCompletedRide(RideAffinity input){
        int arrayIndex = 0;
        while(arrayIndex < completedRides.length && completedRides[arrayIndex] != null){
            arrayIndex++;
        }
        completedRides[arrayIndex] = input;
    }
    private int setNumberOfRides(){
        int output = random.nextInt(2,4);
        if(output > rideAffinities.length){
            output = rideAffinities.length;
            return output;
        }
        return output;
    }

    private double getWaitTime(Attraction[] attractionsReference, String rideName){
        for(Attraction attraction : attractionsReference){
            if(rideName == attraction.getRideName()){
                return attraction.waitTimeCalculation();
            }
        }
        return 201.11;
    }
    public void reduceNumberOfRides(){
        numberOfRides--;
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
        while(index < inputArray.length && inputArray[index] != null){
            index++;
        }
        RideAffinity[] outputArray = new RideAffinity[index];
        for(int i =0; i < outputArray.length;i++){
            outputArray[i] = inputArray[i];
        }
        return outputArray;
    }
    public int getNumberOfRides(){return numberOfRides;}
    public UUID getJobID(){
        return jobID;
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
