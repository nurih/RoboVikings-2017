package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Remote Controlled op mode
 */
@TeleOp(name = "Competition 1", group = "Remote Control")
public class Competition1OpMode extends MultiOpMode {

    /**
     * User defined init method
     * <p>
     * This method will be called once when the INIT button is pressed.
     */
    @Override
    public void init() {
        addOpMode(new DriveOpMode());
        addOpMode(new CubeLiftClawOpMode());
        addOpMode(new CubeLiftMotorOpMode());
        addOpMode(new RelicGripServoOpMode());
        addOpMode(new RelicElevatorServoOpMode());
        addOpMode(new RelicExtenderMotorOpMode());
    }
}
