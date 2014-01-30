package com.ubante.intervalometer.models;

/**
 * Created by Con_0 on 1/29/14.
 */
public class MemoryCard {

    private int capacity; // in GB

    int getCapacity() {
        return capacity;
    }

    MemoryCard (int capacity) {
        this.capacity = capacity;
    }
}
