package com.codecool.marsexploration.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile{

    public String readFile(String filePath){
        StringBuilder sb = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while((line = br.readLine()) != null)
                sb.append(line).append("\n");
        }catch(IOException e){
            System.out.println("An error occurred while reading the file.");
        }
        return sb.toString();
    }
}
