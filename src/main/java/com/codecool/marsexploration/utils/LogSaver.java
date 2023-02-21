package com.codecool.marsexploration.utils;

import com.codecool.marsexploration.data.Context;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogSaver {

    public void logStep(Context context){
        //STEP 6; EVENT position; UNIT rover-1; POSITION [21,22]
        String content = "STEP-" + context.stepNumber() + "; EVENT position; UNIT ROVER-" + context.rover().getId() + "; POSITION [" + context.rover().getCoordinate().x() + "," +context.rover().getCoordinate().y() + "]";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(context.logPath()))){
            bw.append(content);
        }catch(IOException e){
            System.out.println("An error occurred while trying to write the file.\n Error message: " + e.getMessage());
        }
    }
}