package com.vdshb;

public class Island {

    private final int id;
    private int size = 0;

    public Island(int id) {
        this.id = id;
    }

    public void incSize(int amount) {
        size += amount;
    }

    public int getSize() {
        return size;
    }

    @Override
    public int hashCode() {
        return id;
    }

}
