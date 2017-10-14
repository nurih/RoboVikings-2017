package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

/**
 * Created by Robovikings on 10/14/2017.
 */
@Autonomous(name = "Jewel")
public class JewelOpMode extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Alliance alliance = Alliance.Red;
        NormalizedColorSensor colorSensor = TeamShared.getRobotPart(hardwareMap, RobotPart.colorSensor);
        // read the color
        NormalizedRGBA color =  HelloOpMode.getRelativeColor(colorSensor);
        // change to hsv
         float[] hsvValues = new float[3];

        Color.colorToHSV(color.toColor(), hsvValues);

        // decide if red or blue
        boolean isRed = isRed(hsvValues);
        boolean isBlue = isBlue(hsvValues);
        // if blue alliance hit blue ball, if red alliance hit red ball
    }

    private boolean isRed(float[] hsvValues) {
        float hue = hsvValues[0];
        if (hue <5) {
            return true;
        }else if (hue >320) {
            return true;

        }else return false;

    }
    private boolean isBlue(float[] hsvValues) {
        float hue = hsvValues[0];
        if (hue <180) {
            return false;
        }else if (hue >220) {
            return false;

        }else return true;

    }
}
