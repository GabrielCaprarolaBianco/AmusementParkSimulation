package org.example;

import org.example.Simulation.TimeSimulation;

public class Main {
    public static void main(String[] args) {
        //This section is mainly author notes:
        //I had a lot of fun making this program
        //It taught me a lot about managing references from external classes
        //I also got to experiment with pathing algorithms by making one myself
        //I'll probably expand on this program some in the near future
        TimeSimulation TS = new TimeSimulation();
        TS.runSimulation(10000);
    }
}