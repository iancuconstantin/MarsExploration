package com.codecool.marsexploration.db.database;

import com.codecool.marsexploration.db.data.DBRover;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class RoverRepository {

    private final Database database;

    public RoverRepository(Database database) {
        this.database = database;
    }

    public Optional<DBRover> findOneById(String id){
        String query = "SELECT * FROM rover WHERE id::text LIKE ?";
        try(Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            Optional<DBRover> rover = Optional.empty();
            if(resultSet.next()){
                rover = Optional.of(toEntity(resultSet));
            }
            resultSet.close();
            return rover;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    private DBRover toEntity(ResultSet resultSet) throws SQLException {
        UUID ownedById = (UUID) resultSet.getObject("ownedById");
        UUID id = (UUID) resultSet.getObject("id");
        int gatheredMinerals = resultSet.getInt("gathered_minerals");
        int gatheredWater = resultSet.getInt("gathered_water");

        return new DBRover(ownedById, id, gatheredMinerals, gatheredWater);
    }

    public void save(DBRover rover){
        if (findOneById(rover.id().toString()).isPresent()){
            return;
        }
        String query = "INSERT INTO rover(ownedById, id, gathered_minerals, gathered_water) VALUES(?, ?, ?, ?)";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)){
            prepare(rover, statement);
            statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void prepare(DBRover rover, PreparedStatement statement) throws  SQLException {
        statement.setObject(1, UUID.fromString(rover.ownedById().toString()), Types.OTHER);
        statement.setObject(2, UUID.fromString(rover.id().toString()), Types.OTHER);
        statement.setInt(3,rover.gathered_minerals());
        statement.setInt(4, rover.gathered_water());
    }
}

