package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Remote Controlled op mode
 */
@TeleOp(name = "Competition 1", group = "Remote Control")
public class Competition1OpMode extends MultiOpMode {
    @Override
    public void init() {
        addOpMode(new DriveOpMode());
        addOpMode(new CubeLiftClawOpMode());
        addOpMode(new SwitchedMotorOpMode());
        addOpMode(new RelicGripServoOpMode());
        addOpMode(new RelicExtenderMotorOpMode());
        addOpMode(new motorChangerServoOpMode());
    }
}
