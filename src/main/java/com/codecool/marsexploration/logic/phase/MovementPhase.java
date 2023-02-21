package com.codecool.marsexploration.logic.phase;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.Rover;

public class MovementPhase implements Phase{

    @Override
    public void perform(Context context) {
        Rover rover = context.getRover();
        rover.getState().move(context);
    }
}
