package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
//import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name="Drive To Images", group ="Concept")
@SuppressWarnings("unused")
public class ConceptVuMarkIdentification extends LinearOpMode {
    public enum BotDirection{
        center, right, left, notVisible
    }
    boolean shouldTurnRight = false;
    boolean shouldTurnLeft = false;
    boolean shouldGoForward = false;
    OpenGLMatrix lastLocation = null;

    VuforiaLocalizer vuforia;
    @Override public void runOpMode() {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = TeamShared.VisionKey;

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();
         DcMotor rightMotor = null;
         DcMotor leftMotor = null;

        rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");
        leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        relicTrackables.activate();
        //Entering Loop
        while (opModeIsActive()) {

            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

                telemetry.addData("VuMark", "%s visible", vuMark);

                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getPose();
                telemetry.addData("Pose", format(pose));
                if (pose != null) {
                    VectorF trans = pose.getTranslation();
                    Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                    // Extract the X, Y, and Z components of the offset of the target relative to the robot
                    double tX = Math.round(trans.get(0));
                    double tY = Math.round(trans.get(1));
                    double tZ = Math.round(trans.get(2));

                    telemetry.addData("translation X", tX);
                    telemetry.addData("translation Y", tY);
                    telemetry.addData("translation Z", tZ);

                    // Extract the rotational components of the target relative to the robot
                    double rX = Math.round(rot.firstAngle);
                    double rY = Math.round(rot.secondAngle);
                    double rZ = Math.round(rot.thirdAngle);

                    telemetry.addData("rotation X", rX);
                    telemetry.addData("rotation Y", rY);
                    telemetry.addData("rotation Z", rZ);
                    double X = tX;
                    double Y = tY;
                    double Z = tZ;

                    shouldGoForward = ( Z < -100);

                    if (X > 50){
                        shouldTurnRight = false;
                        shouldTurnLeft = true;
                    }
                    else if (X < -50){
                        shouldTurnRight = true;
                        shouldTurnLeft = false;
                    }
                    else {
                        shouldTurnRight = false;
                        shouldTurnLeft = false;
                    }

                    if(shouldGoForward) {
                        if (shouldTurnRight == true && shouldTurnLeft == false) {
                            leftMotor.setPower(-.1);
                            rightMotor.setPower(.5);
                        } else if (shouldTurnRight == false && shouldTurnLeft == true) {
                            leftMotor.setPower(-.5);
                            rightMotor.setPower(.1);
                        } else if (shouldTurnRight == false && shouldTurnLeft == false) {
                            leftMotor.setPower(-.5);
                            rightMotor.setPower(.75);
                        }
                    }else{
                        leftMotor.setPower(0);
                        rightMotor.setPower(0);
                    }
                }
            }
            else {
                telemetry.addData("VuMark", "not visible");
                leftMotor.setPower(.5);
                rightMotor.setPower(-.25);
            }

            telemetry.update();
        }
    }
    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }

}