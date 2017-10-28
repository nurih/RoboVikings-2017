package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

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
