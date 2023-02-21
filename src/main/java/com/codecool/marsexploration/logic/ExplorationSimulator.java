package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.Rover;
import com.codecool.marsexploration.data.SimulationInput;
import com.codecool.marsexploration.logic.routine.ExploringRoutine;
import com.codecool.marsexploration.logic.routine.Routine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExplorationSimulator {

    public void simulate(SimulationInput input) {

        Context context = process(input);

//        input.mapPath();
//        input.landing();
//        input.timeout();
//        input.logPath();
    }

    private Context process(SimulationInput input) {
        List<Coordinate> trackRecord = new ArrayList<>();
        Map<Coordinate,String> sightings = new HashMap<>();
        Routine state = new ExploringRoutine();
        Rover rover = new Rover(42,input.landing(),10,state,trackRecord,sightings);
        return new Context(0,100,getMap(),input.landing(),rover,input.logPath());
    }

}
