package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Robovikings on 10/13/2017.
 */
@TeleOp(name = "Cube lift Claw", group = "Test")
@SuppressWarnings("unused")
public class CubeLiftClawOpMode extends OpMode {

  //  Servo servo;
    private DcMotor cubemotor = null;

    @Override
    public void init() {
  //      servo = Viki.getRobotPart(hardwareMap, RobotPart.cubeLiftClaw);
  //      servo.setPosition(Servo.MAX_POSITION*0.25);
        cubemotor = Viki.getRobotPart(hardwareMap, RobotPart.cubeMotor);
        cubemotor.setDirection(DcMotorSimple.Direction.FORWARD);
        cubemotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
//
    @Override
    public void loop() {
        {
            // if pressing button then open else close
            if(gamepad2.right_bumper){
                //   servo.setPosition(Servo.MAX_POSITION * 0.5);
              //  cubemotor.setPower(0.5);
            }
            else if(gamepad1.left_bumper){
                //   servo.setPosition(Servo.MAX_POSITION*0.25);
               // cubemotor.setPower(-0.5);
            }
        }


    }
}



