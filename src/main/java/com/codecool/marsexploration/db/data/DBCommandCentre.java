package com.codecool.marsexploration.db.data;

import java.util.UUID;

public record DBCommandCentre(UUID id, int location_x, int location_y, int number_of_resources_in_sight, int stored_minerals, int stored_water) {

}
