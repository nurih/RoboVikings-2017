package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name = "Jewel", group = "Test")
public class JewelOpMode extends OpMode {
    final float FULLPOWER = 1;
    NormalizedColorSensor colorSensor;

    private Servo servo = null;
    private DcMotor rightMotor = null;
    private DcMotor leftMotor = null;

    @Override
    public void init() {
        colorSensor = Viki.getRobotPart(hardwareMap, RobotPart.colorSensor);
        servo = Viki.getRobotPart(hardwareMap, RobotPart.jewelServo);
        rightMotor = Viki.getRobotPart(hardwareMap, RobotPart.rightMotor);
        leftMotor = Viki.getRobotPart(hardwareMap, RobotPart.leftMotor);

        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        servo.setPosition(Servo.MAX_POSITION);
telemetry.addLine("Initialized!");
    }

    @Override
    public void loop() {

        HsvValues hsv = Viki.getHsvValues(colorSensor);

        boolean isRed = getIsRed(hsv);
        boolean isBlue = getIsBlue(hsv);

        servo.setPosition(Servo.MAX_POSITION);

        if ( isBlue ) {
            leftMotor.setPower(FULLPOWER);
            rightMotor.setPower(FULLPOWER);

        } else if ( isRed ) {
            leftMotor.setPower(-FULLPOWER);
            rightMotor.setPower(-FULLPOWER);

        } else {
            leftMotor.setPower(0);
            rightMotor.setPower(0);
        }

        telemetry.addData("Hsv.Hue", hsv.Hue);
        telemetry.addData("Hsv.Value", hsv.Value);

        telemetry.addData("Seeing Blue ", isBlue);
        telemetry.addData("Seeing Red ", isRed);


    }

    private boolean getIsBlue(HsvValues hsv) {
        boolean result;
        if ( hsv.Hue < 180 ) {
            result = false;
        } else if ( hsv.Hue > 220 ) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    private boolean getIsRed(HsvValues hsv) {

        boolean result;
        if ( hsv.Hue < 5 ) {
            result = true;
        } else if ( hsv.Hue > 320 ) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

}

