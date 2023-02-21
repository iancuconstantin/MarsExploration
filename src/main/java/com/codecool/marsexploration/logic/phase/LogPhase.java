package com.codecool.marsexploration.logic.phase;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.utils.LogSaver;

public class LogPhase implements Phase{

    private final LogSaver writer;

    public LogPhase(LogSaver writer) {
        this.writer = writer;
    }

    @Override
    public void perform(Context context) {
            writer.logStep(context);
    }
}
