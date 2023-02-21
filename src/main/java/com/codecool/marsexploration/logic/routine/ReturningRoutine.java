package com.codecool.marsexploration.logic.routine;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.Rover;

import java.util.List;

public class ReturningRoutine implements Routine{

    @Override
    public void move(Context context) {
        List<Coordinate> pastCoordinates = context.getRover().getTrackRecord();
        Rover rover = context.getRover();
        for (int i = pastCoordinates.size() - 1; i >= 0; i--){
            rover.setCoordinate(pastCoordinates.get(i));
        }
    }
}
