package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name="Drive OpMode Test")
public class DriveOpMode extends OpMode {
    private DcMotor rightMotor = null;
    private DcMotor leftMotor = null;
    private float leftJoystickPosition = 0;
    private float rightJoystickPosition = 0;
    /**
     * User defined init method
     * <p>
     * This method will be called once when the INIT button is pressed.
     */
    @Override
    public void init() {
        rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");
        leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);


    }

    /**
     * User defined loop method
     * <p>
     * This method will be called repeatedly in a loop while this op mode is running
     */
    @Override
    public void loop() {

        leftJoystickPosition = gamepad1.left_stick_y;
        rightJoystickPosition = gamepad1.right_stick_y;

        leftMotor.setPower(leftJoystickPosition);
        rightMotor.setPower(-rightJoystickPosition);
    }
}