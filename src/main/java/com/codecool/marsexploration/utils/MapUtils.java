package com.codecool.marsexploration.utils;

import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.Rover;
import com.codecool.marsexploration.data.Symbol;

import java.util.ArrayList;
import java.util.List;

public class MapUtils {

    public static List<Coordinate> getEmptyNeighborSpots(Coordinate psn, Character[][] map){
        List<Coordinate> availableSpots = new ArrayList<>();
        int[] dx = {-1, -1, -1, 0, 1, 1, 1, 0};
        int[] dy = {-1, 0, 1, 1, 1, 0, -1, -1};

        for (int i = 0; i < 8; i++){
            int newX = psn.x() + dx[i];
            int newY = psn.y() + dy[i];
            if (newX >= 0 && newX < map.length &&
                    newY >= 0 && newY < map.length &&
                    Character.toString(map[newX][newY]).equals(Symbol.EMPTY.getSymbol())){
                availableSpots.add(new Coordinate(newX, newY));
            }
        }

        return availableSpots;
    }

    public static List<Coordinate> getNeverVisitedSpots(List<Coordinate> availableSpots, Rover rover) {
        List<Coordinate> neverVisitedSpots = new ArrayList<>();
        for(Coordinate c : availableSpots){
            boolean hasVisited = rover.getTrackRecord().stream().anyMatch(coordinate -> coordinate.x() == c.x() && coordinate.y() == c.y());
            if (!hasVisited){
                neverVisitedSpots.add(c);
            }
        }
        return neverVisitedSpots;
    }

}
