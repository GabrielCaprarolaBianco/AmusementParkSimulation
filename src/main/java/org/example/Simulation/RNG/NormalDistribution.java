package org.example.Simulation.RNG;

import java.util.Random;

public class NormalDistribution {
    //This method generates a normal distribution used to calculate a semi-random number for the service time
    private double mean;
    private Random random;
    private double standardDeviation;
    public NormalDistribution(double mean, double standardDeviation){
        this.mean = mean;
        this.standardDeviation = standardDeviation;
        random = new Random();
    }
    public double sample(){
        return (standardDeviation* random.nextDouble(-2,2)) + mean;
    }
}
