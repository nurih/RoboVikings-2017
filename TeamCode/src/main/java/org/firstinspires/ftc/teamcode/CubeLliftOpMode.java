package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by Robovikings on 10/7/2017.
 */

@TeleOp(name = "joaquinisthebest" )
public class CubeLliftOpMode extends OpMode
{
    DcMotor liftMotor = null;

    /**
     * User defined init method
     * <p>
     * This method will be called once when the INIT button is pressed.
     */
    @Override
    public void init() {
        liftMotor = TeamShared.getRobotPart( hardwareMap, RobotPart.cubeLiftMotor);
        liftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    /**
     * User defined loop method
     * <p>
     * This method will be called repeatedly in a loop while this op mode is running
     */
    @Override
    public void loop() {
// 1 if Y is pressed: up.
        if (gamepad1.y) {
            liftMotor.setPower(0.5);
        }else if (gamepad2.a){
            liftMotor.setPower(-0.5);
        }else{liftMotor.setPower(0);

        }
    }
}
