package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name = "Blue Right", group = "Test")
public class JewelOpModeBlueRight extends LinearOpMode {
    public static final double KNOCK_JEWEL_TIME = .40001;
    public static final double DRIVE_OFF_PLATFORM_TIME = 1.5;
    public static final double TURN_TIME = .5;
    public static final double PARKING_TIME = .6;
    public static final double EXTENDER_GRIP = .3;

    final float FULLPOWER = 0.5f;
    NormalizedColorSensor colorSensor;
    Alliance allience = Alliance.Blue;
    double MAX_POSITION = .9;
    private Servo servo = null;
    private Servo servoCube = null;
    private DcMotor rightMotor = null;
    private DcMotor leftMotor = null;
    private DcMotor extenderMotor = null;


    public void setup() {
        telemetry.addLine("setup!");
        colorSensor = Viki.getRobotPart(hardwareMap, RobotPart.colorSensor);
        servo = Viki.getRobotPart(hardwareMap, RobotPart.jewelServo);
        rightMotor = Viki.getRobotPart(hardwareMap, RobotPart.rightMotor);
        leftMotor = Viki.getRobotPart(hardwareMap, RobotPart.leftMotor);
        servoCube = Viki.getRobotPart(hardwareMap, RobotPart.cubeLiftClaw);
        servoCube.setPosition(0.5);
        extenderMotor = Viki.getRobotPart(hardwareMap, RobotPart.relicExtenderMotor);
        extenderMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        //extenderMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        telemetry.addLine("Initialized!");
    }


    @Override
    public void runOpMode() throws InterruptedException {
        setup();
        servoCube.setPosition(Servo.MAX_POSITION);
        double startTime = super.getRuntime();
        do {
            extenderMotor.setPower(FULLPOWER);
        } while (getRuntime() < startTime + 10);







        // Lower arm
        //servo.setPosition(MAX_POSITION);
        // wait for servo to drop
        // wait(3000);
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
            extenderMotor.setPower(FULLPOWER);
        } while (isRed == false && isBlue == false);
        //}

        // "react" - knock off the jewel according to our alliance
        if (isRed) {
            goForward();
        } else if (isBlue) {
            goBackward();
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
