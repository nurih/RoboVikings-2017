package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name = "Cube Or Relic Switched Lift Motor", group = "Test")
public class SwitchedMotorOpMode extends OpMode {
    DcMotor motor = null;

    @Override
    public void init() {
        motor = Viki.getRobotPart(hardwareMap, RobotPart.changedMotors);
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        motor.setPower(gamepad2.right_stick_y);
    }
}
