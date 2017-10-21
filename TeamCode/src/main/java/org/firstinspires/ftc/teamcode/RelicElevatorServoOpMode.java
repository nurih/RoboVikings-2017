package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Relic Elevator Servo", group = "Test")
public class RelicElevatorServoOpMode extends OpMode {
    public static final double errorRange = 0.1;
    private Servo elevatorServo = null;
    private double servoPosition = Servo.MAX_POSITION;

    @Override
    public void init() {
        elevatorServo = Viki.getRobotPart(hardwareMap, RobotPart.relicElevatorServo);
        elevatorServo.setPosition(this.servoPosition);
    }

    @Override
    public void loop() {
        if (gamepad2.right_stick_y > errorRange) {
            servoPosition = Math.max( Servo.MAX_POSITION,servoPosition + 0.01);
        } else if (gamepad2.right_stick_y < errorRange) {
            servoPosition = Math.min( Servo.MIN_POSITION, servoPosition - 0.01);
        }
        elevatorServo.setPosition(servoPosition);
    }
}
