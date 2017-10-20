package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;


@Autonomous(name = "Jewel", group = "Test")
public class JewelOpMode extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Alliance alliance = Alliance.Red;

        NormalizedColorSensor colorSensor = Viki.getRobotPart(hardwareMap, RobotPart.colorSensor);
        // read the color
        HsvValues hsv = Viki.getHsvValues(colorSensor);


        // decide if red or blue
        boolean isRed = isRed(hsv);
        boolean isBlue = isBlue(hsv);

        // if blue alliance hit blue ball, if red alliance hit red ball


    }

    private boolean isRed(HsvValues hsv) {
        if (hsv.Hue < 5) {
            return true;
        } else if (hsv.Hue > 320) {
            return true;

        } else return false;
    }

    private boolean isBlue(HsvValues hsv) {
        if (hsv.Hue < 180) {
            return false;
        } else if (hsv.Hue > 220) {
            return false;

        } else return true;
    }
}
