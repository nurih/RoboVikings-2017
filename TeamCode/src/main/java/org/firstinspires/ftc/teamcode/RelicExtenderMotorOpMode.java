package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/// second switched motor, actually.
///TODO: find better names
@TeleOp(name = "Relic Extender Motor", group = "test")
public class RelicExtenderMotorOpMode extends OpMode {
    private DcMotor motor = null;


    @Override
    public void init() {
        motor = Viki.getRobotPart(hardwareMap, RobotPart.relicExtenderMotor);
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        MotorSwitchState switchState = MotorChangerServoOpMode.switchState;

        if(switchState == MotorSwitchState.RELIC){
            liftRelic();
        }
        else {
            grabCube();
        }
    }

    public void liftRelic(){
        if (gamepad2.right_trigger == 1) {
            motor.setPower(1);
            motor.setPower(0);
        } else if (gamepad2.left_trigger == 1) {
            motor.setPower(1);
            motor.setPower(0);
        }

    }
    public void grabCube() {

        if (gamepad2.right_trigger == 1) {
            motor.setPower(0.7);
            motor.setPower(0);
        } else if (gamepad2.left_trigger == 1) {
            motor.setPower(-.7);
        }

    }


}
