package com.codecool.marsexploration.utils;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.rover.Explorer;
import com.codecool.marsexploration.data.rover.Gatherer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static com.codecool.marsexploration.utils.LogMessageCreator.*;

public class LogSaver {

    private int gathererIndex = 0;

    public void logStep(Context context){
        String content ="";
        Explorer explorer = context.getExplorer();
        if(context.noCommandCentreAvailable()){

            if(!explorer.isOnBuildingRoutine()){
                content = createExplorerPositionLogMessage( context.getStepNumber(),explorer);

                if (context.getOutcome().isPresent()) {
                    content += createFoundOutcomeLogMessage(context.getOutcome().get().getStatusMessage());
                }
            } else {
                if (!explorer.isBuilding()) {
                    content = createExplorerMoveToBuildingSpotLogMessage(context.getStepNumber(), explorer);
                } else {
                    content = createExplorerBuildingLogMessage(context.getStepNumber(), explorer);
                }
            }
        }else{
            Gatherer rover = context.getCommandCentres().get(0).getGatherers().get(gathererIndex);
            gathererIndex = gathererIndex + 1 > context.getCommandCentres().get(0).getGatherers().size() - 1
                    ? 0
                    : gathererIndex + 1;

            if (rover.isAtGatheringSpot() && rover.hasGathered()) {
                content = createGatheringLogMessage(context.getStepNumber(),  rover);
            } else if(rover.isAtCommandCentreSpot() && rover.hasGathered()) {
                content = createDeliveringResourceLogMessage(context.getStepNumber(), rover);
            } else if (rover.hasGathered() && !rover.isAtCommandCentreSpot()) {
                    content = createGathererReturnToBaseLogMessage(context.getStepNumber(), rover);
            } else{
                content = createMoveToGatheringSpotLogMessage(context.getStepNumber(),rover);
            }

        }

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(context.getLogPath(), true))){
            bw.append(content);
            bw.newLine();
        }catch(IOException e){
            System.out.println("An error occurred while trying to write the file.\n Error message: " + e.getMessage());
        }
    }


}