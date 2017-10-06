package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

/**
 * Created by Robovikings on 9/23/2017.
 */

@TeleOp(name = "Color Sensor", group = "Test")

public class ColorSensorOpMode extends OpMode {
    NormalizedColorSensor colorSensor;


    @Override
    public void init() {

        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");
    }

    @Override
    public void loop() {
        NormalizedRGBA normalizedColors = colorSensor.getNormalizedColors();
        telemetry.addData("red", normalizedColors.red);
        telemetry.addData("blue", normalizedColors.blue);
        telemetry.addData("green", normalizedColors.green);
        if(normalizedColors.blue > 0.02) {
            telemetry.addData("red", "red");
        }
    }
}