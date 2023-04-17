package src.SymbolicRegression.Elements;


import lombok.Data;

import java.util.ArrayList;

@Data
public class PreProcessing {

    private ArrayList<Double> mappingList;
    private double fitness;

    private double inverseFunctionedFitness;
    private double individualProbability;
    private double cumulativeProbability;


    private double rSquare;
    private double absRSquare;


    public PreProcessing() {

    }

    public PreProcessing(ArrayList<Double> mappingList, double fitness) {

        this.mappingList = mappingList;
        this.fitness = fitness;


    }

}
