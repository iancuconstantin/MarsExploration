package com.codecool.marsexploration.data;

public class IdentifiedResource {

    private Symbol resourceType;

    private Coordinate location;

    private boolean isAvailableToBeAssigned;

    public IdentifiedResource(Symbol resourceType, Coordinate location) {
        this.resourceType = resourceType;
        this.location = location;
        this.isAvailableToBeAssigned = true;
    }

    public Symbol getResourceType() {
        return resourceType;
    }

    public void setResourceType(Symbol resourceType) {
        this.resourceType = resourceType;
    }

    public Coordinate getLocation() {
        return location;
    }

    public void setLocation(Coordinate location) {
        this.location = location;
    }

    public boolean isAvailableToBeAssigned() {
        return isAvailableToBeAssigned;
    }

    public void setAvailableToBeAssigned(boolean availableToBeAssigned) {
        isAvailableToBeAssigned = availableToBeAssigned;
    }
}
