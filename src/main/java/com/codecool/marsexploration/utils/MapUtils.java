package com.codecool.marsexploration.utils;

import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.Symbol;
import com.codecool.marsexploration.data.rover.Rover;

import java.util.*;

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

    public static List<Coordinate> getNeighborSpots(Coordinate psn, Character[][] map){
        List<Coordinate> availableSpots = new ArrayList<>();
        int[] dx = {-1, -1, -1, 0, 1, 1, 1, 0};
        int[] dy = {-1, 0, 1, 1, 1, 0, -1, -1};

        for (int i = 0; i < 8; i++){
            int newX = psn.x() + dx[i];
            int newY = psn.y() + dy[i];
            if (newX >= 0 && newX < map.length &&
                    newY >= 0 && newY < map.length){
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

    public static List<Coordinate> getShortestRoute(Character[][] map, Coordinate from, Coordinate to){
        Queue<Coordinate> queue = new LinkedList<>(); //all nearby spots for current
        Set<Coordinate> visited = new HashSet<>(); //
        Map<Coordinate, Coordinate> previous = new HashMap<>();
        // queue   - 9, 10,11
        //visited  - F, 1, 2 , 3, 4, 5, 6,7 ,8, 9 , 10, 11
        //previous - (1,F), (2,F) , (3,F), (4,F) ,(5,F) ,(6,F), (7,F), (8,F), (9,6), (10,6), (11,7)
        //path     - F,6,9,T
        queue.add(from);
        visited.add(from);
        //distance.put(from, 0);
        // |1|2|3|
        // |4|F|5|
        // |6|7|8|
        // |9|10|11|
        // | |T| |
        while (!queue.isEmpty()){
            Coordinate current = queue.remove();
            if (getNeighborSpots(to, map).contains(current)){
                List<Coordinate> path = new ArrayList<>();
                while(previous.containsKey(current)){
                    path.add(0, current);
                    current = previous.get(current);
                }
                path.add(0, current);
                path.add(to);
                return path;
            }

            List<Coordinate> neighbors = getEmptyNeighborSpots(current, map);

            for (Coordinate c : neighbors){
                if (!visited.contains(c)){
                    queue.offer(c);
                    visited.add(c);
                    //distance.put(c, distance.get(current) + 1);
                    previous.put(c, current);
                }
            }
        }

        return Arrays.asList(from, to);
    }

}
