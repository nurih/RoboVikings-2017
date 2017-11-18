package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


/*
* This op mode lifts the color sensor arm so that it doesn't interfere in
* normal driving.
* */
@TeleOp(name="Jewel Servo Op Mode")
public class JewelServoOpMode extends OpMode {
    private Servo servo = null;
    @Override
    public void init() {
        servo = Viki.getRobotPart(hardwareMap, RobotPart.jewelServo);
    }

    @Override
    public void loop() {
        servo.setPosition(Servo.MIN_POSITION);
    }
}
