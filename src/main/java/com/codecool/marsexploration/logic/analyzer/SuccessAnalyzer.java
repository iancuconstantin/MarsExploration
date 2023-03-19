package com.codecool.marsexploration.logic.analyzer;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.Outcome;
import com.codecool.marsexploration.data.Symbol;

import java.util.Map;
import java.util.Optional;

public class SuccessAnalyzer implements Analyzer{

    private final int RESOURCES_REQUIRED_FOR_COLONIZATION = 10;

    @Override
    public Optional<Outcome> analyze(Context context) {
        Map<Coordinate, String> sightings = context.getExplorer().getSightings();
        int mineralsCount = 0;
        int waterCount = 0;

        for (Map.Entry<Coordinate, String> entry: sightings.entrySet()){
            if (entry.getValue().equals(Symbol.MINERAL.getSymbol()))
                mineralsCount++;
            if (entry.getValue().equals(Symbol.WATER.getSymbol()))
                waterCount++;

            if(mineralsCount + waterCount == RESOURCES_REQUIRED_FOR_COLONIZATION)
                return Optional.of(Outcome.COLONIZABLE);
        }
        return Optional.empty();
    }
}
