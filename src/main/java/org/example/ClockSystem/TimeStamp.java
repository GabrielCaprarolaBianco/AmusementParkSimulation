package org.example.ClockSystem;



public class TimeStamp{
    //This class emulates the way people track the amount of time they spend within each section of a queueing system
    //This will include any part of the system where people are required to wait, so service and queue times are included
    //Each TimeStamp will be differentiated through the name given to each time stamp
    //The format will be queueWait:locationOfQueue, ServiceWait:TypeOfService. for queue waits and service waits respectively
    //The TimeStamps will be connected like a linked list allowing for future flexibility when job routing becomes more complicated
    private double startTime, endTime, totalWaitTime;
    private String locationOfWait;
    public TimeStamp(double startTime, String locationOfWait){
        this.startTime = startTime;
        endTime = 0;
        totalWaitTime = 0;
        this.locationOfWait = locationOfWait;
    }
    public void setEndTime(double endTime){
        this.endTime = endTime;
        setTotalWaitTime();
    }
    protected void setTotalWaitTime(){
        totalWaitTime = endTime - startTime;
    }
    public double getTotalWaitTime(){
        return totalWaitTime;
    }
    public String getLocationOfWait(){
        return locationOfWait;
    }
}