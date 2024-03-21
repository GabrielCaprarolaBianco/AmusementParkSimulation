package org.example.ServerQueue;

import org.example.ServerQueue.ParkRides.Attraction;
import org.example.ServerQueue.ParkRides.AttractionValues;
import org.example.ServerQueue.QueueCore.Job;
import org.example.Simulation.RNG.NormalDistribution;

public class ArrivalProcess {
    //this arrival process follows a normal distribution
    //If it used an exponential process the lambda would be astronomically high
    private NormalDistribution arrivalDistribution;
    private AttractionValues[] attractionValues;
    public ArrivalProcess(int mean, double standardDeviation, AttractionValues[] attractionValues){
        arrivalDistribution = new NormalDistribution(mean, standardDeviation);
        this.attractionValues = attractionValues;
    }

    public Job[] nextArrivalGroup(){
        double numberOfArrivals = Math.ceil(arrivalDistribution.sample());
        Job[] output = new Job[(int)numberOfArrivals];
        for(int i = 0; i < (int)numberOfArrivals;i++){
            output[i] = new Job(attractionValues);
        }
        return output;
    }
}
