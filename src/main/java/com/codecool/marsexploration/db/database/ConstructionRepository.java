package com.codecool.marsexploration.db.database;

import com.codecool.marsexploration.db.data.DBConstruction;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class ConstructionRepository {
    private final Database database;

    public ConstructionRepository(Database database) {
        this.database = database;
    }

    public Optional<DBConstruction> findOneById(String id){
        String query = "SELECT * FROM construction WHERE id::text LIKE ?";
        try(Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            Optional<DBConstruction> construction = Optional.empty();
            if(resultSet.next()){
                construction = Optional.of(toEntity(resultSet));
            }
            resultSet.close();
            return construction;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    private DBConstruction toEntity(ResultSet resultSet) throws SQLException {
        UUID id = (UUID) resultSet.getObject("id");
        int mineralsUsedForBuildingRovers  = resultSet.getInt("minerals_used_for_building_rovers");

        return new DBConstruction(id, mineralsUsedForBuildingRovers);
    }

    public void save(DBConstruction construction){
        if (findOneById(construction.id().toString()).isPresent()){
            return;
        }
        String query = "INSERT INTO construction(id, minerals_used_for_building_rovers) VALUES(?, ?)";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)){
            prepare(construction, statement);
            statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void prepare(DBConstruction construction, PreparedStatement statement) throws  SQLException {
        statement.setObject(1, UUID.fromString(construction.id().toString()), Types.OTHER);
        statement.setInt(2,construction.minerals_used_for_building_rovers());
    }
}
