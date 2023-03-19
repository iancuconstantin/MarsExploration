package com.codecool.marsexploration.data.rover;

import com.codecool.marsexploration.data.CommandCentre;
import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.Symbol;
import com.codecool.marsexploration.logic.routine.Routine;
import com.codecool.marsexploration.utils.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.codecool.marsexploration.data.Symbol.MINERAL;

public class Explorer extends Rover {
    private final int sight;
    private Map<Coordinate, String> sightings;
    private Map<Symbol, Integer> storedResources;
    private List<Coordinate> routeToBuildingSpot;
    private Coordinate buildCommandCentreSpot;
    public int currentStepsInConstruction = 0;
    public int STEPS_NEEDED_FOR_CONSTRUCTION = 4;

    public Explorer(Coordinate coordinate, Routine state, int sight) {
        super(coordinate, state);
        this.sight = sight;
        this.sightings = new HashMap<>();
        this.storedResources = initStoredResources();
        this.buildCommandCentreSpot = null;
        this.routeToBuildingSpot = null;
    }

    public void buildCommandCentre(Context context) {
        if(buildCommandCentreSpot == null){
            findAndAssignBestBuildingSpot(context);
        }
        if (routeToBuildingSpot == null) {
            this.routeToBuildingSpot = MapUtils.getShortestRoute(context.getMap(), getCurrentCoordinate(), buildCommandCentreSpot);
            routeToBuildingSpot.add(buildCommandCentreSpot);
            routeToBuildingSpot.remove(0);
        } else {
            if (!routeToBuildingSpot.isEmpty()) {
                setCurrentLocation(routeToBuildingSpot.remove(0));
            }
        }
        if (currentStepsInConstruction < STEPS_NEEDED_FOR_CONSTRUCTION) {
            currentStepsInConstruction++;
            useResourcesForBuilding();
        } else {
            CommandCentre commandCentre = new CommandCentre(buildCommandCentreSpot, context);
            currentStepsInConstruction = 0;
            context.deliverNewCommandCentre(commandCentre);
            deliverResourcesForBuildingRover(commandCentre);
        }
    }

    public int deliverResourcesForBuildingRover(CommandCentre commandCentre) {
        if(storedResources.get(MINERAL) - commandCentre.getREQUIRED_MINERALS_FOR_NEW_ROVER() < 0){
            throw new RuntimeException("Not enough minerals for delivering new command centre");
        }
        storedResources.put(MINERAL, storedResources.get(MINERAL) - commandCentre.getREQUIRED_MINERALS_FOR_NEW_ROVER());
        return commandCentre.getREQUIRED_MINERALS_FOR_NEW_ROVER();
    }

    public void findAndAssignBestBuildingSpot(Context context) {
        List<Coordinate> resourcesSpots = sightings.entrySet().stream()
                .filter(entry -> entry.getValue().equals(MINERAL.getSymbol()) || entry.getValue().equals(Symbol.WATER.getSymbol()))
                .map(Map.Entry::getKey)
                .toList();

        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Coordinate c : resourcesSpots) {
            if (c.x() < minX) minX = c.x();
            if (c.x() > maxX) maxX = c.x();
            if (c.y() > maxY) maxY = c.y();
            if (c.y() < minY) minY = c.y();
        }

        Character[][] map = context.getMap();
        int commandCentreSight = context.getCOMMAND_CENTRE_SIGHT();

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                int foundResources = 0;
                // iterate through surrounding points within max sight of 5
                for (int i = Math.max(x - commandCentreSight, minX); i <= Math.min(x + commandCentreSight, maxX); i++) {
                    for (int j = Math.max(y - commandCentreSight, minY); j <= Math.min(y + commandCentreSight, maxY); j++) {
                        String currentSymbol = map[i][j].toString();
                        String mineralSymbol = MINERAL.getSymbol();
                        String waterSymbol = Symbol.WATER.getSymbol();
                        if (currentSymbol.equals(waterSymbol) || currentSymbol.equals(mineralSymbol)) {
                            foundResources++;
                            if (foundResources == 3) {
                                List<Coordinate> emptyNearbySpots = MapUtils.getEmptyNeighborSpots(new Coordinate(i, j), map);
                                List<Coordinate> nearbySpots = MapUtils.getNeighborSpots(new Coordinate(i, j), map);
                                System.out.println(nearbySpots);
                                for (Coordinate c : nearbySpots) {
                                    emptyNearbySpots = MapUtils.getEmptyNeighborSpots(c, map).isEmpty() ? emptyNearbySpots : MapUtils.getEmptyNeighborSpots(c, map);
                                    if (!emptyNearbySpots.isEmpty()) {
                                        Random random = new Random();
                                        Coordinate buildingSpot = emptyNearbySpots.get(random.nextInt(emptyNearbySpots.size()));
                                        this.buildCommandCentreSpot = buildingSpot;
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public Map<Coordinate, String> getSightings() {
        return sightings;
    }

    public void setSightings(Map<Coordinate, String> sightings) {
        this.sightings = sightings;
    }

    public int getSight() {
        return sight;
    }

    public int getCurrentStepsInConstruction() {
        return currentStepsInConstruction;
    }

    public int getSTEPS_NEEDED_FOR_CONSTRUCTION() {
        return STEPS_NEEDED_FOR_CONSTRUCTION;
    }

    public void useResourcesForBuilding() {
        storedResources.put(MINERAL, storedResources.get(MINERAL) - 5);
    }

    public Map<Symbol, Integer> getStoredResources() {
        return storedResources;
    }

    public Coordinate getBuildCommandCentreSpot() {
        return buildCommandCentreSpot;
    }

    public List<Coordinate> getRouteToBuildingSpot() {
        return routeToBuildingSpot;
    }

    private Map<Symbol, Integer> initStoredResources() {
        Map<Symbol, Integer> resources = new HashMap<>();
        resources.put(MINERAL, 60);

        return resources;
    }
}
