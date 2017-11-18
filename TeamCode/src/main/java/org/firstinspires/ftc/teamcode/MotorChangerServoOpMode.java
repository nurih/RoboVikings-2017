package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

/*
* This op mode switches between using the motor controls to be for the relic or for the cube operations.
* We only have 4 motor ports on one hub, so we re-use the 2 motor ports electronically by switching the wiring
* using a servo.
* This op mode just chooses which "mode" we're in: Relic motors or Cube motors.
* **/
public class MotorChangerServoOpMode extends OpMode {

    private Servo servo = null;
    private double servoPosition = Servo.MAX_POSITION;

    @Override
    public void init() {
        servo = Viki.getRobotPart(hardwareMap, RobotPart.motorChangerServo);
        servo.setPosition(this.servoPosition);
    }

    @Override
    public void loop() {

        if (gamepad2.x) {
            servoPosition = Servo.MIN_POSITION;
        } else if (gamepad2.y) {
            servoPosition = Servo.MAX_POSITION;
        }
        servo.setPosition(servoPosition);
        telemetry.addData("Motor selection", servoPosition);
    }
}
