package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "Hello", group = "Other Side")
public class HelloOpMode extends OpMode {

    private NormalizedColorSensor colorSensor = null;
    private float[] hsvValues = new float[3];

    public static NormalizedRGBA getRelativeColor(NormalizedColorSensor sensor) {

        NormalizedRGBA colors = sensor.getNormalizedColors();

        float max = Math.max(Math.max(Math.max(colors.red, colors.green), colors.blue), colors.alpha);

        // normalize relative to maximum value found in current reading
        colors.blue /= max;
        colors.green /= max;
        colors.red /= max;

        return colors;
    }

    public static void printColors(Telemetry telemetrics, NormalizedRGBA colors) {
        telemetrics.addLine()
                .addData("Color.alpha", "%.3f", colors.alpha)
                .addData("Color.red", "%.3f", colors.red)
                .addData("Color.green", "%.3f", colors.green)
                .addData("Color.blue", "%.3f", colors.blue);
    }

    /**
     * User defined init method
     * <p>
     * This method will be called once when the INIT button is pressed.
     */
    @Override
    public void init() {

        colorSensor = TeamShared.getRobotPart( hardwareMap, RobotPart.colorSensor);

        telemetry.addLine("Initialized!");
    }

    /**
     * User defined loop method
     * <p>
     * This method will be called repeatedly in a loop while this op mode is running
     * Coding is like healers, no one wants to pick them XD
     */
    @Override
    public void loop() {
        NormalizedRGBA colors = getRelativeColor(colorSensor);

        Color.colorToHSV(colors.toColor(), hsvValues);

        telemetry.addLine()
                .addData("Hue", "%.3f", hsvValues[0])
                .addData("Saturation", "%.3f", hsvValues[1])
                .addData("Value", "%.3f", hsvValues[2]);


        printColors(telemetry, colors);


    }
}
