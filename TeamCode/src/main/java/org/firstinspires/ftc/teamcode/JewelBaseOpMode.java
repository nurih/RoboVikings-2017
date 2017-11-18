package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

public abstract class JewelBaseOpMode extends LinearOpMode {
    public static final int ARM_DROP_MILLIS = 3 * 1000;
    public static final int KNOCK_JEWEL_MILLISEC = 300;
    final float FULLPOWER = 0.5f;
    protected long DRIVE_OFF_PLATFORM_TIME = 1700;
    protected long TURN_TIME_MILLISEC = 500;
    protected boolean shouldTurn;
    protected NormalizedColorSensor colorSensor;
    protected Alliance alliance;
    protected double COLOR_SENSOR_ARM_DOWN = .9;
    protected Servo colorSensorArmServo = null;
    protected DcMotor rightMotor = null;
    protected DcMotor leftMotor = null;
    protected boolean isRedJewel;
    protected boolean isBlueJewel;
    protected boolean lastDroveFowrard;

    public void setup(Alliance alliance, boolean shouldTurn) {
        telemetry.addLine("setup!");
        this.alliance = alliance;
        this.shouldTurn = shouldTurn;
        colorSensor = Viki.getRobotPart(hardwareMap, RobotPart.colorSensor);
        colorSensorArmServo = Viki.getRobotPart(hardwareMap, RobotPart.jewelServo);
        rightMotor = Viki.getRobotPart(hardwareMap, RobotPart.rightMotor);
        leftMotor = Viki.getRobotPart(hardwareMap, RobotPart.leftMotor);

        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addLine("Initialized!");
    }

    protected void driveFowrard(long milliseconds) throws InterruptedException {
        leftMotor.setPower(-FULLPOWER);
        rightMotor.setPower(-FULLPOWER);
        lastDroveFowrard = true;
        this.wait(milliseconds);
    }

    protected void driveBackward(long milliseconds) throws InterruptedException {
        leftMotor.setPower(FULLPOWER);
        rightMotor.setPower(FULLPOWER);
        lastDroveFowrard = false;
        this.wait(milliseconds);
    }

    protected void detectColor() throws InterruptedException {
        // Lower arm
        colorSensorArmServo.setPosition(COLOR_SENSOR_ARM_DOWN);

        // wait for servo to drop
        this.wait(ARM_DROP_MILLIS);

        // detect color
        do {
            HsvValues hsv = Viki.getHsvValues(colorSensor);
            isRedJewel = getIsRed(hsv);
            isBlueJewel = getIsBlue(hsv);
            telemetry.addData("Hsv.Hue", hsv.Hue);
            telemetry.addData("Hsv.Value", hsv.Value);
            telemetry.addData("Seeing Blue ", isBlueJewel);
            telemetry.addData("Seeing Red ", isRedJewel);
            telemetry.update();
        } while (isRedJewel == false && isBlueJewel == false);
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

    protected void knockJewel() throws InterruptedException {
        if (isBlueJewel) {
            if (alliance == Alliance.Blue) {
                driveBackward(KNOCK_JEWEL_MILLISEC);
            } else {
                driveFowrard(KNOCK_JEWEL_MILLISEC);
            }
        }
        if (isRedJewel) {
            if (alliance == Alliance.Blue) {
                driveFowrard(KNOCK_JEWEL_MILLISEC);
            } else {
                driveBackward(KNOCK_JEWEL_MILLISEC);
            }
        }
        // lift arm
        colorSensorArmServo.setPosition(Servo.MIN_POSITION);
    }

    protected void stopMotors() {
        leftMotor.setPower(0);
        rightMotor.setPower(0);

    }

    protected void compansateJewelKnock() throws InterruptedException {
        // compensate for jewel knock
        if (lastDroveFowrard) {
            driveFowrard(KNOCK_JEWEL_MILLISEC);
        } else {
            driveBackward(KNOCK_JEWEL_MILLISEC);
        }
    }

    protected void driveOffPlatform() throws InterruptedException {
        // drive off platform
        if (alliance == Alliance.Blue) {
            driveBackward(DRIVE_OFF_PLATFORM_TIME);
        } else {
            driveFowrard(DRIVE_OFF_PLATFORM_TIME);
        }
    }

    protected void turnToCrypto(Alliance alliance) throws InterruptedException {
        if (alliance == Alliance.Red) {
            leftMotor.setPower(0);
            rightMotor.setPower(1);
        } else {
            leftMotor.setPower(1);
            rightMotor.setPower(0);
        }
        // wait
        this.wait(TURN_TIME_MILLISEC);
        stopMotors();

    }

    protected void runAutonomous() throws InterruptedException {
        detectColor();

        knockJewel();

        compansateJewelKnock();

        driveOffPlatform();

        if (shouldTurn) {
            turnToCrypto(alliance);
        }

        stopMotors();
    }
}

