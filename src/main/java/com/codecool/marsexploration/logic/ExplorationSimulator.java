package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.Rover;
import com.codecool.marsexploration.data.SimulationInput;
import com.codecool.marsexploration.logic.phase.Phase;
import com.codecool.marsexploration.logic.routine.ExploringRoutine;
import com.codecool.marsexploration.logic.routine.Routine;
import com.codecool.marsexploration.utils.ReadFile;

import java.util.*;

public class ExplorationSimulator {
    private List<Phase> phases;

    public ExplorationSimulator(List<Phase> phases) {
        this.phases = phases;
    }


    public void simulate(SimulationInput input) {
        Context context = process(input);
        for(Phase phase:phases){
            phase.perform(context);
        }
    }

    private Context process(SimulationInput input) {
        List<Coordinate> trackRecord = new ArrayList<>();
        Map<Coordinate,String> sightings = new HashMap<>();
        Routine state = new ExploringRoutine();
        Rover rover = new Rover(42,input.landing(),10,state,trackRecord,sightings);
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
