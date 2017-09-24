package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ServoOpMode extends OpMode {
    private Servo servo = null;
    private double servoPosition = 0.5;
    private boolean rightBumper = false;
    private boolean leftBumper = false;

    /**
     * User defined init method
     * <p>
     * This method will be called once when the INIT button is pressed.
     */
    @Override
    public void init() {
        servo = hardwareMap.get(Servo.class, "servo");
        servo.setPosition(servoPosition);

    }

    /**
     * User defined loop method
     * <p>
     * This method will be called repeatedly in a loop while this op mode is running
     */
    @Override
    public void loop() {
        rightBumper = gamepad1.right_bumper;
        leftBumper = gamepad1.left_bumper;
        if (leftBumper == true) {
            servoPosition = 0;
        } else if (rightBumper == true) {
            servoPosition = 1;//
        } else {
            servoPosition = (float) 0.5;
        }
        servo.setPosition(servoPosition);
    }
}
