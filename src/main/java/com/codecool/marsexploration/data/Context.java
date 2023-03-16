package com.codecool.marsexploration.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Context {
    private final int REQUIRED_MINERALS_FOR_NEW_ROVER = 10;
    private final int REQUIRED_MINERALS_FOR_NEW_COMMAND_CENTRE = 20;
    private Integer stepNumber;
    private long timeout;
    private Character[][] map;
    private Coordinate landing;
    private Rover rover;
    private String logPath;
    private Optional<Outcome> outcome = Optional.empty();
    private List<CommandCentre> commandCentres;
    private final int COMMAND_CENTRE_SIGHT = 5;
    public int currentStepsInConstruction = 0;
    public int stepsNeededForConstruction = 4;

    public Context(Integer stepNumber, long timeout, Character[][] map, Coordinate landing, Rover rover, String logPath) {
        this.stepNumber = stepNumber;
        this.timeout = timeout;
        this.map = map;
        this.landing = landing;
        this.rover = rover;
        this.logPath = logPath;
        this.commandCentres = new ArrayList<>();
    }

    public List<CommandCentre> getCommandCentres() {
        return commandCentres;
    }

    public void incrementStepNumber(){
        stepNumber++;
    }

    public int getCOMMAND_CENTRE_SIGHT() {
        return COMMAND_CENTRE_SIGHT;
    }

    public void addCommandCenter(CommandCentre commandCentre) {
        commandCentres.add(commandCentre);
    }

    public Integer getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(Integer stepNumber) {
        this.stepNumber = stepNumber;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public Character[][] getMap() {
        return map;
    }

    public void setMap(Character[][] map) {
        this.map = map;
    }

    public Coordinate getLanding() {
        return landing;
    }

    public void setLanding(Coordinate landing) {
        this.landing = landing;
    }

    public Rover getRover() {
        return rover;
    }

    public void setRover(Rover rover) {
        this.rover = rover;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public Optional<Outcome> getOutcome() {
        return outcome;
    }

    public void setOutcome(Optional<Outcome> outcome) {
        this.outcome = outcome;
    }
}