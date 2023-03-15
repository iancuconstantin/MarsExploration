package com.codecool.marsexploration.data;

import com.codecool.marsexploration.logic.routine.Routine;

import java.util.*;

public class Rover {
    private UUID id;
    private Coordinate coordinate;
    private int sight;
    private Routine state;
    private List<Coordinate> trackRecord;
    private Map<Coordinate, String> sightings;
    private int currentTrackRecordIndex = 0;
    private boolean isColonizer;
    private int resources;

    public Rover(Coordinate coordinate, int sight, Routine state) {
        this.id = UUID.randomUUID();
        this.coordinate = coordinate;
        this.sight = sight;
        this.state = state;
        this.trackRecord = new ArrayList<>();
        this.sightings = new HashMap<>();
        this.isColonizer = false;
        this.resources = 20;
        initFirstPsnInTrackRecord();
    }

    private void initFirstPsnInTrackRecord(){
        trackRecord.add(coordinate);
    }

    public UUID getId() {
        return id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

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

    public void buildCommandCentre() {
        //Coordinate buildingSpot = findBestBuildingSpot();
        //TODO
    }

    private Coordinate findBestBuildingSpot(Context context) {
        List<Coordinate> resourcesSpots = new ArrayList<>();

        for (Map.Entry<Coordinate, String> entry : sightings.entrySet()) {
            if (entry.getValue().equals(Symbol.MINERAL.getSymbol()) || entry.getValue().equals(Symbol.WATER.getSymbol())) {
                resourcesSpots.add(entry.getKey());
            }
        }

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
        int maxXAreaBound = 0;
        int maxYAreaBound = 0;
        int foundResources = 0;
        for (int i = minX; i <= maxX; i++) {
            maxXAreaBound++;
            if (maxXAreaBound == 5) {
                maxXAreaBound = 0;
                i--;
                foundResources = 0;
                continue;
            }
            for (int j = minY; j <= minY; j++) {
                maxYAreaBound++;
                if (maxYAreaBound == 5) {
                    maxYAreaBound = 0;
                    j--;
                    foundResources = 0;
                    continue;
                }
                if (String.valueOf(map[i][j]).equals(Symbol.MINERAL.getSymbol()) || String.valueOf(map[i][j]).equals(Symbol.WATER.getSymbol())) {
                    foundResources++;
                }
            }
        }
        return null;
    }
}