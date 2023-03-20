package com.codecool.marsexploration.logic.phase;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.rover.Explorer;

public class MovementPhase implements Phase{

    @Override
    public void perform(Context context) {
        Explorer rover = context.getExplorer();
        rover.getState().move(context);
    }
}
