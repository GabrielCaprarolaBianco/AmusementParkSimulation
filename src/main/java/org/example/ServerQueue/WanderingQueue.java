package org.example.ServerQueue;
import org.example.ServerQueue.ParkRides.Attraction;
import org.example.ServerQueue.ParkRides.AttractionValues;
import org.example.ServerQueue.QueueCore.Job;
import org.example.ServerQueue.QueueCore.Queue;

public class WanderingQueue{
    //this queue has a special set of functions beyond those of a regular queue that require its own class
    private Queue processingQueue;
    private Queue completedQueueReference;
    private ArrivalProcess arrivalProcess;
    //private AttractionValues[] attractionValues;
    private Attraction[] attractionReferences;

    public WanderingQueue(AttractionValues[] attractionValues,Attraction[] attractionReferences, Queue completedQueueReference){
        processingQueue = new Queue();
        arrivalProcess = new ArrivalProcess(17, 4, attractionValues);
        //this.attractionValues = attractionValues;
        this.attractionReferences = attractionReferences;
        this.completedQueueReference = completedQueueReference;
    }

    public void action(double currentTime){
        //order of operations will be arrivals first, then moving everything around
        arrivals();
        jobMovement(currentTime);
    }

    private void jobMovement(double currentTime){
        while(!processingQueue.isQueueEmpty()){
            Job job = processingQueue.dequeue();
            if(job.getNumberOfRides() <= 0){ departure(job); }
            else{
                dequeueToAttraction(job,job.pathingDecisionEquation(attractionReferences), attractionReferences, currentTime);
            }
        }
    }
    private void arrivals(){
        Job[] inputArray = arrivalProcess.nextArrivalGroup();
        for(Job job : inputArray){
            processingQueue.enqueue(job);
        }
    }
    private void departure(Job job){
        completedQueueReference.enqueue(job);
    }
    private void dequeueToAttraction(Job job, String destinationName, Attraction[] attractionReferences, double currentTime){
        for(Attraction attraction : attractionReferences){
            if(attraction.getRideName() == destinationName){
                attraction.enqueue(job,currentTime);
                break;
            }
        }
    }
    public void enqueue(Job job){
        processingQueue.enqueue(job);
    }
}
