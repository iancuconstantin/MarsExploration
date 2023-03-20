package com.codecool.marsexploration.logic.routine;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.rover.Explorer;
import com.codecool.marsexploration.utils.MapUtils;

import java.util.List;
import java.util.Random;

public class ExploringRoutine implements Routine {

    @Override
    public void move(Context context) {
        Explorer rover = context.getExplorer();
        rover.initFirstPsnInTrackRecord();

        Coordinate roverPsn = rover.getCurrentLocation();
        Character[][] map = context.getMap();
        Random rand = new Random();

        List<Coordinate> availableSpots = MapUtils.getEmptyNeighborSpots(roverPsn, map);
        List<Coordinate> neverVisitedSpots = MapUtils.getNeverVisitedSpots(availableSpots, rover);

        Coordinate newPsn;
        if (neverVisitedSpots.isEmpty()) {
            newPsn = availableSpots.get(rand.nextInt(availableSpots.size()));
        } else {
            newPsn = availableSpots.get(rand.nextInt(neverVisitedSpots.size()));
        }

        rover.moveForward(newPsn);
    }
}
