package org.firstinspires.ftc.teamcode.Experimental;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.RobotPart;
import org.firstinspires.ftc.teamcode.Viki;

@TeleOp(name = "Omni Wheel Drive")
public class OmniWheelDriveOpMode extends OpMode {
    public static final double MOTOR_POWER = 1.0;
    private DcMotor rightFront = null;
    private DcMotor rightBack = null;
    private DcMotor leftFront = null;
    private DcMotor leftBack = null;

    @Override
    public void init() {

        rightFront = Viki.getRobotPart(hardwareMap, RobotPart.rightFront);
        rightBack = Viki.getRobotPart(hardwareMap, RobotPart.rightMotor);
        leftFront = Viki.getRobotPart(hardwareMap, RobotPart.leftFront);
        leftBack = Viki.getRobotPart(hardwareMap, RobotPart.leftMotor);

        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        setWheelsForForward();
    }

    private void setWheelsForForward() {
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    private void setWheelsForRight() {
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    private void setWheelsForClockwise() {
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    private void setPowerUsingY() {
        setAllMotorPower(gamepad1.right_stick_y);
    }

    private void setPowerUsingX() {
        setAllMotorPower(gamepad1.right_stick_x);
    }

    private void setPowerUsingRightStick() {
        setAllMotorPower(gamepad1.left_stick_x);

    }

    private void setAllMotorPower(float value) {
        rightFront.setPower(value * MOTOR_POWER);
        rightBack.setPower(value * MOTOR_POWER);
        leftFront.setPower(value * MOTOR_POWER);
        leftBack.setPower(value * MOTOR_POWER);
    }

    @Override
    public void loop() {
        if (shouldMoveStraight()) {
            setWheelsForForward();
            setPowerUsingY();

        } else if (shouldMoveSidway()) {
            setWheelsForRight();
            setPowerUsingX();

        } else if (shouldRotateInPlace()) {
            setWheelsForClockwise();
            setPowerUsingRightStick();
        } else {
            setAllMotorPower(0);
        }
    }

    private boolean shouldMoveStraight() {
        return gamepad1.right_stick_y > 0 || gamepad1.right_stick_y < 0;
    }

    private boolean shouldMoveSidway() {
        return gamepad1.right_stick_x > 0 || gamepad1.right_stick_x < 0;
    }

    private boolean shouldRotateInPlace() {
        return gamepad1.left_stick_x < 0 || gamepad1.left_stick_x > 0;
    }
}
