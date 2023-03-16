package com.codecool.marsexploration.db.database;

import com.codecool.marsexploration.db.data.DBCommandCentre;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class CommandCentreRepository {
    private final Database database;

    public CommandCentreRepository(Database database) {
        this.database = database;
    }

    public Optional<DBCommandCentre> findOneById(String id){
        String query = "SELECT * FROM command_centre WHERE id::text LIKE ?";
        try(Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            Optional<DBCommandCentre> commandCentre = Optional.empty();
            if(resultSet.next()){
                commandCentre = Optional.of(toEntity(resultSet));
            }
            resultSet.close();
            return commandCentre;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    private DBCommandCentre toEntity(ResultSet resultSet) throws SQLException {
        UUID id = (UUID) resultSet.getObject("id");
        int landingX = resultSet.getInt("landing_x");
        int landingY = resultSet.getInt("landing_y");
        int resourcesInSight = resultSet.getInt("number_of_resources_in_sight");
        int storedMinerals = resultSet.getInt("stored_minerals");
        int storedWater = resultSet.getInt("stored_water");

        return new DBCommandCentre(id, landingX, landingY,resourcesInSight, storedMinerals, storedWater);
    }

    public void save(DBCommandCentre commandCentre){
        if (findOneById(commandCentre.id().toString()).isPresent()){
            return;
        }
        String query = "INSERT INTO command_centre(id, location_x, location_y, number_of_resources_in_sight, stored_minerals, stored_water) VALUES(?, ? , ?, ?, ?, ?)";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)){
            prepare(commandCentre, statement);
            statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void prepare(DBCommandCentre commandCentre, PreparedStatement statement) throws  SQLException {
        statement.setObject(1, UUID.fromString(commandCentre.id().toString()), Types.OTHER);
        statement.setInt(2,commandCentre.location_x());
        statement.setInt(3,commandCentre.location_y());
        statement.setInt(4,commandCentre.number_of_resources_in_sight());
        statement.setInt(5,commandCentre.stored_minerals());
        statement.setInt(6,commandCentre.stored_water());
    }
}
