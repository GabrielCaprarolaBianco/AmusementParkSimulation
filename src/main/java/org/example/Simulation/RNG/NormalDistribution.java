package org.example.Simulators.RNG;

import java.util.Random;

public class NormalDistribution {
    //This method generates a normal distribution used to calculate a semi-random number for the service time
    private double meanServiceTime;
    private Random random;
    private double standardDeviation;
    public NormalDistribution(double meanServiceTime, double standardDeviation){
        this.meanServiceTime = meanServiceTime;
        this.standardDeviation = standardDeviation;
        random = new Random();
    }
    public double sample(){
        return (standardDeviation* random.nextDouble(-2,2)) + meanServiceTime;
    }
}
