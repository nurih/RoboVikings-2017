package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "Blue Right", group = "Test")
public class JewelOpModeBlueRight extends JewelBaseOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        setup(Alliance.Blue, true);

        runAutonomous();
    }

}
