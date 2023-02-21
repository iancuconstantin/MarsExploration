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
        //TODO same as exploring routine, implemment below in new method;
        //TODO do not remove from track records, use field which will be incremented in move forward and decremented when moved back'
        Coordinate goBackPsn = pastCoordinates.get(pastCoordinates.size()-1);
        pastCoordinates.remove(pastCoordinates.size()-1);
        rover.setCoordinate(goBackPsn);
    }
}
