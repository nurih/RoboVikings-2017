package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

@Disabled
@TeleOp(name = "Hello", group = "Test")
public class HelloOpMode extends OpMode {

    private NormalizedColorSensor colorSensor = null;



    @Override
    public void init() {


        telemetry.addLine("Initialized!");
    }


    @Override
    public void loop() {


    }
}
