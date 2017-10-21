package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Robovikings on 10/13/2017.
 */
@TeleOp(name = "Cube lift Claw", group = "Test")
@SuppressWarnings("unused")
public class CubeLiftClawOpMode extends OpMode {

    Servo servo;

    @Override
    public void init() {
        servo = Viki.getRobotPart(hardwareMap, RobotPart.cubeLiftClaw);
        servo.setPosition(Viki.STOP_MOTOR);
    }

    @Override
    public void loop() {
        {
            // if pressing button then open else close
            if(gamepad1.right_bumper){
                servo.setPosition(Servo.MAX_POSITION);
            }
            else if(gamepad1.left_bumper){
                servo.setPosition(Servo.MIN_POSITION);
            }
        }


    }
}



