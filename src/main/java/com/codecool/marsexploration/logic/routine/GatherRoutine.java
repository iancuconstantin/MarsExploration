package com.codecool.marsexploration.logic.routine;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.rover.Gatherer;

public class GatherRoutine implements Routine {

    private Gatherer gatherer;

    public GatherRoutine(Gatherer gatherer) {
        this.gatherer = gatherer;
    }

    @Override
    public void move(Context context) {

        if(!gatherer.hasGathered()){
            if(!gatherer.getCurrentLocation().equals(
                    gatherer.getAssignedResource().getLocation())) {
                // cat timp nu ajunge la resource
                int currentTrackRecordIndex = gatherer.getCurrentTrackRecordIndex();
                Coordinate newPosition = gatherer.getPathToResource().get(currentTrackRecordIndex);
                gatherer.moveForward(newPosition);
            }else{
                gatherer.gather();
            }

        }else{
            if(!gatherer.getCurrentLocation().equals(gatherer.getOwnedBy().getLocation())){
                gatherer.moveBack();
            } else {
                gatherer.dropHarvest();
            }
        }

        //cand a ajuns la resursa ia materiale
        // cat timp nu a ajuns la baza
        // move backward
    }


}
