package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Relic Extender Motor", group = "test")
public class RelicExtenderMotorOpMode extends OpMode {
    private DcMotor motor = null;

    @Override
    public void init() {
        motor = Viki.getRobotPart(hardwareMap, RobotPart.relicExtenderMotor);
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        motor.setPower(gamepad2.left_stick_y);
    }
}
