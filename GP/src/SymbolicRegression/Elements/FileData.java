package src.SymbolicRegression.Elements;

import lombok.Data;

import java.util.ArrayList;

@Data
public class FileData {
    private Double independentVariable;
    private ArrayList<Double> features;
}
