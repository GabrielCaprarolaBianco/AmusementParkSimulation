package org.example.Simulation;

import org.example.ClockSystem.Clock;
import org.example.ServerQueue.ParkRides.Attraction;
import org.example.ServerQueue.ParkRides.AttractionValues;
import org.example.ServerQueue.QueueCore.Queue;
import org.example.ServerQueue.WanderingQueue;
import org.w3c.dom.Attr;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TimeSimulation {
    private Clock globalClock;
    private WanderingQueue wanderingQueue;
    private Attraction[] attractions;
    private Queue completedQueue;
    private AttractionValues[] attractionValues;

    public TimeSimulation(){
        globalClock = new Clock(1);
        attractions = initializeAttractions();
        attractionValues = initializeAttractionValues(attractions);
        completedQueue = new Queue();
        wanderingQueue = new WanderingQueue(attractionValues, attractions, completedQueue);
        attractionFinalization(attractions,wanderingQueue);
    }

    public void runSimulation(double timeToRun){
        while(globalClock.whatTimeIsIt() < timeToRun){
            for(Attraction attraction : attractions){
                attraction.action(globalClock.whatTimeIsIt());
            }
            wanderingQueue.action(globalClock.whatTimeIsIt());
            globalClock.tick();
        }
    }


    //All methods below this comment are used in the initialization of the simulation loop
    private Attraction[] initializeAttractions(){
        Scanner k;
        try {
            k = new Scanner(new File("/Users/gcapr/intelliJ Files/CMIS202/AmusementParkSimulation/src/main/java/org/example/Simulation/AttractionInformation.txt")).useDelimiter("\n");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        k.next();
        //System.out.println(k.next())
        Attraction[] output = new Attraction[Integer.parseInt(k.next().replace("\r",""))];
        //Attraction[] output = new Attraction[5];
        //k.next();
        int arrayIndex = 0;
        while(k.hasNext()){
            String[] tempArray = k.next().split(",");
            tempArray[4] = tempArray[4].replace("\r","");
            output[arrayIndex] = new Attraction(tempArray[0],Double.parseDouble(tempArray[1]), Integer.parseInt(tempArray[2]),
                    Double.parseDouble(tempArray[3]), Integer.parseInt(tempArray[4]));
            arrayIndex++;
        }
        return output;
    }
    private void attractionFinalization(Attraction[] attractions, WanderingQueue wanderingQueueReference){
        for(Attraction attraction : attractions){
            attraction.setWanderingQueueReference(wanderingQueueReference);
        }
    }
    private AttractionValues[] initializeAttractionValues(Attraction[] attractions){
        AttractionValues[] output = new AttractionValues[attractions.length];
        int arrayIndex = 0;
        for(Attraction attraction : attractions){
            output[arrayIndex] = attraction.getNamePopularity();
            arrayIndex++;
        }
        return output;
    }

}
