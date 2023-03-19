package com.codecool.marsexploration.logic.routine;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.rover.Explorer;

public class BuildingRoutine implements Routine {

    @Override
    public void move(Context context) {
        Explorer rover = context.getExplorer();
        rover.buildOrMoveTowardsCommandCentre(context);
    }
}
