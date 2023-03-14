package com.codecool.marsexploration.data;

import java.util.*;

public class CommandCentre {
    private final int REQUIRED_MINERALS_FOR_NEW_ROVER = 10;
    private final Coordinate location;
    private final UUID id;
    private final int sight;
    private Map<Rover, Coordinate> rovers;
    private List<Coordinate> resourcesInSight;
    private Map<Symbol, Integer> quantityStored;

    public CommandCentre(Coordinate location) {
        this.id = UUID.randomUUID();
        this.location = location;
        this.sight = 5;
        this.rovers = new HashMap<>();
        this.resourcesInSight = new ArrayList<>();
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


}
