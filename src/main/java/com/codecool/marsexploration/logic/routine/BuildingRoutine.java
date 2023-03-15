package com.codecool.marsexploration.logic.routine;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.Rover;

public class BuildingRoutine implements Routine{

    @Override
    public void move(Context context) {
        Rover rover = context.getRover();
        rover.buildCommandCentre(context);
    }
}
