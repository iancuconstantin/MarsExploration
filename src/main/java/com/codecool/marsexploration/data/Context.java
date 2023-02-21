package com.codecool.marsexploration.data;

public record Context(
        Integer stepNumber,
        long timeout,
        Character[][] map,
        Coordinate landing,
        Rover rover,
        Outcome outcome,
        String logPath) {}