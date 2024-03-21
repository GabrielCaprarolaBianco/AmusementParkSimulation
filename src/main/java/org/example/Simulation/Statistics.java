package org.example.Simulation;

import org.example.ClockSystem.TimeStamp;
import org.example.ServerQueue.QueueCore.Queue;

public class Statistics {
    //Method used to calculate the Mean wait time and the standard deviation for each timeStamp collected during the simulation
    StatObject[] locationStatisticsList = new StatObject[10]; //11 is an arbitrary number picked for the time being. Might make more dynamic as needed

    public void calculate(Queue queue){
        add(queue);
        int index = 0;
        while(locationStatisticsList[index] != null) {
            double standardDeviation = standardDeviationCalculator(locationStatisticsList[index]);
            System.out.println("Mean Wait time for : "+ locationStatisticsList[index].getLocationName() + " is : " + locationStatisticsList[index].getMeanWaitTime() +
                    " and the Standard Deviation is : "+ standardDeviation);
            index++;
        }
    }

    //Method used to both create and add TimeStamp objects to the StatObjects
    private void add(Queue queue){
        TimeStamp[] tempArray;
        while(!queue.isQueueEmpty()){ //While loop that will run until all objects are moved from completedJobQueue into their respective bucket
            tempArray = queue.dequeue().timeStampDump(); //pulls the TimeStamp[] from each job within the Queue
            for(int i = 0; i < tempArray.length; i++){
                if(nameVerification(tempArray[i].getLocationOfWait(),locationStatisticsList)){ //checks to see if the name of the location on a TimeStamp has a matching bucket
                    int index = 0;
                    while(locationStatisticsList[index].getLocationName() != tempArray[i].getLocationOfWait()){
                        index++;
                    }
                    locationStatisticsList[index].add(tempArray[i]);}
                else{
                    int index = 0;
                    while(locationStatisticsList[index] != null){ index++; } //loop to find end of values within array
                    locationStatisticsList[index] = new StatObject(queue.queueLength()+1, tempArray[i].getLocationOfWait());
                    locationStatisticsList[index].add(tempArray[i]);
                }
            }//for Loop
        }//while Loop
    }

    //uses the population formula for standard deviation using the data collected within each timeStampBucket
    private double standardDeviationCalculator(StatObject timeStampBucket){
        double runningTotal = 0.0;
        double meanWaitTime = timeStampBucket.getMeanWaitTime();
        int index = 0;
        TimeStamp[] timeStamps = timeStampBucket.getTimeStampList();
        while(timeStamps[index] != null){
            runningTotal += Math.pow((timeStamps[index].getTotalWaitTime() - meanWaitTime),2);
            index++;
            if(index >= timeStamps.length)
                break;
        }
        return Math.pow(runningTotal/timeStampBucket.getNumberOfJobs(),.5);
    }

    private boolean nameVerification(String timeStampName, StatObject[] nameList){ //checks to see if name is on list
        int index = 0;
        while(nameList[index] != null){
            if(timeStampName == nameList[index].getLocationName()) return true;
            index++;
        }
        return false;
    }


    //This object acts as a bucket for collecting the TimeStamp objects for each type of wait
    //This object collects the total number of jobs within itself and the total wait time for those objects
    protected class StatObject{
        private double totalWaitTime; //used for average wait time
        private int numberOfJobs; //used for both
        private int arrayIndex;
        private TimeStamp[] timeStampList; //used for calculating standard deviations
        private String locationName; //used to distinguish each StatObject from each other

        protected StatObject(int timeStampsSize, String locationName){
            totalWaitTime = 0;
            numberOfJobs = 0;
            arrayIndex = 0;
            timeStampList = new TimeStamp[timeStampsSize];
            this.locationName = locationName;
        }
        protected String getLocationName(){
            return locationName;
        }

        protected void add(TimeStamp timeStamp){
            totalWaitTime += timeStamp.getTotalWaitTime();
            numberOfJobs += 1;
            timeStampList[arrayIndex] = timeStamp;
            arrayIndex += 1;
        }
        protected TimeStamp[] getTimeStampList(){
            return timeStampList;
        }
        protected double getMeanWaitTime(){
            return totalWaitTime/numberOfJobs;
        }
        protected int getNumberOfJobs(){
            return numberOfJobs;
        }
    }

}
