package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name = "Drive", group = "Test")
public class DriveOpMode extends OpMode {
    private DcMotor rightMotor = null;
    private DcMotor leftMotor = null;

    @Override
    public void init() {
        setMotorsForForward();
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
        leftMotor.setPower(gamepad1.left_stick_y * 0.7);
        rightMotor.setPower(gamepad1.right_stick_y * 0.7);
        if ( gamepad1.x ) {
            setMotorsForForward();
        } else if ( gamepad1.y ) {
            setMotorsForReverse();
        }
    }
}