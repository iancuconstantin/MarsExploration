package com.codecool.marsexploration.logic.routine;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.Rover;

public class BuildingRoutine implements Routine{

    @Override
    public void move(Context context) {
        while (context.getCommandCentres().isEmpty()){
            // TODO build-CmdCntr
            Rover rover = context.getRover();
            rover.buildCommandCentre();
        }
    }
}
