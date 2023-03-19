package com.codecool.marsexploration.logic.analyzer;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.Outcome;
import com.codecool.marsexploration.data.Symbol;

import java.util.Map;
import java.util.Optional;

public class AlienSpotterAnalyzer implements Analyzer{

    @Override
    public Optional<Outcome> analyze(Context context) {

        Map<Coordinate,String> sightings = context.getExplorer().getSightings();
        for(Map.Entry<Coordinate,String> entry:sightings.entrySet()){
            if(entry.getValue().equals(Symbol.ALIEN.getSymbol())){
                return Optional.of(Outcome.NOT_COLONIZABLE_ALIENS);
            }
        }

        return Optional.empty();
    }
}
