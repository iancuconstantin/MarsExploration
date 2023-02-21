package com.codecool.marsexploration.logic.analyzer;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.Outcome;

import java.util.Optional;

public class LackOfResourcesAnalyzer implements Analyzer{

    @Override
    public Optional<Outcome> analyze(Context context) {
        return Optional.empty();
    }
}
