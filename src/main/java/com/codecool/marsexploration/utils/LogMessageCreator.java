package com.codecool.marsexploration.utils;

import com.codecool.marsexploration.data.rover.Explorer;
import com.codecool.marsexploration.data.rover.Gatherer;

import static com.codecool.marsexploration.data.Symbol.MINERAL;

public class LogMessageCreator

{
	public static String createExplorerPositionLogMessage(int stepNumber, Explorer explorer){
		return  "STEP-" + stepNumber +
				"; EVENT position; UNIT EXPLORING ROVER-" + explorer.getId() +
				"; POSITION [" + explorer.getCurrentLocation().x() + "," + explorer.getCurrentLocation().y() + "]";
	}

	public static  String createFoundOutcomeLogMessage(String message){
		return "\nEVENT outcome; OUTCOME " + message;
	}

	public static String createExplorerMoveToBuildingSpotLogMessage(int stepNumber, Explorer explorer){
		return "STEP-" + stepNumber +
				"; EVENT position(move to building spot); UNIT EXPLORING ROVER-" + explorer.getId() +
				"; POSITION [" + explorer.getCurrentLocation().x() + "," +explorer.getCurrentLocation().y() + "]";
	}

	public static String createExplorerBuildingLogMessage(int stepNumber, Explorer explorer){
		return "STEP-" + stepNumber +
				"; EVENT building; UNIT ROVER- " + explorer.getId() +  "; Building step - " +
				explorer.getCurrentStepsInConstruction() + "/" +
				explorer.getSTEPS_NEEDED_FOR_CONSTRUCTION() +
				"; COMMAND CENTRE LOCATION - " + explorer.getBuildCommandCentreSpot() +
				"; RESOURCES USED - " + (60 - explorer.getStoredResources().get(MINERAL)) +
				"/" + 20 +
				"; Progress: " + (((60 - explorer.getStoredResources().get(MINERAL)) * 100) / 20.0) +"%";
	}

	public static String createGatheringLogMessage(int stepNumber, Gatherer gatherer){

		return "STEP-" + stepNumber +
				"; EVENT gathering; UNIT GATHERING ROVER-" + gatherer.getId() +
				"; RESOURCES GATHERED - TYPE: " + gatherer.getAssignedResource().getResourceType().getSymbol() +
				" / AMOUNT - " + gatherer.getGATHERING_POWER() +
				"; POSITION [" + gatherer.getCurrentLocation().x() + "," + gatherer.getCurrentLocation().y() + "]";

	};

	public static String createDeliveringResourceLogMessage(int stepNumber, Gatherer gatherer){
		return  "STEP-" + stepNumber +
				"; EVENT delivering; UNIT GATHERING ROVER-" + gatherer.getId() +
				"; RESOURCES GATHERED - TYPE: " + gatherer.getAssignedResource().getResourceType().getSymbol() +
				" / AMOUNT - " + gatherer.getGATHERING_POWER() +
				"; POSITION [" +gatherer.getCurrentLocation().x() + "," +gatherer.getCurrentLocation().y() + "]";
	}

	public static String createGathererReturnToBaseLogMessage(int stepNumber, Gatherer gatherer){
		return  "STEP-" + stepNumber +
				"; EVENT position (returning home); UNIT GATHERING ROVER-" + gatherer.getId() +
				"; POSITION [" + gatherer.getCurrentLocation().x() + "," + gatherer.getCurrentLocation().y() + "]";

	}

	public static String createMoveToGatheringSpotLogMessage(int stepNumber, Gatherer gatherer){
		return "STEP-" + stepNumber +
				"; EVENT position (move to gathering spot); UNIT GATHERING ROVER-" + gatherer.getId() +
				"; POSITION [" + gatherer.getCurrentLocation().x() + "," + gatherer.getCurrentLocation().y() + "]";
	}
}
