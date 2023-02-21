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
        Coordinate goBackPsn = pastCoordinates.get(pastCoordinates.size()-1);
        pastCoordinates.remove(pastCoordinates.size()-1);
        rover.setCoordinate(goBackPsn);
    }
}
