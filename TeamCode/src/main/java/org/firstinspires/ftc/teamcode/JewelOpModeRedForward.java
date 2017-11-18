package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "Red Forward", group = "Test")
public class JewelOpModeRedForward extends JewelBaseOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        setup(Alliance.Red, false);

        runAutonomous();
    }
}

