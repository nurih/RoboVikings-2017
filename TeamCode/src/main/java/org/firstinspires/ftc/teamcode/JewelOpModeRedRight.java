package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "Red Right", group = "Test")
public class JewelOpModeRedRight extends JewelBaseOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        setup(Alliance.Red, true);

        runAutonomous();
    }
}

