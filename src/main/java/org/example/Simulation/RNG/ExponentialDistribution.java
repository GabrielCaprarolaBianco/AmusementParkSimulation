package org.example.Simulation.RNG;

import java.util.Random;

public class ExponentialDistribution extends RandomDistribution{
    //used to generate a modifier to multiple with the averageTimeBetweenEvents for calculating arrival times
    private double lambda;
    private Random random;
    private double averageTimeBetweenEvents;

    public ExponentialDistribution(double lambda, double averageTimeBetweenEvents, Random random){
        this.lambda = lambda;
        this.averageTimeBetweenEvents = averageTimeBetweenEvents;
        this.random = random;
    }

    public double sample(){
        return ((-1/lambda)*Math.log(random.nextDouble(0,.99)))*averageTimeBetweenEvents;
    }
}
