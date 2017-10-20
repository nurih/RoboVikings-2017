package org.firstinspires.ftc.teamcode;

public class HsvValues {
    public final float Hue;
    public final float Value;
    public final float Saturation;


    HsvValues(float[] hsvValues) {
        if (hsvValues == null || hsvValues.length != 3) {
            throw new IllegalArgumentException("HSV values must be an array of 3 elements");
        }

        this.Hue = hsvValues[0];

        this.Value = hsvValues[1];

        this.Saturation = hsvValues[2];
    }
}
