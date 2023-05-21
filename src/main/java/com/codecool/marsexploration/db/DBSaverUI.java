package com.codecool.marsexploration.db;

import com.codecool.marsexploration.data.CommandCentre;
import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.Symbol;
import com.codecool.marsexploration.data.rover.Gatherer;
import com.codecool.marsexploration.db.data.DBCommandCentre;
import com.codecool.marsexploration.db.data.DBConstruction;
import com.codecool.marsexploration.db.data.DBRover;
import com.codecool.marsexploration.db.database.CommandCentreRepository;
import com.codecool.marsexploration.db.database.ConstructionRepository;
import com.codecool.marsexploration.db.database.RoverRepository;

import java.util.List;
import java.util.Map;

import static com.codecool.marsexploration.data.Symbol.MINERAL;
import static com.codecool.marsexploration.data.Symbol.WATER;

public class DBSaverUI {

    private final CommandCentreRepository commandCentreRepository;
    private final ConstructionRepository constructionRepository;
    private final RoverRepository roverRepository;
    private final Context context;

    public DBSaverUI(CommandCentreRepository commandCentreRepository, ConstructionRepository constructionRepository, RoverRepository roverRepository, Context context) {
        this.commandCentreRepository = commandCentreRepository;
        this.constructionRepository = constructionRepository;
        this.roverRepository = roverRepository;
        this.context = context;
    }

    public void run(){
        List<CommandCentre> commandCentres = context.getCommandCentres();

        for(CommandCentre cmd : commandCentres){
            int totalMinerals = 0;
            int totalWater = 0;
            for (Gatherer gatherer : cmd.getGatherers()) {
                Map<Symbol, Integer> totalGatheredResources = gatherer.getTotalGatheredResources();
                totalMinerals += totalGatheredResources.getOrDefault(MINERAL, 0);
                totalWater += totalGatheredResources.getOrDefault(WATER, 0);
            }
            commandCentreRepository.save(
                    new DBCommandCentre(
                            cmd.getId(),
                            cmd.getLocation().x(),
                            cmd.getLocation().y(),
                            cmd.getResourcesInSight().size(),
                            totalMinerals,
                            totalWater
                    )
            );
            for (Gatherer gatherer : cmd.getGatherers()){
                roverRepository.save(
                        new DBRover(
                                gatherer.getOwnedBy().getId(),
                                gatherer.getId(),
                                gatherer.getTotalGatheredResources().get(MINERAL),
                                gatherer.getTotalGatheredResources().get(WATER)
                        )
                );
            }

            constructionRepository.save(
                    new DBConstruction(
                            cmd.getId(),
                            cmd.getGatherers().size() * 20
                    )
            );
        }
    }


}
