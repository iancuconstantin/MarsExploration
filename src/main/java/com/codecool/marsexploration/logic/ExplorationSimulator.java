package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.*;
import com.codecool.marsexploration.data.rover.Explorer;
import com.codecool.marsexploration.data.rover.Gatherer;
import com.codecool.marsexploration.logic.analyzer.CheckLandingCoordonates;
import com.codecool.marsexploration.logic.phase.LogPhase;
import com.codecool.marsexploration.logic.phase.Phase;
import com.codecool.marsexploration.logic.routine.BuildingRoutine;
import com.codecool.marsexploration.logic.routine.ExploringRoutine;
import com.codecool.marsexploration.logic.routine.ReturningRoutine;
import com.codecool.marsexploration.logic.routine.Routine;
import com.codecool.marsexploration.utils.LogSaver;
import com.codecool.marsexploration.utils.ReadFile;

import java.util.*;

import static com.codecool.marsexploration.data.Symbol.MINERAL;
import static com.codecool.marsexploration.data.Symbol.WATER;

public class ExplorationSimulator{
    private List<Phase> phases;

    public ExplorationSimulator(List<Phase> phases) {
        this.phases = phases;
    }

    public Context simulate(SimulationInput input) {
        Context context = process(input);
        Phase logPhase = new LogPhase(new LogSaver());

        CheckLandingCoordonates checkLandingCoordonates = new CheckLandingCoordonates();
        if(checkLandingCoordonates.analyze(context).isPresent()){
            context.setOutcome(checkLandingCoordonates.analyze(context));
            logPhase.perform(context);
            return null;
        }

        logPhase.perform(context);
        context.setStepNumber(context.getStepNumber() + 1);

        while (context.getOutcome().isEmpty()){
            for(Phase phase:phases){
                phase.perform(context);
            }
        }

        if (context.getOutcome().orElse(null).getStatusMessage().equals(Outcome.COLONIZABLE.getStatusMessage())){
            context.getExplorer().setState(new BuildingRoutine());
            int commandCentresAvailable = context.getCommandCentres().size();
            while(context.getCommandCentres().isEmpty()){
                    context.getExplorer().getState().move(context);
                    logPhase.perform(context);
                    context.incrementStepNumber();
            }

            while(context.getCommandCentres().get(0).getResourceInventory().get(MINERAL) + context.getCommandCentres().get(0).getResourceInventory().get(WATER) != 50){
                for (Gatherer gatherer : context.getCommandCentres().get(0).getGatherers()){
                    gatherer.getState().move(context);
                    logPhase.perform(context);
                    context.incrementStepNumber();
                }
            }

        } else {
            context.getExplorer().setState(new ReturningRoutine());
            while (!context.getExplorer().getCurrentLocation().equals(context.getLanding())){
                context.getExplorer().getState().move(context);
            }
        }

        return context;
    }

    private Context process(SimulationInput input) {
        Routine state = new ExploringRoutine();
        Explorer rover = new Explorer(input.landing(),state,5);
        return new Context(0,100,getMap(input.mapPath()),input.landing(),rover,input.logPath());
    }

    private Character[][] getMap(String mapPath) {
        ReadFile readFile = new ReadFile();
        String file = readFile.readFile(mapPath);
        String[] mapRows = file.split("\n");
        int mapLength = mapRows.length;
        Character[][] map = new Character[mapLength][mapLength];

        for(int i=0; i<mapLength; i++){
            String[] rowSymbols = mapRows[i].split("");
            for(int j=0; j<mapLength; j++){
                map[i][j] = rowSymbols[j].charAt(0);
            }
        }
        return map;
    }
}
