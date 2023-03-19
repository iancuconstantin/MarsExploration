package com.codecool.marsexploration.data.rover;

import com.codecool.marsexploration.data.*;
import com.codecool.marsexploration.logic.routine.GatherRoutine;
import com.codecool.marsexploration.logic.routine.Routine;
import com.codecool.marsexploration.utils.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.codecool.marsexploration.data.Symbol.MINERAL;

public class Gatherer extends Rover {
    private Map<Symbol, Integer> totalGatheredResources;
    private IdentifiedResource assignedResource;
    private final Map<Symbol, Integer>  resourcesInBag;
    private List<Coordinate> pathToResource;
    private final CommandCentre ownedBy;
    private boolean hasGathered;
    private int GATHERING_POWER = 5 ;

    public Gatherer(CommandCentre ownedBy) {
        super(ownedBy.getLocation());
        Routine routine = new GatherRoutine(this);
        setState(routine);
        this.ownedBy = ownedBy;
        this.pathToResource = null;
        this.resourcesInBag = new HashMap<>();
        this.totalGatheredResources = new HashMap<>();
        hasGathered = false;
    }

    private Map<Symbol, Integer> initTotalGatheredResources() {
        Map<Symbol, Integer> totalStored = new HashMap<>();
        totalStored.put(MINERAL, 0);
        totalStored.put(Symbol.WATER, 0);

        return totalStored;
    }

    private void initBag() {
        resourcesInBag.put(assignedResource.getResourceType(), 0);
    }

    public Map<Symbol, Integer> getGatheredResources() {
        return totalGatheredResources;
    }

    public Map<Symbol, Integer> getTotalGatheredResources() {
        return totalGatheredResources;
    }

    public IdentifiedResource getAssignedResource() {
        return assignedResource;
    }

    public List<Coordinate> getPathToResource() {
        return pathToResource;
    }

    public CommandCentre getOwnedBy() {
        return ownedBy;
    }

    public void setAssignedResource(IdentifiedResource assignedResource, Character[][] mapPlacedOn) {
        this.assignedResource = assignedResource;
        this.pathToResource = MapUtils.getShortestRoute(
                mapPlacedOn,
                ownedBy.getLocation(),
                assignedResource.getLocation()
        );
        initBag();
    }

    public boolean hasGathered() {
        return hasGathered;
    }

    public void gather() {
        resourcesInBag.put(assignedResource.getResourceType(), GATHERING_POWER);
        totalGatheredResources.put(assignedResource.getResourceType(), totalGatheredResources.getOrDefault(MINERAL, 0)  + GATHERING_POWER);
        hasGathered = true;
    }

    public void dropHarvest() {
        Symbol typeOfResource = assignedResource.getResourceType();
        int amountToBeCollected = resourcesInBag.get(typeOfResource);
        ownedBy.collectResources(typeOfResource, amountToBeCollected );
        hasGathered = false;
        resourcesInBag.put(typeOfResource, 0);
    }

    public int getGATHERING_POWER() {
        return GATHERING_POWER;
    }

    public boolean isAtGatheringSpot(){
        return getCurrentLocation().equals(pathToResource.get(pathToResource.size()-1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gatherer)) return false;
        Gatherer gatherer = (Gatherer) o;
        return Objects.equals(pathToResource, gatherer.pathToResource);
    }
}
