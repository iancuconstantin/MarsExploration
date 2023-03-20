package com.codecool.marsexploration.db.data;

import java.util.UUID;

public record DBRover(UUID ownedById, UUID id, int gathered_minerals, int gathered_water) {
}
