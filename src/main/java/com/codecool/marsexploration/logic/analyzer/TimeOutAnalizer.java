package com.codecool.marsexploration.logic.analyzer;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.Outcome;

import java.util.Optional;

public class TimeOutAnalizer implements Analyzer{

    @Override
    public Optional<Outcome> analyze(Context context) {
        int currentStep = context.getStepNumber();
        long timeOut = context.getTimeout();

        if(currentStep == timeOut) {
            //context.setOutcome(Optional.of(Outcome.TIMEOUT));
            return Optional.of(Outcome.TIMEOUT);
        }
        return Optional.empty();
    }
}
