package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Relic Grip Servo", group = "Test")
public class RelicGripServoOpMode extends OpMode {
    private Servo servo = null;
    private double servoPosition = Servo.MAX_POSITION;

    @Override
    public void init() {
        servo = Viki.getRobotPart(hardwareMap, RobotPart.relicGripServo);
        servo.setPosition(this.servoPosition);
    }

    @Override
    public void loop() {
        boolean rightBumper = gamepad2.right_bumper;
        boolean leftBumper = gamepad2.left_bumper;

        if (leftBumper) {
            servoPosition = Servo.MIN_POSITION;
        } else if (rightBumper) {
            servoPosition = Servo.MAX_POSITION;
        }
        servo.setPosition(servoPosition);
    }
}
