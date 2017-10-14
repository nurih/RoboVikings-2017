package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Robovikings on 10/13/2017.
 */
@TeleOp(name = "cubeliftClaw")
@SuppressWarnings("unused")
public class CubeLiftClawOpMode extends OpMode {

    Servo servo;

    double ServoPosition;
    double ServoMaxedPostion = 1;
    double ServoMinPosition = 0;

    @Override
    public void init() {
        servo = hardwareMap.get(Servo.class, "CubeLiftClaw");
        servo.setPosition(0);

    }

    @Override
    public void loop() {

    }
}



