package com.ubante.intervalometer.models;

import java.util.Calendar;

/**
 * Created by Con_0 on 1/29/14.
 */
public class Reality {


    private float durationSeconds;
    private Calendar now;
    private Calendar sequenceStart;
    private Calendar sequenceFinish;
    private float imageSize = 15; // in MB
    private MemoryCard card;
    private Battery battery;
    private float totalElapsedTimeSeconds; // includes the delay before sequence start


    float getDurationSeconds() {
        return durationSeconds;
    }

    float getDurationMinutes() {
        return durationSeconds / 60;
    }

    float getDurationHours() {
        return durationSeconds / 3600;
    }

    float getImageSize() {
        return imageSize;
    }

    Calendar getNow() {
        return now;
    }

    int getCardCapacity() {
        return card.getCapacity();
    }

    float getBatteryUsage(int frames) {
//        return battery.getConsumption(frames, getDurationMinutes());
        return battery.getConsumption(frames, totalElapsedTimeSeconds/60);
    }

    void computeTimes(Settings s) {
        // The below is not precise since the two different calls to getInstance will return
        // slightly different time values.
        sequenceStart = Calendar.getInstance();
        sequenceStart.add(Calendar.SECOND, s.getStartDelay());
        sequenceFinish = Calendar.getInstance();
        sequenceFinish.add(Calendar.SECOND, s.getStartDelay());
        sequenceFinish.add(Calendar.SECOND, (int) getDurationSeconds());
        totalElapsedTimeSeconds = durationSeconds + s.getStartDelay();
    }

    Calendar getSequenceStart() {
        return sequenceStart;
    }

    Calendar getSequenceFinish() {
        return sequenceFinish;
    }

    float getTotalElapsedTimeSeconds() {
        return totalElapsedTimeSeconds;
    }

    /** Constructor */
    public Reality (Settings s) {
        durationSeconds = s.calculateTotalElapsedTime();
        card = new MemoryCard(32); // this and battery may need to go to a new class, like Equipment
        battery = new Battery();
        now = Calendar.getInstance();
        computeTimes(s);
    }
}
