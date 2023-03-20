package com.codecool.marsexploration.data.rover;

import com.codecool.marsexploration.data.*;
import com.codecool.marsexploration.logic.routine.GatherRoutine;
import com.codecool.marsexploration.logic.routine.Routine;
import com.codecool.marsexploration.utils.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codecool.marsexploration.data.Symbol.MINERAL;

public class Gatherer extends Rover {
    private Map<Symbol, Integer> totalGatheredResources;
    private IdentifiedResource assignedResource;
    private final Map<Symbol, Integer>  resourcesInBag;
    private List<Coordinate> pathToResource;
    private final CommandCentre ownedBy;
    private int GATHERING_POWER = 1 ;

    public Gatherer(CommandCentre ownedBy) {
        super(ownedBy.getLocation());
        Routine routine = new GatherRoutine(this);
        setState(routine);
        this.ownedBy = ownedBy;
        this.assignedResource = null;
        this.pathToResource = null;
        this.resourcesInBag = new HashMap<>();
        this.totalGatheredResources = initTotalGatheredResources();
        //setCurrentTrackRecordIndex(1);
    }

    public void dropHarvest(Context context) {
        Symbol typeOfResource = assignedResource.getResourceType();
        int amountToBeCollected = resourcesInBag.get(typeOfResource);
        ownedBy.collectResources(typeOfResource, amountToBeCollected);
        resourcesInBag.put(typeOfResource, 0);
    }

    public void setAssignedResourceAndInitializeData(IdentifiedResource assignedResource, Character[][] mapPlacedOn) {
        this.assignedResource = assignedResource;
        this.pathToResource = MapUtils.getShortestRoute(
                mapPlacedOn,
                ownedBy.getLocation(),
                assignedResource.getLocation()
        );
        initBag();
    }

    public boolean isAtGatheringSpot(){
        return getCurrentLocation().x() == assignedResource.getLocation().x() &&
                getCurrentLocation().y() == assignedResource.getLocation().y();
    }

    public boolean isAtCommandCentreSpot(){
        return getCurrentLocation().x() == ownedBy.getLocation().x() &&
                getCurrentLocation().y() == ownedBy.getLocation().y();
    }

    public void gather() {
        resourcesInBag.put(assignedResource.getResourceType(), GATHERING_POWER);
        totalGatheredResources.put(assignedResource.getResourceType(), totalGatheredResources.get(MINERAL)  + GATHERING_POWER);
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

    public boolean hasGathered() {
        return totalGatheredResources.get(assignedResource.getResourceType()) - resourcesInBag.get(assignedResource.getResourceType())
                ==
                totalGatheredResources.get(assignedResource.getResourceType()) - GATHERING_POWER;
    }

    public int getGATHERING_POWER() {
        return GATHERING_POWER;
    }

    private void initBag() {
        resourcesInBag.put(assignedResource.getResourceType(), 0);
    }

    private Map<Symbol, Integer> initTotalGatheredResources() {
        Map<Symbol, Integer> totalStored = new HashMap<>();
        totalStored.put(MINERAL, 0);
        totalStored.put(Symbol.WATER, 0);

        return totalStored;
    }
}
