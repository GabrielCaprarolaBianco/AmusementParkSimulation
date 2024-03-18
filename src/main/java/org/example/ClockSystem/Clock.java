package org.example.ClockSystem;

public class Clock {
    //encapsulated Object responsible for the keeping of time
    private ClockObject globalClock;
    public Clock(double tickValue){
        globalClock = new ClockObject(tickValue);
    }

    public void tick(){ //this method should be used to advance time forward
        globalClock.tick();
    }
    public void setGlobalClock(double newTime){ //this method should only be used to rewind time, not advance it
        globalClock.setCurrentTime(newTime);
    }
    public double whatTimeIsIt(){ //method for returning current time
        return globalClock.whatTimeIsIt();
    }

    //this class is the private side of the Clock system that is constantly updated
    protected class ClockObject{
        private double currentTime;
        private double tickValue;

        protected ClockObject(double tickValue){
            currentTime = 0;
            this.tickValue = tickValue;
        }

        public void tick(){
            currentTime += tickValue;
        }
        public void setCurrentTime(double newTime){
            currentTime = newTime;
        }
        public double whatTimeIsIt(){
            return currentTime;
        }

    }//ClockObject
}//Clock