package com.codecool.marsexploration.logic.phase;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.Symbol;
import com.codecool.marsexploration.data.rover.Explorer;

import java.util.Arrays;

public class ScanPhase implements Phase {

    private static final int MIN_INDEX = 0;

    @Override
    public void perform(Context context) {
        Coordinate[] sightBounds = getSightBounds(context);

        for (int i = sightBounds[0].x(); i < sightBounds[1].x(); i++) {
            for (int j = sightBounds[0].y(); j < sightBounds[1].y(); j++) {
                Coordinate currentCoordinate = new Coordinate(i, j);
                if (isSymbolInSight(currentCoordinate, context)) {
                    addSighting(currentCoordinate,context);
                }
            }
        }
    }

    private Coordinate[] getSightBounds(Context context) {
        Coordinate roverPsn = context.getExplorer().getCurrentLocation();
        int sight = context.getExplorer().getSight();

        int maxX = Math.min((roverPsn.x() + sight), context.getMap().length - 1);
        int minX = Math.max((roverPsn.x() - sight), MIN_INDEX);
        int maxY = Math.min((roverPsn.y() + sight), context.getMap()[0].length - 1);
        int minY = Math.max((roverPsn.y() - sight), MIN_INDEX);

        return new Coordinate[]{new Coordinate(minX, minY), new Coordinate(maxX, maxY)};
    }

    private boolean isSymbolInSight(Coordinate coordinate, Context context) {
        Character[][] map = context.getMap();
        String currentSymbol = map[coordinate.x()][coordinate.y()].toString();
        return Arrays.stream(Symbol.values())
                .anyMatch(symbol -> symbol.getSymbol().equals(currentSymbol));
    }

    private void addSighting(Coordinate coordinate, Context context) {
        Explorer rover = context.getExplorer();
        Character[][] map = context.getMap();
        String currentSymbol = map[coordinate.x()][coordinate.y()].toString();
        rover.getSightings().put(coordinate, currentSymbol);
    }
}
