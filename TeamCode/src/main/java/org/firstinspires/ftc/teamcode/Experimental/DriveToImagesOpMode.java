package org.firstinspires.ftc.teamcode.Experimental;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.teamcode.DriveByCamera;
import org.firstinspires.ftc.teamcode.RobotPart;
import org.firstinspires.ftc.teamcode.Viki;


@Autonomous(name = "Drive To Images", group = "Test")
public class DriveToImagesOpMode extends LinearOpMode {




    @Override
    public void runOpMode() {


        DcMotor rightMotor = Viki.getRobotPart(hardwareMap, RobotPart.rightMotor);
        DcMotor leftMotor = Viki.getRobotPart(hardwareMap, RobotPart.leftMotor);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        DriveByCamera driveByCamera = new DriveByCamera(rightMotor, leftMotor, telemetry, hardwareMap);


        telemetry.addData(">", "Press Play to start");

        telemetry.update();

        waitForStart();



        //Entering Loop
        while (opModeIsActive()) {

            int distanceGoal = -200;
            int translationGoal = -250;
            driveByCamera.driveToImage(distanceGoal, translationGoal);
        }
    }


}
