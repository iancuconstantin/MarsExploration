package com.codecool.marsexploration.data;

import com.codecool.marsexploration.logic.routine.ExploringRoutine;
import com.codecool.marsexploration.logic.routine.GatherRoutine;

import java.util.*;

public class CommandCentre {

    private final Coordinate location;
    private final UUID id;
    private final int sight;
    private Map<Rover, Coordinate> rovers;
    private List<Coordinate> resourcesInSight;
    private Map<Symbol, Integer> quantityStored;
    private static final int MIN_INDEX = 0;

    public CommandCentre(Coordinate location, Context context) {
        this.id = UUID.randomUUID();
        this.location = location;
        this.sight = 5;
        this.rovers = new HashMap<>();
        this.resourcesInSight = getListOfResources(context);
        //asign a rover for each resource
    }

    public Coordinate getLocation() {
        return location;
    }

    public UUID getId() {
        return id;
    }

    public int getSight() {
        return sight;
    }

    public Map<Rover, Coordinate> getRovers() {
        return rovers;
    }

    public List<Coordinate> getResourcesInSight() {
        return resourcesInSight;
    }



    private List<Coordinate> getListOfResources(Context context){
        List<Coordinate> resourcesInSight = new ArrayList<>();
        Character[][] map = context.getMap();
        for (int x = location.x() - sight; x <= location.x() + sight; x++) {
            for (int y = location.y() - sight; y <= location.y() + sight; y++) {
                if(map[x][y].toString().equals("*") || map[x][y].toString().equals("~")){
                    resourcesInSight.add(new Coordinate(x,y));
                }
            }
        }
        return resourcesInSight;
    }
    public List<Coordinate> checkFreeSpotForRover(Context context,Coordinate coordinate){
        List<Coordinate> freeSpots = new ArrayList<>();
        Character[][] map = context.getMap();
        for (int x = coordinate.x() - 1; x <= coordinate.x() + 1; x++) {
            for (int y = coordinate.y() - 1; y <= coordinate.y() + 1; y++) {
                if(map[x][y].toString().equals(Symbol.EMPTY.getSymbol())){
                    freeSpots.add(new Coordinate(x,y));
                }
            }
        }
        return freeSpots;
    }
    public void createNewRover(Coordinate coordinate){
        Coordinate resCoordinate = new Coordinate(getResourcesInSight().get(0).x(),getResourcesInSight().get(0).y());
        rovers.put(new Rover(coordinate,5,new GatherRoutine()),resCoordinate);
    }
}
