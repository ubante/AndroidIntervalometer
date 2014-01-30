package com.ubante.intervalometer.models;

/**
 * Created by Con_0 on 1/29/14.
 */
public class Movie {

    static int MAXFRAMESALLOWEDBYFREETLTIMELAPSE = 400;
    static float DEFAULTFPS = 30;
    static String DEFAULTTITLE = "Default movie title (you should set this)";
    private float FPS = DEFAULTFPS;
    private float speedUp;
    private float durationSeconds;
    private int numberOfFrames;
    private String title;

    public float getDurationMinutes() {
        return getDurationSeconds() / 60;
    }

    public float getDurationSeconds() {
        durationSeconds = numberOfFrames / FPS;
        return durationSeconds;
    }

    public String getTitle() {
        return title;
    }

    public float getFPS() {
        return FPS;
    }

    public void setFPS(float FPS) {
        this.FPS = FPS;
    }

    public float getSpeedUp(float realElaspedTimeSeconds) {
        speedUp = realElaspedTimeSeconds / durationSeconds;
        return speedUp;
    }

    void setTitle(String title) {
        this.title = title;
    }

    /** Constructors */
    Movie (Settings s, String title) {
        numberOfFrames = s.getNumberOfFrames();
        this.title = title;
    }

    public Movie (Settings s) {
        this(s, DEFAULTTITLE);
    }
}
