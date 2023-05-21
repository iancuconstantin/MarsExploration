package com.codecool.marsexploration;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.SimulationInput;
import com.codecool.marsexploration.db.DBSaverUI;
import com.codecool.marsexploration.db.database.CommandCentreRepository;
import com.codecool.marsexploration.db.database.ConstructionRepository;
import com.codecool.marsexploration.db.database.Database;
import com.codecool.marsexploration.db.database.RoverRepository;
import com.codecool.marsexploration.db.initialize.TableInitializer;
import com.codecool.marsexploration.db.initialize.TableStatements;
import com.codecool.marsexploration.logic.ExplorationSimulator;
import com.codecool.marsexploration.logic.analyzer.Analyzer;
import com.codecool.marsexploration.logic.analyzer.LackOfResourcesAnalyzer;
import com.codecool.marsexploration.logic.analyzer.SuccessAnalyzer;
import com.codecool.marsexploration.logic.analyzer.TimeOutAnalizer;
import com.codecool.marsexploration.logic.phase.*;
import com.codecool.marsexploration.utils.LogSaver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Application {
    public static void main(String[] args) {

        Database database = new Database(
                "jdbc:postgresql://localhost:5432/mars_exploration",
                "postgres",
                "postgres");

        Map<String, String> tables = Map.of(
                "rover", TableStatements.ROVER,
                "command_centre", TableStatements.COMMAND_CENTRE,
                "construction", TableStatements.CONSTRUCTION
        );

        TableInitializer tableInitializer = new TableInitializer(database, tables);
        tableInitializer.initialize();

        RoverRepository roverRepository = new RoverRepository(database);
        CommandCentreRepository commandCentreRepository = new CommandCentreRepository(database);
        ConstructionRepository constructionRepository = new ConstructionRepository(database);

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
                "src/main/resources/sprint3-3.map",
                new Coordinate(19, 9), // 19-9,20-18(plenty)29/2(5 only - short) - 24/12 (wrong)- 29/29(timeout) -
                1000,
                "src/main/resources/exploration-1111.log");

        Context context = simulator.simulate(input);
        if (context != null){
            DBSaverUI dbSaverUI = new DBSaverUI(commandCentreRepository, constructionRepository, roverRepository,context);
            dbSaverUI.run();;
        }

    }
}
