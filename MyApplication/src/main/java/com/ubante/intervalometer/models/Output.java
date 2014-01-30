package com.ubante.intervalometer.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Con_0 on 1/29/14.
 */
public class Output {

    private Settings s;
    private Movie m;
    private Reality r;

    /** This will consider possible problems. */
    String getSpecialReport() {
        List<String> notes = new ArrayList<String>();
        String specialReport = "";

        // check for over exposure
        float exposure = s.getExposure();
        double stops = Settings.exposureToStops(exposure);
        notes.add(String.format("The exposure is %1.1f times (%1.1f stops) that of the Sunny 16 rule.",
                exposure, stops));

        // check for space usage on memory card
        float totalCardSpaceUsed = s.getNumberOfFrames()*r.getImageSize();
        int cardCapacity = r.getCardCapacity();
        float percent = totalCardSpaceUsed / cardCapacity * 100 / 1024;
        notes.add(String.format("This sequence will take %.1f MB on your %d GB card (%.1f%%).",
                totalCardSpaceUsed,
                cardCapacity,
                percent));
        if (percent > 50) {
            notes.add("Warning: make sure you have space on your memory card.");
        }

        // check for blur from long shutter speeds
        if (Settings.RULEOFSIXHUNDRED/s.getFocalLength() < s.getShutterSpeed()) {
            notes.add(String.format(
                    "Warning: shutter speed too slow for focal length (%d mm) and will cause motion blur",
                    s.getFocalLength()));
        }

        // check for 400 frame limit of LRTimelapse
        if (s.getNumberOfFrames() > Movie.MAXFRAMESALLOWEDBYFREETLTIMELAPSE) {
            notes.add(String.format(
                    "Warning: %d is too many frames for one movie made with the free version of TLTimelapse",
                    s.getNumberOfFrames()));
        }

        // check for long sequences
        if (r.getDurationHours() > 2) {
            notes.add(String.format("Warning: your duration (%.2f) is >2hours - consider Aperature priority.",
                    r.getDurationHours()));
        }

        // check for batteries
        float batteriesNeeded = r.getBatteryUsage(s.getNumberOfFrames());
        if (batteriesNeeded > 1) {
            notes.add(String.format("Fatal: insufficient battery power; you need %f batteries.",
                    batteriesNeeded));
        } else if (batteriesNeeded > 0.8) {
            notes.add(String.format("Warning: your battery usage is very high (%.1f%%) - consider fresh batteries.",
                    batteriesNeeded*100));
        }

        if (! notes.isEmpty()) {
            specialReport = "-------------------------------\nNotes:";
            for ( String note : notes ) {
                specialReport += note+"\n";
//                System.out.println(note);
            }
        }

        return specialReport;
    }

    String getStandardReport() {
        String standardReport = "";

        standardReport += "\n===============================\n";
        standardReport += String.format("%s\n", m.getTitle());
        standardReport += "-------------------------------\nSettings:";
        standardReport += String.format("\tnumber of frames\t%8d\n", s.getNumberOfFrames());
        standardReport += String.format("\tshutter speed\t\t\t%10.5f\n", s.getShutterSpeed());
        standardReport += String.format("\tdelay\t\t\t\t%10.1f\n", s.getIntervalBetweenFrames()); // weird that this wants a float
        standardReport += "-------------------------------\nMovie:";
        standardReport += String.format("\tduration (seconds)\t%10.1f\n", m.getDurationSeconds());
        standardReport += String.format("\tduration (minutes)\t%10.1f\n", m.getDurationMinutes());
        standardReport += String.format("\tFPS\t\t\t\t\t%10.1f\n", m.getFPS());
        standardReport += String.format("\tspeedup\t\t\t\t%10.1f\n", m.getSpeedUp(r.getDurationSeconds()));
        standardReport += String.format("-------------------------------\nReality:");
        standardReport += String.format("\tduration (seconds)\t%10.1f\n", r.getDurationSeconds());
        standardReport += String.format("\tduration (minutes)\t%10.1f\n", r.getDurationMinutes());
        standardReport += String.format("\tduration (hours)\t%10.1f\n", r.getDurationHours());
        standardReport += String.format("\ttime now\t\t\t\t%s\n",r.getNow().getTime());
        standardReport += String.format("\tsequence start\t\t\t%s\n",r.getSequenceStart().getTime());
        standardReport += String.format("\tsequence finish\t\t\t%s\n",r.getSequenceFinish().getTime());
        standardReport += String.format("\ttotal elapsed time\t%10.1f\n",r.getTotalElapsedTimeSeconds());

        return standardReport;
    }

    public String getOutput() {
        String out = getStandardReport();
        out += getSpecialReport();
        return out;
    }

    /** Constructor */
    public Output (Settings s, Movie m, Reality r) {
        this.s = s;
        this.m = m;
        this.r = r;
    }
}
