package com.codecool.marsexploration.data;


import com.codecool.marsexploration.data.rover.Explorer;
import com.codecool.marsexploration.data.rover.Gatherer;
import com.codecool.marsexploration.logic.routine.GatherRoutine;

import java.util.*;

import static com.codecool.marsexploration.data.Symbol.MINERAL;
import static com.codecool.marsexploration.data.Symbol.WATER;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class CommandCentre {

    private final UUID id;
    private final int sight;
    private List<IdentifiedResource> resourcesInSight;
    private final Coordinate location;
    private List<Gatherer> gatherers;
    private final Map<Symbol, Integer> resourceInventory;
    private final Character[][] mapPlacedOn;
    private final int REQUIRED_MINERALS_FOR_NEW_ROVER = 10;


    public CommandCentre(Coordinate location, Context context) {
        this.id = UUID.randomUUID();
        this.location = location;
        this.sight = 8;
        this.gatherers = new ArrayList<>();
        this.resourcesInSight = identifyInSightResources(context);
        this.resourceInventory = initQuantityStored(context.getExplorer());
        this.mapPlacedOn = context.getMap();
        buildInitialGatherer(context);
    }

    public void buildInitialGatherer(Context context) {
        Optional<Gatherer> initialGatherer = buildGatherer();
        if(initialGatherer.isEmpty()) {
            throw new RuntimeException("Command center cannot build a gatherer");
        }

        Optional<IdentifiedResource> picked = pickAvailableResource();

        if(picked.isEmpty()) {
            throw new RuntimeException("Command center build without a resource");
        } else {
            assignResourceToGatherer(initialGatherer.get(), picked.get(), context.getMap());
        }
    }



    public Optional<Gatherer> buildGatherer() {
        int availableMineral = resourceInventory.get(MINERAL);
        if (availableMineral >= 10) {
            resourceInventory.put(MINERAL, availableMineral - 10);
            Gatherer gatherer = new Gatherer(this);
            gatherers.add(gatherer);

            return Optional.of(gatherer);
        }

        return Optional.empty();
    }
    public void assignResourceToGatherer(Gatherer gatherer, IdentifiedResource identifiedResource, Character[][] mapPlacedOn) {
        identifiedResource.setAvailableToBeAssigned(false);
        gatherer.setAssignedResource(identifiedResource, mapPlacedOn);
    }

    private List<IdentifiedResource> identifyInSightResources(Context context) {
        List<IdentifiedResource> resourcesInSight = new ArrayList<>();
        Character[][] map = context.getMap();
        for (int x = max(location.x() - sight, 0); x <= min(location.x() + sight, map.length); x++) {
            for (int y = max(location.y() - sight, 0); y <= min(location.y() + sight, map.length); y++) {
                if (MINERAL.getSymbol().equals(map[x][y].toString())) {
                    resourcesInSight.add(
                            new IdentifiedResource(MINERAL, new Coordinate(x, y)));
                } else if (WATER.getSymbol().equals(map[x][y].toString())) {
                    resourcesInSight.add(
                            new IdentifiedResource(WATER, new Coordinate(x, y)));
                }
            }
        }
        return resourcesInSight;
    }

    public List<Coordinate> checkFreeSpotForRover(Context context, Coordinate coordinate) {
        List<Coordinate> freeSpots = new ArrayList<>();
        Character[][] map = context.getMap();
        for (int x = coordinate.x() - 1; x <= coordinate.x() + 1; x++) {
            for (int y = coordinate.y() - 1; y <= coordinate.y() + 1; y++) {
                if (map[x][y].toString().equals(Symbol.EMPTY.getSymbol())) {
                    freeSpots.add(new Coordinate(x, y));
                }
            }
        }
        return freeSpots;
    }

    public Optional<IdentifiedResource> pickAvailableResource() {
        return resourcesInSight.stream()
                .filter(IdentifiedResource::isAvailableToBeAssigned)
                .findFirst();

//        if (getResourceManaged() < getResourcesInSight().size() - 1) {
//            Coordinate resCoordinate = new Coordinate(
//                    getResourcesInSight().get(getResourceManaged()).getLocation().x(),
//                    getResourcesInSight().get(getResourceManaged()).getLocation().y());
//            rovers.put(new Rover(coordinate, 5, new GatherRoutine()), resCoordinate);
//            resourceManaged++;
//            int newValue = resourceInventory.get(MINERAL) - REQUIRED_MINERALS_FOR_NEW_ROVER;
//            setResourceInventory(newValue);
//        }
    }

    public void collectResources(Symbol type, int amount) {
        resourceInventory.put(type, resourceInventory.get(type) + amount);

        if (resourcesInSight.size() != gatherers.size() && type.equals(MINERAL)){
            boolean hasEnoughMineralsForNewGatherer = checkInventoryForMinerals();
            if(hasEnoughMineralsForNewGatherer){
                Optional<Gatherer> newGatherer = buildGatherer();
                if(newGatherer.isEmpty()) {
                    throw new RuntimeException("Command centre could not build a new gatherer");
                }

                Optional<IdentifiedResource> picked = pickAvailableResource();
                if(picked.isEmpty()){
                    throw new RuntimeException("Command center build without a resource for new gatherer");
                }else{
                    assignResourceToGatherer(newGatherer.get(), picked.get(), mapPlacedOn);
                }
            }
        }
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

    public List<Gatherer> getGatherers() {
        return gatherers;
    }

    public List<IdentifiedResource> getResourcesInSight() {
        return resourcesInSight;
    }

    public Map<Symbol, Integer> getResourceInventory() {
        return resourceInventory;
    }

    public int getREQUIRED_MINERALS_FOR_NEW_ROVER() {
        return REQUIRED_MINERALS_FOR_NEW_ROVER;
    }

    public void setResourceInventory(int newValue) {
        resourceInventory.put(MINERAL, newValue);
    }

    private Map<Symbol, Integer> initQuantityStored(Explorer explorer) {
        Map<Symbol, Integer> quantityStored = new HashMap<>();
        quantityStored.put(MINERAL, explorer.deliverResourcesForBuildingRover(this));
        quantityStored.put(Symbol.WATER, 0);
        return quantityStored;
    }

    private boolean checkInventoryForMinerals() {
        return resourceInventory.get(MINERAL) == REQUIRED_MINERALS_FOR_NEW_ROVER;
    }
}
