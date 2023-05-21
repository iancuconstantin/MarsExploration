package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.CommandCentre;
import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.Outcome;
import com.codecool.marsexploration.data.SimulationInput;
import com.codecool.marsexploration.data.rover.Explorer;
import com.codecool.marsexploration.data.rover.Gatherer;
import com.codecool.marsexploration.logic.analyzer.CheckLandingCoordonates;
import com.codecool.marsexploration.logic.phase.LogPhase;
import com.codecool.marsexploration.logic.phase.Phase;
import com.codecool.marsexploration.logic.routine.BuildingRoutine;
import com.codecool.marsexploration.logic.routine.ExploringRoutine;
import com.codecool.marsexploration.logic.routine.ReturningRoutine;
import com.codecool.marsexploration.logic.routine.Routine;
import com.codecool.marsexploration.utils.LogSaver;
import com.codecool.marsexploration.utils.ReadFile;

import java.util.ArrayList;
import java.util.List;

import static com.codecool.marsexploration.data.Symbol.MINERAL;
import static com.codecool.marsexploration.data.Symbol.WATER;

public class ExplorationSimulator {
	private List<Phase> phases;
	private MarsMapFrame marsMapFrame;

	public ExplorationSimulator(List<Phase> phases) {
		this.phases = phases;
		marsMapFrame = null;
	}

	public Context simulate(SimulationInput input) {
		Context context = process(input);
		Phase logPhase = new LogPhase(new LogSaver());
		marsMapFrame = new MarsMapFrame(context.getMap());
		marsMapFrame.updateRoverPosition(context);


		CheckLandingCoordonates checkLandingCoordonates = new CheckLandingCoordonates();
		if (checkLandingCoordonates.analyze(context).isPresent()) {
			context.setOutcome(checkLandingCoordonates.analyze(context));
			logPhase.perform(context);
			return null;
		}

		logPhase.perform(context);
		context.setStepNumber(context.getStepNumber() + 1);
		marsMapFrame.updateRoverPosition(context);

		while (context.getOutcome().isEmpty()) {
			for (Phase phase : phases) {
				phase.perform(context);

				try {
					Thread.sleep(50); // Add a delay to visualize the rover's movements
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				marsMapFrame.updateRoverPosition(context);
			}

			if (marsMapFrame != null) {
				marsMapFrame.updateResourcesFound(
						context.getExplorer().getSightings().values().stream()
								.filter(s -> s.equals(WATER.getSymbol()) || s.equals(MINERAL.getSymbol()))
								.count()
				);
			}

		}

		if (context.getOutcome().orElse(null).getStatusMessage().equals(Outcome.COLONIZABLE.getStatusMessage())) {
			context.getExplorer().setState(new BuildingRoutine());
			context.getExplorer().findAndAssignBestBuildingSpot(context);
			context.getExplorer().initRouteToBuildingSpot(context);

			while (context.getCommandCentres().isEmpty()) {
				context.getExplorer().getState().move(context);
				marsMapFrame.updateRoverPosition(context);
				try {
					Thread.sleep(50); // Add a delay to visualize the rover's movements
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				logPhase.perform(context);
				context.incrementStepNumber();
			}

			while (!context.getCommandCentres().get(0).getResourcesInSightQueue().isEmpty()) {
				List<Gatherer> gatherersCopy = new ArrayList<>(context.getCommandCentres().get(0).getGatherers());
				CommandCentre commandCentre = context.getCommandCentres().get(0);
				for (Gatherer gatherer : gatherersCopy) {
					marsMapFrame.updateRoverPosition(context);
					try {
						Thread.sleep(50); // Add a delay to visualize the rover's movements
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					logPhase.perform(context);
					gatherer.getState().move(context);
					context.incrementStepNumber();
				}
				commandCentre.setGatherers(commandCentre.getGatherers());

			}

		} else {
			context.getExplorer().setState(new ReturningRoutine());
			while (!context.getExplorer().getCurrentLocation().equals(context.getLanding())) {
				context.getExplorer().getState().move(context);
			}
		}
		return context;
	}

	private boolean allBasesHaveMaxNumberOfGatherers(Context context) {
		boolean allCentresHaveEqualResources = true;
		List<CommandCentre> commandCentres = context.getCommandCentres();
		for (CommandCentre centre : commandCentres) {
			if (centre.getGatherers().size() != centre.getResourcesInSight().size()) {
				allCentresHaveEqualResources = false;
				break;
			}
		}
		return allCentresHaveEqualResources;
	}

	private Context process(SimulationInput input) {
		Routine state = new ExploringRoutine();
		Explorer rover = new Explorer(input.landing(), state, 5);
		return new Context(0, 100, getMap(input.mapPath()), input.landing(), rover, input.logPath());
	}

	private Character[][] getMap(String mapPath) {
		ReadFile readFile = new ReadFile();
		String file = readFile.readFile(mapPath);
		String[] mapRows = file.split("\n");
		int mapLength = mapRows.length;
		Character[][] map = new Character[mapLength][mapLength];

		for (int i = 0; i < mapLength; i++) {
			String[] rowSymbols = mapRows[i].split("");
			for (int j = 0; j < mapLength; j++) {
				map[i][j] = rowSymbols[j].charAt(0);
			}
		}
		return map;
	}
}
