package com.codecool.marsexploration.data.rover;

import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.logic.routine.Routine;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Rover {
    private final UUID id;
    private Coordinate currentLocation;
    private Routine state;
    private List<Coordinate> trackRecord;
    private int currentTrackRecordIndex = 0;

    public Rover(Coordinate coordinate) {
        this.id = UUID.randomUUID();
        this.currentLocation = coordinate;
        this.trackRecord = new ArrayList<>();
    }

    public Rover(Coordinate coordinate, Routine state) {
        this(coordinate);
        this.state = state;
    }

    public void moveForward(Coordinate newPsn){
        setCurrentLocation(newPsn);
        trackRecord.add(newPsn);
        currentTrackRecordIndex++;
    }

    public void moveBack(){
        currentTrackRecordIndex--;
        Coordinate goBackPsn = trackRecord.get(currentTrackRecordIndex);
        setCurrentLocation(goBackPsn);
    }

    public int getCurrentTrackRecordIndex() {
        return currentTrackRecordIndex;
    }

    public UUID getId() {
        return id;
    }

    public Coordinate getCurrentCoordinate() {
        return currentLocation;
    }

    public void setCurrentLocation(Coordinate currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Routine getState() {
        return state;
    }

    public void setState(Routine state) {
        this.state = state;
    }

    public void setCurrentTrackRecordIndex(int index) {currentTrackRecordIndex = index;}

    public List<Coordinate> getTrackRecord() {
        return trackRecord;
    }

    public Coordinate getCurrentLocation() {
        return currentLocation;
    }

    public void initFirstPsnInTrackRecord(){
        if(getTrackRecord().isEmpty()) {
            trackRecord.add(currentLocation);
        }
    }
}