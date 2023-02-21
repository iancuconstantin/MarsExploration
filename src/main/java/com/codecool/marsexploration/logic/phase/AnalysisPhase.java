package com.codecool.marsexploration.logic.phase;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.logic.analyzer.Analyzer;

public class AnalysisPhase implements Phase{
    //analazyer field (not analyzerImpl) refer to interface
    private final Analyzer analyzer;

    public AnalysisPhase(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    @Override
    public void perform(Context context) {
        //call analyzer.analyze method here
        analyzer.analyze(context);
    }
}
