package com.codecool.marsexploration.db.initialize;

public interface TableStatements {

    String ROVER = "CREATE TABLE rover (ownedById UUID NOT NULL, id UUID PRIMARY KEY, gathered_minerals INTEGER NOT NULL, gathered_water INTEGER NOT NULL );";

    String COMMAND_CENTRE = "CREATE TABLE command_centre (id UUID PRIMARY KEY, location_x INTEGER NOT NULL, location_y INTEGER NOT NULL, number_of_resources_in_sight INTEGER NOT NULL, stored_minerals INTEGER NOT NULL, stored_water INTEGER NOT NULL);";

    String CONSTRUCTION = "CREATE TABLE construction (id UUID PRIMARY KEY, minerals_used_for_building_rovers INTEGER NOT NULL); ";
}
