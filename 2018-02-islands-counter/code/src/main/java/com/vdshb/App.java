package com.vdshb;

import java.io.*;

public class App {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("File with map must be specified as first param");
            return;
        }
        String inputPath = args[0];

        File file = new File(inputPath);

        try (Reader reader = new BufferedReader(new FileReader(file), 16384)) {
            IslandsCounter islandsCounter = new IslandsCounter();
            System.out.println("Quantity of islands in the map: " + islandsCounter.countIslands(reader));
        } catch (FileNotFoundException e) {
            System.out.println("File '" + inputPath + "' not found");
        } catch (IOException e) {
            System.out.println("IO Error on reading file '" + inputPath + "'");
            e.printStackTrace();
        }
    }

}
