package com.codecool.marsexploration.logic.analyzer;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.Outcome;
import com.codecool.marsexploration.data.Symbol;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LackOfResourcesAnalyzer implements Analyzer {

    private static final int MINERAL_THRESHOLD = 1;
    private static final int WATER_THRESHOLD = 1;
    private Map<Coordinate,String> trackedSightings = new HashMap<>();
    private int MAX_ITERATIONS_WITHOUT_RESOURCES;
    private int iterationsWithoutResources;

    @Override
    public Optional<Outcome> analyze(Context context) {
        if(iterationsWithoutResources == 0) setIterationsLimit(context);

        Map<Coordinate, String> sightings = context.getExplorer().getSightings();
        int mineralsCount = 0;
        int waterCount = 0;

        for (Map.Entry<Coordinate, String> entry : sightings.entrySet()) {
            if (entry.getValue().equals(Symbol.MINERAL.getSymbol()) && !trackedSightings.containsKey(entry.getKey())){
                mineralsCount++;
                trackedSightings.put(entry.getKey(), entry.getValue());
            }
            else if (entry.getValue().equals(Symbol.WATER.getSymbol()) && !trackedSightings.containsKey(entry.getKey())){
                waterCount++;
                trackedSightings.put(entry.getKey(), entry.getValue());
            }
        }

        if (mineralsCount >= MINERAL_THRESHOLD || waterCount >= WATER_THRESHOLD){
            iterationsWithoutResources = 0;
        }else{
            iterationsWithoutResources++;
        }

        if (iterationsWithoutResources >= MAX_ITERATIONS_WITHOUT_RESOURCES){
            return Optional.of(Outcome.NOT_COLONIZABLE_RESOURCES);
        }

        return Optional.empty();
    }

    private void setIterationsLimit(Context context) {
        MAX_ITERATIONS_WITHOUT_RESOURCES = (int) (context.getTimeout() / 3);
    }
}
