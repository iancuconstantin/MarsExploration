package com.codecool.marsexploration.logic.analyzer;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.Outcome;

import java.util.Optional;

public class SuccessAnalyzer implements Analyzer{

    @Override
    public Optional<Outcome> analyze(Context context) {
        context.getRover().getSightings();
        return Optional.empty();
    }
}
