package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name = "Jewel", group = "Test")
public class JewelOpMode extends LinearOpMode {
    public static final double START_POSITION = .45;
    final float FULLPOWER = 0.5f;
    NormalizedColorSensor colorSensor;

    Alliance allience = Alliance.Red;
    private Servo servo = null;
    private DcMotor rightMotor = null;
    private DcMotor leftMotor = null;

    /**
     * Override this method and place your code here.
     * <p>
     * Please do not swallow the InterruptedException, as it is used in cases
     * where the op mode needs to be terminated early.
     *
     * @throws InterruptedException
     */

    public void setup() {
        colorSensor = Viki.getRobotPart(hardwareMap, RobotPart.colorSensor);
        servo = Viki.getRobotPart(hardwareMap, RobotPart.jewelServo);
        rightMotor = Viki.getRobotPart(hardwareMap, RobotPart.rightMotor);
        leftMotor = Viki.getRobotPart(hardwareMap, RobotPart.leftMotor);

        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        servo.setPosition(START_POSITION);

        telemetry.addLine("Initialized!");
    }


    @Override
    public void runOpMode() throws InterruptedException {
        setup();

        // Lower arm
        servo.setPosition(Servo.MAX_POSITION);

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
        } while (isRed == false && isBlue == false);


        // "react" - knock off the jewel according to our alliance
        if (allience == Alliance.Red) {
            if (isBlue) {
                goForward();
            } else if (isRed) {
                goBackward();
            } else {
                stopMotors();
            }
        } else {
            if (isBlue) {
                goBackward();
            } else if (isRed) {
                goForward();
            } else {
                stopMotors();
            }
        }


        // lift arm again

        servo.setPosition(START_POSITION);

        // driver forward a bit

        double startTime = super.getRuntime();
        do {
            goForward();
        } while (getRuntime() < startTime + 2);

        // stop motors
        stopMotors();

    }

    private void stopMotors() {
        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }

    private void goBackward() {
        leftMotor.setPower(-FULLPOWER);
        rightMotor.setPower(-FULLPOWER);
    }

    private void goForward() {
        leftMotor.setPower(FULLPOWER);
        rightMotor.setPower(FULLPOWER);
    }

    private boolean getIsBlue(HsvValues hsv) {
        boolean result;
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

