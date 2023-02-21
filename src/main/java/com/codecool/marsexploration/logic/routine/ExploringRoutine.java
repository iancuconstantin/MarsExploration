package com.codecool.marsexploration.logic.routine;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.Rover;
import com.codecool.marsexploration.data.Symbol;

import java.util.ArrayList;
import java.util.List;

public class ExploringRoutine implements Routine{

    @Override
    public void move(Context context) {
        Rover rover = context.rover();
        Coordinate roverPsn = rover.getCoordinate();
        Character[][] map = context.map();
        List<Coordinate> availableSpots = new ArrayList<>();
        int[] dx = {-1, -1, -1, 0, 1, 1, 1, 0};
        int[] dy = {-1, 0, 1, 1, 1, 0, -1, -1};
        for (int i = 0; i < 8; i++){
            int newX = roverPsn.x() + dx[i];
            int newY = roverPsn.y() + dy[i];

            if (newX >= 0 && newX < map.length && newY >= 0 && newY < map[0].length && (map[newX][newY].equals(Symbol.EMPTY))){
                availableSpots.add(new Coordinate(newX, newY));
            }
        }
    }
}
