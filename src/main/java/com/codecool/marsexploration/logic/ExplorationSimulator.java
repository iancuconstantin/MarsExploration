package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.*;
import com.codecool.marsexploration.logic.analyzer.CheckLandingCoordonates;
import com.codecool.marsexploration.logic.phase.LogPhase;
import com.codecool.marsexploration.logic.phase.Phase;
import com.codecool.marsexploration.logic.routine.ExploringRoutine;
import com.codecool.marsexploration.logic.routine.ReturningRoutine;
import com.codecool.marsexploration.logic.routine.Routine;
import com.codecool.marsexploration.utils.LogSaver;
import com.codecool.marsexploration.utils.ReadFile;

import java.util.*;

public class ExplorationSimulator {
    private List<Phase> phases;

    public ExplorationSimulator(List<Phase> phases) {
        this.phases = phases;
    }

    public void simulate(SimulationInput input) {
        Context context = process(input);
        Phase logPhase = new LogPhase(new LogSaver());
        boolean isLandingSpotLogged = false;

        CheckLandingCoordonates checkLandingCoordonates = new CheckLandingCoordonates();
        if(checkLandingCoordonates.analyze(context).isPresent()){
            context.setOutcome(checkLandingCoordonates.analyze(context));
            logPhase.perform(context);
            return;
        }

        while (context.getOutcome().isEmpty()){
            if (!isLandingSpotLogged){
                logPhase.perform(context);
                context.setStepNumber(context.getStepNumber() + 1);
                isLandingSpotLogged = true;
            }
            for(Phase phase:phases){
                phase.perform(context);
            }
        }

        context.getRover().setState(new ReturningRoutine());
        while (!context.getRover().getCoordinate().equals(context.getLanding())){
            context.getRover().getState().move(context);
        }
    }

    private Context process(SimulationInput input) {
        List<Coordinate> trackRecord = new ArrayList<>();
        Map<Coordinate,String> sightings = new HashMap<>();
        Routine state = new ExploringRoutine();
        Rover rover = new Rover(42,input.landing(),5,state,trackRecord,sightings);
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
