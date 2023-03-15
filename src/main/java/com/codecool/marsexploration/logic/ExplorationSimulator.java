package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.*;
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

public class ExplorationSimulator{
    private List<Phase> phases;

    public ExplorationSimulator(List<Phase> phases) {
        this.phases = phases;
    }

    public void simulate(SimulationInput input) {
        Context context = process(input);
        Phase logPhase = new LogPhase(new LogSaver());


        //try
//        CommandCentre commandCentre = new CommandCentre(new Coordinate(11, 13),context);
        CommandCentre commandCentre = new CommandCentre(new Coordinate(18, 12),context);
//        System.out.println(commandCentre.getLocation().x(),commandCentre.getLocation().y());
        Character[][] map = context.getMap();

        System.out.println("verificare locatie CC: ");
        System.out.println(commandCentre.getLocation());


        System.out.println("VERIFICARE getResourcesInSight");
        for(Coordinate resource: commandCentre.getResourcesInSight()){
            System.out.println(resource);
            System.out.println(map[resource.x()][resource.y()].toString());
        }

        System.out.println("VERIFICARE SPOTURI LIBERE prima resursa:");
        Coordinate resPsn = new Coordinate(commandCentre.getResourcesInSight().get(0).x(),commandCentre.getResourcesInSight().get(0).y());
        List<Coordinate> freeSpots = commandCentre.checkFreeSpotForRover(context,resPsn);
        for(Coordinate spot:freeSpots){
            System.out.println(spot);
        }

        System.out.println("verificare lista rovere in cc:");
        Random random = new Random();
        int randomIndex = random.nextInt(freeSpots.size());
        Coordinate randomItem = freeSpots.get(randomIndex);
        System.out.println("VERIFICARE PICK RANDOM COORDINATE:");
        System.out.println(randomItem.toString());

        System.out.println("VERIFICARE LUNGIME LISTA ROVERE IN CC- INCEPUT: ");
        System.out.println(commandCentre.getRovers().size());

        commandCentre.createNewRover(randomItem);

        System.out.println("VERIFICARE LUNGIME LISTA ROVERE IN CC- FINAL: ");
        System.out.println(commandCentre.getRovers().size());
        Map.Entry<Rover, Coordinate> firstEntry = commandCentre.getRovers().entrySet().iterator().next();
        UUID firstKey = firstEntry.getKey().getId();
        Coordinate firstValue = firstEntry.getValue();

        System.out.println("First rover in the cc is: " + firstKey + " => " + firstValue);
//        commandCentre.createNewRover(randomItem);
//        System.out.println("VERIFICARE LISTA ROVERE IN CC: ");
//        System.out.println(commandCentre.getRovers().keySet());
        //end try




        CheckLandingCoordonates checkLandingCoordonates = new CheckLandingCoordonates();
        if(checkLandingCoordonates.analyze(context).isPresent()){
            context.setOutcome(checkLandingCoordonates.analyze(context));
            logPhase.perform(context);
            return;
        }

        logPhase.perform(context);
        context.setStepNumber(context.getStepNumber() + 1);
        while (context.getOutcome().isEmpty()){
            for(Phase phase:phases){
                phase.perform(context);
            }
        }
        if (context.getOutcome().equals(Outcome.COLONIZABLE)){
            context.getRover().setState(new BuildingRoutine());
            //TODO
        }else{
            context.getRover().setState(new ReturningRoutine());
            while (!context.getRover().getCoordinate().equals(context.getLanding())){
                context.getRover().getState().move(context);
            }
        }
    }

    private Context process(SimulationInput input) {
        Routine state = new ExploringRoutine();
        Rover rover = new Rover(input.landing(),5,state);
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
