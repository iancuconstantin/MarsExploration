package com.codecool.marsexploration.utils;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.rover.Gatherer;
import com.codecool.marsexploration.logic.routine.BuildingRoutine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static com.codecool.marsexploration.data.Symbol.MINERAL;

public class LogSaver {

    private int gathererIndex = 1;

    public void logStep(Context context){
        String content;
        if(context.getCommandCentres().isEmpty()){
            if(!(context.getExplorer().getState() instanceof BuildingRoutine)){
                content = "STEP-" + context.getStepNumber() +
                        "; EVENT position; UNIT EXPLORING ROVER-" + context.getExplorer().getId() +
                        "; POSITION [" + context.getExplorer().getCurrentLocation().x() + "," + context.getExplorer().getCurrentLocation().y() + "]";
                if (context.getOutcome().isPresent()) {
                    content += "\nEVENT outcome; OUTCOME " + context.getOutcome().get().getStatusMessage();
                }
            } else {
                if (!context.getExplorer().getRouteToBuildingSpot().isEmpty()) {
                    content = "STEP-" + context.getStepNumber() +
                            "; EVENT position(move to building spot); UNIT EXPLORING ROVER-" + context.getExplorer().getId() +
                            "; POSITION [" + context.getExplorer().getCurrentLocation().x() + "," +context.getExplorer().getCurrentLocation().y() + "]";
                }else{
                    content = "STEP-" + context.getStepNumber() +
                            "; EVENT building; UNIT ROVER- " + context.getExplorer().getId() +  "; Building step - " +
                            context.getExplorer().getCurrentTrackRecordIndex() + "/" +
                            context.getExplorer().getSTEPS_NEEDED_FOR_CONSTRUCTION() +
                            "; COMMAND CENTRE LOCATION - " + context.getExplorer().getBuildCommandCentreSpot() +
                            "; RESOURCES USED - " + (20 - context.getExplorer().getStoredResources().get(MINERAL)) +
                            "/" + 20 +
                            "; Progress: " + ((20 - context.getExplorer().getStoredResources().get(MINERAL)) * 100 / 20.0) +"%";
                }
            }
        }else{
            gathererIndex = gathererIndex + 1 > context.getCommandCentres().get(0).getGatherers().size() - 1
                    ? 0
                    : gathererIndex + 1 ;
            Gatherer rover = context.getCommandCentres().get(0).getGatherers().get(gathererIndex);

            if (rover.isAtGatheringSpot()){
                content = "STEP-" + context.getStepNumber() +
                        "; EVENT gathering; UNIT GATHERING ROVER-" + context.getCommandCentres().get(0).getGatherers().get(gathererIndex).getId()+
                        "; RESOURCES GATHERED - TYPE: " + context.getCommandCentres().get(0).getGatherers().get(gathererIndex).getAssignedResource().getResourceType().getSymbol() +
                        " / AMOUNT - " + context.getCommandCentres().get(0).getGatherers().get(gathererIndex).getGATHERING_POWER() +
                         "; POSITION [" + context.getCommandCentres().get(0).getGatherers().get(gathererIndex).getCurrentLocation().x() + "," + context.getCommandCentres().get(0).getGatherers().get(gathererIndex).getCurrentLocation().y() + "]";

            }else if (rover.hasGathered()){
                content = "ROVER MOVING BACK TO BASE;" + rover.getCurrentLocation();
            }else{
                content = "ROVER MOVING TOWARDS GATHERING LOCATION;" + rover.getCurrentLocation();
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