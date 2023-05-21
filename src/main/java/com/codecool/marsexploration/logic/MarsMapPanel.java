package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.rover.Gatherer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MarsMapPanel extends JPanel {
	private final Character[][] map;
	private Coordinate roverPosition;
	private List<Coordinate> gatheresPosition;

	public MarsMapPanel(Character[][] map) {
		this.map = map;
		this.roverPosition = new Coordinate(0, 0);
		this.gatheresPosition = new ArrayList<>();
	}

	public void updateRoverPosition(Context context) {
		this.roverPosition = context.getExplorer().getCurrentLocation();
		if (!context.getCommandCentres().isEmpty()) {
			if (!context.getCommandCentres().get(0).getGatherers().isEmpty()) {
				this.gatheresPosition = context
						.getCommandCentres()
						.get(0)
						.getGatherers()
						.stream()
						.map(Gatherer::getCurrentLocation)
						.toList();
			}
		}
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int tileSize = 20;
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map.length; y++) {
				char symbol = map[y][x];
				switch (symbol) {
					case '*' -> g.setColor(Color.GREEN); // Resources
					case '^' -> g.setColor(Color.GRAY); // Mountains
					case '~' -> g.setColor(Color.BLUE); // Water
					case '#' -> g.setColor(Color.MAGENTA); // Pits
					case 'C' -> g.setColor(Color.ORANGE);
					case 'A' -> // Alien
							g.setColor(Color.RED);
					default -> g.setColor(Color.WHITE); // Empty space
				}
				g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);

				if (x == roverPosition.y() && y == roverPosition.x()) {
					g.setColor(Color.BLACK);
					g.fillOval(x * tileSize, y * tileSize, tileSize, tileSize);
				}

				if (!gatheresPosition.isEmpty()) {
					for (Coordinate coordinate : gatheresPosition) {
						if (x == coordinate.y() && y == coordinate.x()) {
							g.setColor(Color.PINK);
							g.fillOval(x * tileSize, y * tileSize, tileSize, tileSize);
						}
					}
				}
			}
		}
	}
}