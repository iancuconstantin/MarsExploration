package com.codecool.marsexploration.db;

import com.codecool.marsexploration.data.*;
import com.codecool.marsexploration.db.data.DBCommandCentre;
import com.codecool.marsexploration.db.data.DBConstruction;
import com.codecool.marsexploration.db.data.DBRover;
import com.codecool.marsexploration.db.database.CommandCentreRepository;
import com.codecool.marsexploration.db.database.ConstructionRepository;
import com.codecool.marsexploration.db.database.RoverRepository;

import java.util.List;
import java.util.Map;

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
//        List<CommandCentre> commandCentres = context.getCommandCentres();
//
//        for(CommandCentre cmd : commandCentres){
//            commandCentreRepository.save(
//                    new DBCommandCentre(
//                            cmd.getId(),
//                            cmd.getLocation().x(),
//                            cmd.getLocation().y(),
//                            cmd.getResourcesInSight().size(),
//                            cmd.getQuantityStored().get(Symbol.MINERAL),
//                            cmd.getQuantityStored().get(Symbol.WATER)
//                    )
//            );
//            for (Map.Entry<Rover, Coordinate> entry : cmd.getRovers().entrySet()){
//                Rover rover = entry.getKey();
//                roverRepository.save(
//                        new DBRover(
//                                rover.getId(),
//                                rover.getTotalGatheredResources().get(Symbol.MINERAL),
//                                rover.getTotalGatheredResources().get(Symbol.WATER)
//                        )
//                );
//            }
//
//            constructionRepository.save(
//                    new DBConstruction(
//                            cmd.getId(),
//                            cmd.getRovers().size() * 20
//                    )
//            );
//        }
    }


}
