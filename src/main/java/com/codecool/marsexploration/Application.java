package com.codecool.marsexploration;

import com.codecool.marsexploration.data.*;
import com.codecool.marsexploration.logic.ExplorationSimulator;
import com.codecool.marsexploration.logic.analyzer.Analyzer;
import com.codecool.marsexploration.logic.analyzer.LackOfResourcesAnalyzer;
import com.codecool.marsexploration.logic.analyzer.SuccessAnalyzer;
import com.codecool.marsexploration.logic.analyzer.TimeOutAnalizer;
import com.codecool.marsexploration.logic.phase.*;
import com.codecool.marsexploration.utils.LogSaver;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {

        List<Analyzer> analyzerList = new ArrayList<>();
            analyzerList.add(new TimeOutAnalizer());
            analyzerList.add(new SuccessAnalyzer());
            analyzerList.add(new LackOfResourcesAnalyzer());

        List<Phase> phases= new ArrayList<>();
            phases.add(new MovementPhase());
            phases.add(new ScanPhase());
            phases.add(new AnalysisPhase(analyzerList));
            phases.add(new LogPhase(new LogSaver()));
            phases.add(new StepIncrementPhase());



        ExplorationSimulator simulator = new ExplorationSimulator(phases);
        SimulationInput input = new SimulationInput(
                "src/main/resources/exploration-1.map",
                new Coordinate(12, 12),
                100,
                "src/main/resources/exploration-1.log");

        simulator.simulate(input);
    }
}
