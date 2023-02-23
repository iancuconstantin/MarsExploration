package com.codecool.marsexploration.data;

public enum Outcome {
    COLONIZABLE("COLONIZABLE"),
    NOT_COLONIZABLE_RESOURCES("NOT_COLONIZABLE_RESOURCES"),
    NOT_COLONIZABLE_ALIENS("NOT_COLONIZABLE_ALIENS"),
    TIMEOUT("TIMEOUT"),
    WRONG_LANDING_COORDINATES("WRONG_LANDING_COORDINATES");

    private final String statusMessage;

    Outcome(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
