package com.codecool.marsexploration.logic.routine;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.Rover;
import com.codecool.marsexploration.data.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExploringRoutine implements Routine{

    @Override
    public void move(Context context) {
        Rover rover = context.getRover();
        Coordinate roverPsn = rover.getCoordinate();
        Character[][] map = context.getMap();
        Random rand = new Random();

        List<Coordinate> availableSpots = getEmptyNeighborSpots(roverPsn, map);
        List<Coordinate> neverVisitedSpots = getNeverVisitedSpots(availableSpots, rover);

        Coordinate newPsn;
        if (neverVisitedSpots.isEmpty()){
            newPsn = availableSpots.get(rand.nextInt(availableSpots.size()));
        }else{
            newPsn = availableSpots.get(rand.nextInt(neverVisitedSpots.size()));
        }

        rover.moveForward(newPsn);
    }

    private List<Coordinate> getNeverVisitedSpots(List<Coordinate> availableSpots, Rover rover) {
        List<Coordinate> neverVisitedSpots = new ArrayList<>();
        for(Coordinate c : availableSpots){
            boolean hasVisited = rover.getTrackRecord().stream().anyMatch(coordinate -> coordinate.x() == c.x() && coordinate.y() == c.y());
            if (!hasVisited){
                neverVisitedSpots.add(c);
            }
        }
        return neverVisitedSpots;
    }

    private List<Coordinate> getEmptyNeighborSpots(Coordinate roverPsn, Character[][] map){
        List<Coordinate> availableSpots = new ArrayList<>();
        int[] dx = {-1, -1, -1, 0, 1, 1, 1, 0};
        int[] dy = {-1, 0, 1, 1, 1, 0, -1, -1};

        for (int i = 0; i < 8; i++){
            int newX = roverPsn.x() + dx[i];
            int newY = roverPsn.y() + dy[i];
            if (newX >= 0 && newX < map.length &&
                    newY >= 0 && newY < map.length &&
                    Character.toString(map[newX][newY]).equals(Symbol.EMPTY.getSymbol())){
                availableSpots.add(new Coordinate(newX, newY));
            }
        }

        return availableSpots;
    }
}
