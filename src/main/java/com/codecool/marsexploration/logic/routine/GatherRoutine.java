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
            if(gatherer.isAtGatheringSpot()) {
                gatherer.gather();
            }else{
                int currentTrackRecordIndex = gatherer.getCurrentTrackRecordIndex();
                Coordinate newPosition = gatherer.getPathToResource().get(currentTrackRecordIndex);
                gatherer.moveForward(newPosition);
            }

        }else{
            if(gatherer.isAtCommandCentreSpot()){
                gatherer.dropHarvest(context);
            } else {
                gatherer.moveBack();
            }
        }
    }


}
