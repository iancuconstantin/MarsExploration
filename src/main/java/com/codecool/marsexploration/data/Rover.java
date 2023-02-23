package com.codecool.marsexploration.data;

import com.codecool.marsexploration.logic.routine.Routine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Rover {
    private int id;
    private Coordinate coordinate;
    private int sight;
    private Routine state;
    private List<Coordinate> trackRecord;
    private Map<Coordinate, String> sightings;
    private int currentTrackRecordIndex = 0;

    public Rover(int id, Coordinate coordinate, int sight, Routine state,
                 List<Coordinate> trackRecord, Map<Coordinate, String> sightings) {
        this.id = id;
        this.coordinate = coordinate;
        this.sight = sight;
        this.state = state;
        this.trackRecord = trackRecord;
        this.sightings = sightings;
        initFirstPsnInTrackRecord();
    }

    private void initFirstPsnInTrackRecord(){
        trackRecord.add(coordinate);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}