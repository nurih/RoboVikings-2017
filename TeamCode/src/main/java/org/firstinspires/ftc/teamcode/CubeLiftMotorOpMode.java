package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name = "Cube  Lift Motor", group = "Test")
public class CubeLiftMotorOpMode extends OpMode
{
    public static final double POWER = 0.5;
    DcMotor liftMotor = null;

    @Override
    public void init() {
        liftMotor = Viki.getRobotPart(hardwareMap, RobotPart.cubeLiftMotor);
        liftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    @Override
    public void loop() {
        if (gamepad2.right_trigger) {
            liftMotor.setPower(POWER);
        }else if (gamepad2.left_trigger){
            liftMotor.setPower(-POWER);
        }else{  
            liftMotor.setPower(Viki.STOP_MOTOR);
        }
    }
}
