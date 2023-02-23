package com.codecool.marsexploration.logic.analyzer;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.Outcome;
import com.codecool.marsexploration.data.Symbol;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LackOfResourcesAnalyzer implements Analyzer {

    private static final int MINERAL_THRESHOLD = 1;
    private static final int WATER_THRESHOLD = 1;
    private List<Coordinate> trackedSightings;
    private int MAX_ITERATIONS_WITHOUT_RESOURCES;
    private int iterationsWithoutResources;

    @Override
    public Optional<Outcome> analyze(Context context) {
        if(iterationsWithoutResources == 0) setIterationsLimit(context);

        Map<Coordinate, String> sightings = context.getRover().getSightings();
        int mineralsCount = 0;
        int waterCount = 0;

        for (Map.Entry<Coordinate, String> entry : sightings.entrySet()) {
            if (entry.getValue().equals(Symbol.MINERAL.getSymbol()) && isSightingUnacknowledged(entry.getKey())){
                mineralsCount++;
                trackedSightings.add(entry.getKey());
            }
            else if (entry.getValue().equals(Symbol.WATER.getSymbol()) && isSightingUnacknowledged(entry.getKey())){
                waterCount++;
                trackedSightings.add(entry.getKey());
            }

            if (mineralsCount >= MINERAL_THRESHOLD && waterCount >= WATER_THRESHOLD){
                iterationsWithoutResources = 0;
            }
        }

        iterationsWithoutResources++;

        if (iterationsWithoutResources >= MAX_ITERATIONS_WITHOUT_RESOURCES){
            return Optional.of(Outcome.NOT_COLONIZABLE_RESOURCES);
        }

        return Optional.empty();
    }

    private boolean isSightingUnacknowledged(Coordinate sightingToCheck){
        return trackedSightings.stream()
                .noneMatch(sighting -> sighting.x() == sightingToCheck.x() && sighting.y() == sightingToCheck.y());
    }

    private void setIterationsLimit(Context context) {
        MAX_ITERATIONS_WITHOUT_RESOURCES = (int) (context.getTimeout() / 3);
    }
}
