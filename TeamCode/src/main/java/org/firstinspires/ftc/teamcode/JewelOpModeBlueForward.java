package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name = "Blue Forward", group = "Test")
public class JewelOpModeBlueForward extends LinearOpMode {
    public static final double KNOCK_JEWEL_TIME = .2;
    public static final double DRIVE_OFF_PLATFORM_TIME = 3;
    public static final double TURN_TIME = 0;
    public static final double PARKING_TIME = 0;

    final float FULLPOWER = 0.5f;
    NormalizedColorSensor colorSensor;
    Alliance allience = Alliance.Red;
    private Servo servo = null;
    private DcMotor rightMotor = null;
    private DcMotor leftMotor = null;


    public void setup() {
        telemetry.addLine("setup!");
        colorSensor = Viki.getRobotPart(hardwareMap, RobotPart.colorSensor);
        servo = Viki.getRobotPart(hardwareMap, RobotPart.jewelServo);
        rightMotor = Viki.getRobotPart(hardwareMap, RobotPart.rightMotor);
        leftMotor = Viki.getRobotPart(hardwareMap, RobotPart.leftMotor);

        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        servo.setPosition(Servo.MAX_POSITION);

        telemetry.addLine("Initialized!");
    }


    @Override
    public void runOpMode() throws InterruptedException {
        setup();

        // Lower arm
        servo.setPosition(Servo.MAX_POSITION);
        // wait for servo to drop
        double startTime = super.getRuntime();

        do {
            // waiting for servo to drop
        } while (getRuntime() < startTime + 3);

        // detect color
        boolean isRed;
        boolean isBlue;
        do {
            HsvValues hsv = Viki.getHsvValues(colorSensor);
            isRed = getIsRed(hsv);
            isBlue = getIsBlue(hsv);
            telemetry.addData("Hsv.Hue", hsv.Hue);
            telemetry.addData("Hsv.Value", hsv.Value);
            telemetry.addData("Seeing Blue ", isBlue);
            telemetry.addData("Seeing Red ", isRed);
            telemetry.update();
        } while (isRed == false && isBlue == false);
        //}

        // "react" - knock off the jewel according to our alliance

        if (isBlue) {
            goBackward();
        } else if (isRed) {
            goForward();
        }
        stopMotors();
    }

    private void stopMotors() {
        leftMotor.setPower(0);
        rightMotor.setPower(0);

    }

    private void goBackward() {
        double startTime = super.getRuntime();
        do {
            leftMotor.setPower(FULLPOWER);
            rightMotor.setPower(FULLPOWER);
        } while (getRuntime() < startTime + KNOCK_JEWEL_TIME);

        servo.setPosition(Servo.MIN_POSITION);

        do {
            leftMotor.setPower(-FULLPOWER);
            rightMotor.setPower(-FULLPOWER);
        } while (getRuntime() < startTime + DRIVE_OFF_PLATFORM_TIME);

        startTime = super.getRuntime();

        do {
            leftMotor.setPower(-FULLPOWER);
            rightMotor.setPower(FULLPOWER);
        } while (getRuntime() < startTime + TURN_TIME);

        startTime = super.getRuntime();
        do {
            leftMotor.setPower(-FULLPOWER);
            rightMotor.setPower(-FULLPOWER);
        } while (getRuntime() < startTime + PARKING_TIME);


    }


    private void goForward() {
        double startTime = super.getRuntime();
        do {
            leftMotor.setPower(-FULLPOWER);
            rightMotor.setPower(-FULLPOWER);
        } while (getRuntime() < startTime + KNOCK_JEWEL_TIME);
        servo.setPosition(Servo.MIN_POSITION);
        startTime = super.getRuntime();
        do {
            leftMotor.setPower(-FULLPOWER);
            rightMotor.setPower(-FULLPOWER);
        } while (getRuntime() < startTime + DRIVE_OFF_PLATFORM_TIME + KNOCK_JEWEL_TIME);

        startTime = super.getRuntime();

        do {
            leftMotor.setPower(-FULLPOWER);
            rightMotor.setPower(FULLPOWER);
        } while (getRuntime() < startTime + TURN_TIME);

        startTime = super.getRuntime();
        do {
            leftMotor.setPower(-FULLPOWER);
            rightMotor.setPower(-FULLPOWER);
        } while (getRuntime() < startTime + PARKING_TIME);

    }

    private boolean getIsBlue(HsvValues hsv) {
        boolean result;
        telemetry.addLine("getIsBlue!");
        if (hsv.Hue < 180) {
            result = false;
        } else if (hsv.Hue > 220) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    private boolean getIsRed(HsvValues hsv) {
        telemetry.addLine("getIsRed!");
        boolean result;
        if (hsv.Hue < 5) {
            result = true;
        } else if (hsv.Hue > 320) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

}
