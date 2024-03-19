package org.example;

import org.example.Simulation.TimeSimulation;

public class Main {
    public static void main(String[] args) {
        TimeSimulation TS = new TimeSimulation();
        TS.runSimulation(1000000);
    }
}