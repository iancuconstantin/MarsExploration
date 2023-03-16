package com.codecool.marsexploration.data;

import com.codecool.marsexploration.logic.routine.ExploringRoutine;
import com.codecool.marsexploration.logic.routine.ReturningRoutine;
import com.codecool.marsexploration.logic.routine.Routine;
import com.codecool.marsexploration.utils.MapUtils;

import java.util.*;

public class Rover {
    private UUID id;
    private Coordinate coordinate;
    private int sight;
    private Routine state;
    private List<Coordinate> trackRecord;
    private Map<Coordinate, String> sightings;
    private int currentTrackRecordIndex = 0;
    private int resources;
    private Coordinate designatedGatheringSpot;
    private List<Coordinate> gatheringRoute;
    private int gatheredResources;
    private int storedResources;
    private Coordinate buildCommandCentreSpot;
    private List<Coordinate> routeToBuildingSpot;

    public Rover(Coordinate coordinate, int sight, Routine state) {
        this.id = UUID.randomUUID();
        this.coordinate = coordinate;
        this.sight = sight;
        this.state = state;
        this.trackRecord = new ArrayList<>();
        this.sightings = new HashMap<>();
        this.resources = 20;
        initFirstPsnInTrackRecord();
        this.designatedGatheringSpot = null;
        this.gatheringRoute = null;
        this.gatheredResources = 0;
        this.storedResources = 20;
        this.buildCommandCentreSpot = null;
        this.routeToBuildingSpot = null;
    }

    private void initFirstPsnInTrackRecord(){
        trackRecord.add(coordinate);
    }

    public int getCurrentTrackRecordIndex() {
        return currentTrackRecordIndex;
    }

    public List<Coordinate> getRouteToBuildingSpot(){return routeToBuildingSpot;}

    public UUID getId() {
        return id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public int getStoredResources() { return storedResources; }

    public void useResourcesForBuilding(){ storedResources -= 5; }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public int getSight() {
        return sight;
    }

    public void setSight(int sight) {
        this.sight = sight;
    }

    public Routine getState() {
        return state;
    }

    public void setState(Routine state) {
        this.state = state;
    }

    public List<Coordinate> getTrackRecord() {
        return trackRecord;
    }

    public void setTrackRecord(List<Coordinate> trackRecord) {
        this.trackRecord = trackRecord;
    }

    public Map<Coordinate, String> getSightings() {
        return sightings;
    }

    public void setSightings(Map<Coordinate, String> sightings) {
        this.sightings = sightings;
    }

    public Coordinate getDesignatedGatheringSpot() {
        return designatedGatheringSpot;
    }

    public List<Coordinate> getGatheringRoute() {
        return gatheringRoute;
    }

    public int getGatheredResources() {
        return gatheredResources;
    }

    public Coordinate getBuildCommandCentreSpot() {
        return buildCommandCentreSpot;
    }

    public void moveForward(Coordinate newPsn){
        setCoordinate(newPsn);
        trackRecord.add(newPsn);
        currentTrackRecordIndex++;
    }

    public void moveBack(){
        currentTrackRecordIndex--;
        Coordinate goBackPsn = trackRecord.get(currentTrackRecordIndex);
        setCoordinate(goBackPsn);
    }


    public void buildCommandCentre(Context context) {
        Coordinate buildingSpot;

        if(buildCommandCentreSpot == null){
            buildingSpot = findBestBuildingSpot(context);
            this.buildCommandCentreSpot = buildingSpot;
        }

        if(routeToBuildingSpot == null){
            this.routeToBuildingSpot = MapUtils.getShortestRoute(context.getMap(), coordinate, buildCommandCentreSpot);
            routeToBuildingSpot.add(buildCommandCentreSpot);
            routeToBuildingSpot.remove(0);
        }else{
            if(!routeToBuildingSpot.isEmpty()) {
                System.out.println(routeToBuildingSpot);
                setCoordinate(routeToBuildingSpot.remove(0));
            }
        }

        if(routeToBuildingSpot.isEmpty()){
            if (context.currentStepsInConstruction < context.stepsNeededForConstruction) {
                context.currentStepsInConstruction++;
                useResourcesForBuilding();
            }else{
                CommandCentre commandCentre = new CommandCentre(buildCommandCentreSpot, context);
                context.currentStepsInConstruction = 0;
                context.getCommandCentres().add(commandCentre);
            }
        }
    }

    private Coordinate findBestBuildingSpot(Context context) {
        List<Coordinate> resourcesSpots = sightings.entrySet().stream()
                .filter(entry -> entry.getValue().equals(Symbol.MINERAL.getSymbol()) || entry.getValue().equals(Symbol.WATER.getSymbol()))
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
                        String mineralSymbol = Symbol.MINERAL.getSymbol();
                        String waterSymbol = Symbol.WATER.getSymbol();
                        if(currentSymbol.equals(waterSymbol) || currentSymbol.equals(mineralSymbol)){
                            foundResources++;
                            if (foundResources == 3){
                                List<Coordinate> emptyNearbySpots = MapUtils.getEmptyNeighborSpots(new Coordinate(i,j),map);

                                while (true){
                                    List<Coordinate> nearbySpots = MapUtils.getNeighborSpots(new Coordinate(i,j), map);
                                    for (Coordinate c : nearbySpots){
                                        emptyNearbySpots = MapUtils.getEmptyNeighborSpots(c, map).isEmpty() ? emptyNearbySpots : MapUtils.getEmptyNeighborSpots(c, map);
                                        if(!emptyNearbySpots.isEmpty()){
                                            Random random = new Random();
                                            return emptyNearbySpots.get(random.nextInt(emptyNearbySpots.size()));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new RuntimeException("No suitable building spot found!");
    }
}