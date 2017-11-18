package org.firstinspires.ftc.teamcode.Experimental;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.teamcode.HsvValues;
import org.firstinspires.ftc.teamcode.RobotPart;
import org.firstinspires.ftc.teamcode.Viki;

@Disabled
@Autonomous(name = "Color Sensor", group = "Test")
public class ColorSensorOpMode extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        NormalizedColorSensor colorSensor = Viki.getRobotPart(hardwareMap, RobotPart.colorSensor);

        telemetry.addLine("Initialized!");
        while (opModeIsActive()) {
            NormalizedRGBA colors = Viki.getScaledRGBA(colorSensor);
            HsvValues hsv = Viki.getHsvValues(colorSensor);
            telemetry.addLine("Wassup!");
            telemetry.addLine()
                    .addData("Hue", "%.3f", hsv.Hue)
                    .addData("Saturation", "%.3f", hsv.Saturation)
                    .addData("Value", "%.3f", hsv.Value);

            telemetry.addLine()
                    .addData("Color.alpha", "%.3f", colors.alpha)
                    .addData("Color.red", "%.3f", colors.red)
                    .addData("Color.green", "%.3f", colors.green)
                    .addData("Color.blue", "%.3f", colors.blue);
            telemetry.update();
        }

    }
}