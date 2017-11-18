package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "Blue Forward", group = "Test")
public class JewelOpModeBlueForward extends JewelBaseOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        setup(Alliance.Blue, false);

        runAutonomous();
    }
}
