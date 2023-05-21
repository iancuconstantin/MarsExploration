package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.Context;

import javax.swing.*;
import java.awt.*;

public class MarsMapFrame extends JFrame {
    private final MarsMapPanel marsMapPanel;
    private final JLabel resourcesFoundLabel;

    public MarsMapFrame(Character[][] map) {
        setTitle("Mars Rover Exploration");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        resourcesFoundLabel = new JLabel("Resources found: 0"); // Initialize the label
        marsMapPanel = new MarsMapPanel(map);
        add(marsMapPanel);
        setLayout(new BorderLayout()); // Set the layout
        add(resourcesFoundLabel, BorderLayout.NORTH); // Add the label to the frame
        add(marsMapPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void updateRoverPosition(Context context) {
        marsMapPanel.updateRoverPosition(context);
    }

    public void updateResourcesFound(long resourcesFound) {
        resourcesFoundLabel.setText("Resources found: " + resourcesFound);
    }

}
