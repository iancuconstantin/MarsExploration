package com.codecool.marsexploration.data;

import com.codecool.marsexploration.logic.routine.Routine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record Rover(
        int id,
        Coordinate coordinate,
        int sight,
        Routine state,
        List<Coordinate> trackRecord,
        Map<Coordinate, String> sightings) {}