package org.firstinspires.ftc.teamcode;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
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
import org.firstinspires.ftc.teamcode.Experimental.BotDirection;


public class DriveByCamera {
    public static final double MOTOR_FULL_POWER = .3;
    public static final double MOTOR_MEDIUM_POWER = 0;
    public static final int MOTOR_STOP = 0;

    private final HardwareMap hardwareMap;
    public double tX;
    public double tY;
    public double tZ;
    public double rX;
    public double rY;
    public double rZ;

    DcMotor rightMotor = null;
    DcMotor leftMotor = null;
    Telemetry telemetry;


    VuforiaTrackable relicTemplate;

    public DriveByCamera(DcMotor rightMotor, DcMotor leftMotor, Telemetry telemetry, HardwareMap hardwareMap) {

        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
        this.telemetry = telemetry;
        this.hardwareMap = hardwareMap;

        relicTemplate = initializeVuforia();
    }

    public  BotDirection getBotDirection(double translationX, int translationGoal) {
        BotDirection botDirection;
        if (translationX > translationGoal + 10) {
            botDirection = BotDirection.Left;
        } else if
                (translationX < translationGoal -10) {
            botDirection = BotDirection.Right;
        } else {
            botDirection = BotDirection.Center;
        }
        telemetry.addData("Target displacement", botDirection.toString());
        return botDirection;
    }

    public static String formatMatrixString(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }

    @NonNull
    public static VuforiaLocalizer getVuforiaLocalizer(VuforiaLocalizer.Parameters parameters) {
        parameters.vuforiaLicenseKey = Viki.VisionKey;

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        return ClassFactory.createVuforiaLocalizer(parameters);
    }

    private VuforiaTrackable initializeVuforia() {
        VuforiaLocalizer.Parameters parameters = getVuforiaParameters();
        VuforiaLocalizer vuforia = DriveByCamera.getVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");

        VuforiaTrackable result = relicTrackables.get(0);
        result.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
        relicTrackables.activate();
        return result;
    }

    @NonNull
    public VuforiaLocalizer.Parameters getVuforiaParameters() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        return new VuforiaLocalizer.Parameters(cameraMonitorViewId);
    }

    public  void driveToImage(int distanceGoaL, int translationGoal) {
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

        if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

            OpenGLMatrix pose = determinePose(vuMark);

            if (pose != null) {
                VectorF translation = pose.getTranslation();
                Orientation rotation = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
                getTranslationValues(translation);
                getRotationValues(rotation);

                BotDirection botDirection = getBotDirection(tX, translationGoal);

                boolean isDistanceGoalMet = getIfDistanceGoalMet(distanceGoaL);

                if (isDistanceGoalMet) {
                    telemetry.addLine("Got there!!!");
                    stopMotors();
                } else {
                    telemetry.addLine("Still Going...");
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
            stopMotors();
        }

        telemetry.update();
    }

    private boolean getIfDistanceGoalMet(int distanceGoaL) {
        return (tZ> distanceGoaL);
    }

    @Nullable
    private OpenGLMatrix determinePose(RelicRecoveryVuMark vuMark) {
        telemetry.addData("VuMark", "%s visible", vuMark);

        OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) relicTemplate.getListener()).getPose();
        telemetry.addData("Pose", DriveByCamera.formatMatrixString(pose));
        return pose;
    }

    private void stopMotors() {
        leftMotor.setPower(MOTOR_STOP);
        rightMotor.setPower(MOTOR_STOP);
    }

    private void getRotationValues(Orientation rot) {
        // Extract the rotational components of the target relative to the robot
        rX = Math.round(rot.firstAngle);
        rY = Math.round(rot.secondAngle);
        rZ = Math.round(rot.thirdAngle);

        telemetry.addData("rotation X", rX);
        telemetry.addData("rotation Y", rY);
        telemetry.addData("rotation Z", rZ);
    }

    private void getTranslationValues(VectorF trans) {
        // Extract the X, Y, and Z components of the offset of the target relative to the robot
        tX = Math.round(trans.get(0));
        tY = Math.round(trans.get(1));
        tZ = Math.round(trans.get(2));

        telemetry.addData("translation X", tX);
        telemetry.addData("translation Y", tY);
        telemetry.addData("translation Z", tZ);
    }

}
