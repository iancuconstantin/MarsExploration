package com.codecool.marsexploration.data;


import com.codecool.marsexploration.logic.routine.GatherRoutine;
import java.util.*;

public class CommandCentre {

    private final Coordinate location;
    private final UUID id;
    private final int sight;
    private Map<Rover, Coordinate> rovers;
    private List<Coordinate> resourcesInSight;

    private Map<Symbol, Integer> quantityStored;

    private int resourceManaged;

    private final int REQUIRED_MINERALS_FOR_NEW_ROVER = 10;

    public CommandCentre(Coordinate location, Context context) {
        this.id = UUID.randomUUID();
        this.location = location;
        this.sight = 5;
        this.rovers = new HashMap<>();
        this.resourcesInSight = getListOfResources(context);
        this.quantityStored = initQuantityStored();
        this.resourceManaged = 0;
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

    public int getResourceManaged() {
        return resourceManaged;
    }

    public Map<Symbol, Integer> getQuantityStored() {
        return quantityStored;
    }

    public void setQuantityStored(int newValue) {
        quantityStored.put(Symbol.MINERAL, newValue);
    }

    private Map<Symbol,Integer> initQuantityStored(){
        Map<Symbol, Integer> quantityStored = new HashMap<>();
        quantityStored.put(Symbol.MINERAL,100);
        quantityStored.put(Symbol.WATER,100);
        return quantityStored;
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
        if(getResourceManaged() < getResourcesInSight().size()-1){
            Coordinate resCoordinate = new Coordinate(getResourcesInSight().get(getResourceManaged()).x(),getResourcesInSight().get(getResourceManaged()).y());
            rovers.put(new Rover(coordinate,5,new GatherRoutine()),resCoordinate);
            resourceManaged++;
            int newValue = quantityStored.get(Symbol.MINERAL)-REQUIRED_MINERALS_FOR_NEW_ROVER;
            setQuantityStored(newValue);
        }
    }
}
