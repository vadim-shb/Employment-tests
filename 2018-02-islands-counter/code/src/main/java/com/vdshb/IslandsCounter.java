package com.vdshb;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class IslandsCounter {
    private IslandFactory islandFactory = new IslandFactory();

    private char cell;
    private int coordinateX = 0;
    private Map<Integer, Island> lastLineIslands = Collections.emptyMap();
    private Map<Integer, Island> currentLineIslands = new HashMap<>();
    private Set<Island> lastLineIslandsSet = Collections.emptySet();
    private Set<Island> currentLineIslandsSet = new HashSet<>();
    private int islandsQuantity = 0;

    public int countIslands(Reader map) throws IOException {
        int character;
        do {
            character = map.read();
            if (character == -1) break;
            cell = (char) character;
            handleMapCell();
        } while (true);
        finishLine();
        finishLine();
        return islandsQuantity;
    }

    private void handleMapCell() {
        coordinateX++;
        switch (cell) {
            case '0':
                break;
            case '1':
                Island currentIsland = getCurrentIsland();
                currentIsland.incSize(1);
                currentLineIslands.put(coordinateX, currentIsland);
                currentLineIslandsSet.add(currentIsland);
                break;
            case '\n':
                finishLine();
                coordinateX = 0;
                break;
            default:
                throw new AssertionError("Illegal character in the map");
        }
    }

    private Island getCurrentIsland() {
        Island currentIslandFromLeft = currentLineIslands.get(coordinateX - 1);
        Island currentIslandFromAbove = lastLineIslands.get(coordinateX);
        if (currentIslandFromAbove == null && currentIslandFromLeft == null) {
            return islandFactory.generateIsland();
        }
        if (currentIslandFromAbove != null && currentIslandFromLeft != null) {
            return mergeIslands(currentIslandFromAbove, currentIslandFromLeft);
        }
        if (currentIslandFromAbove != null) {
            return currentIslandFromAbove;
        }
        return currentIslandFromLeft;
    }

    private void finishLine() {
        lastLineIslandsSet.removeAll(currentLineIslandsSet);
        lastLineIslandsSet.forEach(island -> {
            if (island.getSize() > 1) islandsQuantity++;
        });

        lastLineIslands = currentLineIslands;
        currentLineIslands = new HashMap<>();

        lastLineIslandsSet = currentLineIslandsSet;
        currentLineIslandsSet = new HashSet<>();
    }

    private Island mergeIslands(Island island1, Island island2) {
        island1.incSize(island2.getSize());
        changeIslandObjects(lastLineIslands, island2, island1);
        changeIslandObjects(currentLineIslands, island2, island1);
        lastLineIslandsSet.remove(island2);
        currentLineIslandsSet.remove(island2);
        lastLineIslandsSet.add(island1);
        currentLineIslandsSet.add(island1);
        return island1;
    }

    private void changeIslandObjects(Map<Integer, Island> lineIslands, Island removeIsland, Island actualIsland) {
        for (Map.Entry<Integer, Island> entry : lineIslands.entrySet()) {
            if (entry.getValue().equals(removeIsland))
                lineIslands.put(entry.getKey(), actualIsland);
        }
    }
}
