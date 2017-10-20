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
        servo.setPosition(0);

    }

    @Override
    public void loop() {
        {
            // if pressing button then open else close
            if(gamepad2.b){
                servo.setPosition(Servo.MAX_POSITION);
            }
            else{
                servo.setPosition(Servo.MIN_POSITION);
            }
        }


    }
}



