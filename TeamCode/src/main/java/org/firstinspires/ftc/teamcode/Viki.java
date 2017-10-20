package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

/**
 * Team Shared code
 * Library of functions to help build op modes
 */
public class Viki {
    public static final String VisionKey = "AbQajKn/////AAAAGU7i3QBuOUjSp+FIwylUn4R1t6lifGcZsFjRQgSVrzy6o0q2+awGv2OiUTS+JJDAP1cPjzy8Qqaa+W0Kp1y+wDyNNJzXPKTk9zpoeA6tCnaH1N7xsfUz8DxBRZmipkzHUWSCwCkslVlvf71X4HXh3tqIJetRchP55t26A3yfgQHBZN6aMMGXR/DWLNv1zI8+t7O4dml5kmHkZLG8yLOr9G8jWUUt7A7e4eoWLxkFm7JE+DTBdIH3dSekVfcSx4tZ09/bDL4fATsN6oom4YzDeWDUaC9M4C+/7MBDLaG7dhtSs6aXhhcSfD3GF1mb1KMju4nO9xuM9ehbCTNJlyt/uHAihVmRtVu08IeVSTO+XsvS";
    public static final double STOP_MOTOR = 0;

    /**
     * Gets a well known robot part
     */
    public static <T> T getRobotPart(HardwareMap map, RobotPart part) {
        String deviceName = part.name();

        try {
            return (T) map.get(part.getPartType(), deviceName);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Computes scaled RGBA values based on the maximum value of RGBA components.
     * This should increase the span and spread between individual RGBA values when they are close to each other.
     */
    public static NormalizedRGBA getScaledRGBA(NormalizedColorSensor sensor) {

        NormalizedRGBA colors = sensor.getNormalizedColors();

        float max = Math.max(Math.max(Math.max(colors.red, colors.green), colors.blue), colors.alpha);

        // normalize relative to maximum value found in current reading
        colors.blue /= max;
        colors.green /= max;
        colors.red /= max;

        return colors;
    }

    /**
     * Gets HSV values based on scaled RGBA values from the sensor.
     */
    public static HsvValues getHsvValues(NormalizedColorSensor sensor) {
        NormalizedRGBA colors = getScaledRGBA(sensor);

        return getHsvValues(colors);
    }

    /**
     * Gets HSV values from NormalizedRGBA color.
     */
    public static HsvValues getHsvValues(NormalizedRGBA colors) {
        float[] hsvValues = new float[3];

        Color.colorToHSV(colors.toColor(), hsvValues);

        return new HsvValues(hsvValues);
    }
}

