package com.vdshb;

public class IslandFactory {
    private int idGenerator = 0;

    public Island generateIsland() {
        return new Island(idGenerator++);
    }
}
