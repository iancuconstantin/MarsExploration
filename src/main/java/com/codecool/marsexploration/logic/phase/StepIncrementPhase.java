package com.codecool.marsexploration.logic.phase;

import com.codecool.marsexploration.data.Context;

public class StepIncrementPhase implements Phase{

    private int currentStepNumber;
    @Override
    public void perform(Context context) {
        currentStepNumber = context.getStepNumber();
        currentStepNumber++;
        context.setStepNumber(currentStepNumber);
    }
}
