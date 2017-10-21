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
        rightMotor = Viki.getRobotPart(hardwareMap, RobotPart.rightMotor);
        leftMotor = Viki.getRobotPart(hardwareMap, RobotPart.leftMotor);
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);


    }

    @Override
    public void loop() {
        leftMotor.setPower(gamepad1.left_stick_y);
        rightMotor.setPower(gamepad1.right_stick_y);
    }
}