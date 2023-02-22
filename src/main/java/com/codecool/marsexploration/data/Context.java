package com.codecool.marsexploration.data;

import java.util.Optional;

public class Context {
    private Integer stepNumber;
    private long timeout;
    private Character[][] map;
    private Coordinate landing;
    private Rover rover;
    private String logPath;
    private Optional<Outcome> outcome = Optional.empty();

    public Context(Integer stepNumber, long timeout, Character[][] map, Coordinate landing, Rover rover, String logPath) {
        this.stepNumber = stepNumber;
        this.timeout = timeout;
        this.map = map;
        this.landing = landing;
        this.rover = rover;
        this.logPath = logPath;
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