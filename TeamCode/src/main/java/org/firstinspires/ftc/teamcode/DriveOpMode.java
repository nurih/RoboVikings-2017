package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name = "Drive", group = "Test")
public class DriveOpMode extends OpMode {
    public static final double MOTOR_POWER = 1.0;
    private DcMotor rightMotor = null;
    private DcMotor leftMotor = null;

    @Override
    public void init() {
        setMotorsForForward();
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    private void setMotorsForForward() {
        rightMotor = Viki.getRobotPart(hardwareMap, RobotPart.rightMotor);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        leftMotor = Viki.getRobotPart(hardwareMap, RobotPart.leftMotor);
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    private void setMotorsForReverse() {
        rightMotor = Viki.getRobotPart(hardwareMap, RobotPart.leftMotor);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        leftMotor = Viki.getRobotPart(hardwareMap, RobotPart.rightMotor);
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    @Override
    public void loop() {
        leftMotor.setPower(gamepad1.left_stick_y * MOTOR_POWER);
        rightMotor.setPower(gamepad1.right_stick_y * MOTOR_POWER);
        if ( gamepad1.x ) {
            setMotorsForForward();
        } else if ( gamepad1.y ) {
            setMotorsForReverse();
        }
    }
}