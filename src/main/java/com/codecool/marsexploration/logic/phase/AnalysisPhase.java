package com.codecool.marsexploration.logic.phase;

import com.codecool.marsexploration.data.Context;
import com.codecool.marsexploration.logic.analyzer.Analyzer;

import java.util.List;

public class AnalysisPhase implements Phase{
    //analazyer field (not analyzerImpl) refer to interface
    private final List<Analyzer> analyzers;

    public AnalysisPhase(List<Analyzer> analyzers) {
        this.analyzers = analyzers;
    }

    @Override
    public void perform(Context context) {
        //call analyzer.analyze method here
        for (int i = 0; i < analyzers.size() && context.getOutcome().isEmpty(); i++) {
            Analyzer analyzer = analyzers.get(i);
            context.setOutcome(analyzer.analyze(context));
        }
    }
}
