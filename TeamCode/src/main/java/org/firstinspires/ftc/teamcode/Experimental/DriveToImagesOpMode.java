package org.firstinspires.ftc.teamcode.Experimental;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.RobotPart;
import org.firstinspires.ftc.teamcode.Viki;


@Autonomous(name = "Drive To Images", group = "Test")
public class DriveToImagesOpMode extends LinearOpMode {

    public static final double MOTOR_FULL_POWER = .3;
    public static final double MOTOR_MEDIUM_POWER = 0;
    public static final int MOTOR_STOP = 0;

    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = Viki.VisionKey;

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

        rightMotor = Viki.getRobotPart(hardwareMap, RobotPart.rightMotor);
        leftMotor = Viki.getRobotPart(hardwareMap, RobotPart.leftMotor);


        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        relicTrackables.activate();
        //Entering Loop
        while (opModeIsActive()) {

            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

                telemetry.addData("VuMark", "%s visible", vuMark);

                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) relicTemplate.getListener()).getPose();
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

                    BotDirection botDirection = getBotDirection(tX);
                    telemetry.addData("Target displacement", botDirection.toString());
                    boolean tooClose = (tZ> -200);

                    if (tooClose) {
                        telemetry.addLine("Too Close");
                        leftMotor.setPower(0);
                        rightMotor.setPower(0);
                    } else {
                        telemetry.addLine("Far enough");
                        switch (botDirection) {
                            case Center:
                                // drive straight
                                leftMotor.setPower(MOTOR_FULL_POWER);
                                rightMotor.setPower(MOTOR_FULL_POWER);
                                break;
                            case Right:
                                leftMotor.setPower(MOTOR_MEDIUM_POWER);
                                rightMotor.setPower(MOTOR_FULL_POWER);
                                // turn left;
                                break;
                            case Left:
                                leftMotor.setPower(MOTOR_FULL_POWER);
                                rightMotor.setPower(MOTOR_MEDIUM_POWER);
                                // turn right
                                break;
                        }
                        telemetry.addData("Right motor power", rightMotor.getPower());
                        telemetry.addData("Left motor power", leftMotor.getPower());

                    }
                }
            } else {
                telemetry.addData("VuMark", "not visible");
                leftMotor.setPower(MOTOR_STOP);
                rightMotor.setPower(MOTOR_STOP);
            }

            telemetry.update();
        }
    }

    private BotDirection getBotDirection(double translationX) {
        BotDirection botDirection;
        if (translationX > 50) {
            botDirection = BotDirection.Left;
        } else if
                (translationX < -50) {
            botDirection = BotDirection.Right;
        } else {
            botDirection = BotDirection.Center;
        }

        return botDirection;
    }

    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }

    public enum BotDirection {
        Center,
        Right,
        Left,
    }
}
