package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

public abstract class JewelBaseOpMode extends LinearOpMode {
    public static final int ARM_DROP_MILLISEC = 3000;
    public static final int ARM_LIFT_MILLISEC = 1000;
    public static final int KNOCK_JEWEL_MILLISEC = 300;
    public static final long CUBE_GRIP_MILLISEC = 1000;
    private static final long LIFT_CUBE_MILLISECONDS = 800;
    final float FULLPOWER = 0.5f;
    protected long DRIVE_OFF_PLATFORM_MILLISEC = 800;
    protected long TURN_TIME_MILLISEC = 600;
    protected boolean shouldTurn;
    protected NormalizedColorSensor colorSensor;
    protected Alliance alliance;
    protected double COLOR_SENSOR_ARM_DOWN = .9;
    protected Servo colorSensorArmServo = null;
    protected DcMotor rightMotor = null;
    protected DcMotor leftMotor = null;
    protected DcMotor cubemotor = null;
    protected DcMotor liftMotor = null;
    protected Servo motorChangeServo = null;
    protected Servo servo = null;
    protected double servoPosition;
    protected DetectedColor detectedColor = DetectedColor.None;
    protected boolean lastDroveFowrard;

    public JewelBaseOpMode(Alliance alliance, boolean shouldTurn) {
        this.alliance = alliance;
        this.shouldTurn = shouldTurn;
    }

    private void setup() {
        telemetry.addLine("setup!");
        colorSensor = Viki.getRobotPart(hardwareMap, RobotPart.colorSensor);
        colorSensorArmServo = Viki.getRobotPart(hardwareMap, RobotPart.jewelServo);
        cubemotor = Viki.getRobotPart(hardwareMap, RobotPart.relicExtenderMotor);
        cubemotor.setDirection(DcMotorSimple.Direction.FORWARD);
        cubemotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor = Viki.getRobotPart(hardwareMap, RobotPart.changedMotors);
        liftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        servo = Viki.getRobotPart(hardwareMap, RobotPart.motorChangerServo);
        servo.setPosition(this.servoPosition);
        rightMotor = Viki.getRobotPart(hardwareMap, RobotPart.rightMotor);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        leftMotor = Viki.getRobotPart(hardwareMap, RobotPart.leftMotor);
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        motorChangeServo = Viki.getRobotPart(hardwareMap, RobotPart.motorChangerServo);
        motorChangeServo.setPosition(Servo.MIN_POSITION);
        telemetry.addLine("Initialized!");
    }

    private void driveFowrard(long milliseconds) throws InterruptedException {
        leftMotor.setPower(-FULLPOWER);
        rightMotor.setPower(-FULLPOWER);
        lastDroveFowrard = true;
        this.vikiWait(milliseconds);
        stopMotors();
    }

    private void driveBackward(long milliseconds) throws InterruptedException {
        leftMotor.setPower(FULLPOWER);
        rightMotor.setPower(FULLPOWER);
        lastDroveFowrard = false;
        this.vikiWait(milliseconds);
        stopMotors();
    }

    private void detectColor() throws InterruptedException {
        // Lower arm
        colorSensorArmServo.setPosition(COLOR_SENSOR_ARM_DOWN);

        // wait for servo to drop
        this.vikiWait(ARM_DROP_MILLISEC);

        // detect color
        do {
            HsvValues hsv = Viki.getHsvValues(colorSensor);
            detectedColor = detectJewelColor(hsv);

            telemetry.addData("Hsv.Hue", hsv.Hue);
            telemetry.addData("Hsv.Value", hsv.Value);
            telemetry.addData("Seeing  ", detectedColor);

            telemetry.update();
        } while (detectedColor == DetectedColor.None);
    }

    private DetectedColor detectJewelColor(HsvValues hsv) {
        if (getIsBlue(hsv)) {
            return DetectedColor.Blue;
        }
        if (getIsRed(hsv)) {
            return DetectedColor.Red;
        } else {
            return DetectedColor.None;
        }
    }

    private boolean getIsBlue(HsvValues hsv) {
        boolean result;
        telemetry.addLine("getIsBlue!");
        if (hsv.Hue < 180) {
            result = false;
        } else if (hsv.Hue > 220) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    private boolean getIsRed(HsvValues hsv) {
        telemetry.addLine("getIsRed!");
        boolean result;
        if (hsv.Hue < 5) {
            result = true;
        } else if (hsv.Hue > 320) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    private void knockJewel() throws InterruptedException {
        telemetry.addData("Seeing  ", detectedColor);

        if (detectedColor == DetectedColor.Red) {
            if (alliance == Alliance.Blue) {
                driveBackward(KNOCK_JEWEL_MILLISEC);
            } else {
                driveFowrard(KNOCK_JEWEL_MILLISEC);
            }
        }
        if (detectedColor == DetectedColor.Blue) {
            if (alliance == Alliance.Blue) {
                driveFowrard(KNOCK_JEWEL_MILLISEC);
            } else {
                driveBackward(KNOCK_JEWEL_MILLISEC);
            }
        }
        // lift arm
        colorSensorArmServo.setPosition(Servo.MIN_POSITION);
    }

    private void stopMotors() {
        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }

    private void compansateJewelKnock() throws InterruptedException {
       // compensate for jewel knock
        if ( lastDroveFowrard ) {
            driveBackward(KNOCK_JEWEL_MILLISEC);
        } else {
            driveFowrard(KNOCK_JEWEL_MILLISEC);
        }
    }

    private void driveOffPlatform() throws InterruptedException {
        // drive off platform
        if (alliance == Alliance.Blue) {
            driveBackward(DRIVE_OFF_PLATFORM_MILLISEC);
        } else {
            driveFowrard(DRIVE_OFF_PLATFORM_MILLISEC);
        }
    }

    private void turnToCrypto(Alliance alliance) throws InterruptedException {
        if (alliance == Alliance.Red) {
            leftMotor.setPower(0);
            rightMotor.setPower(1);
        } else {
            leftMotor.setPower(1);
            rightMotor.setPower(0);
        }
        // wait
        this.vikiWait(TURN_TIME_MILLISEC);
        stopMotors();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        // setup the servors, motors, sensor...
        setup();
        setSwitch();
        // allow any servos to get to initial position
        vikiWait(300);
        // autonomous sequence of operations
        grabCube();
        liftCube();
        detectColor();
        knockJewel();
        // wait for arm to lift
        vikiWait(ARM_LIFT_MILLISEC);
//        compansateJewelKnock();
        // pause to allow smooth direction switch
        vikiWait(500);
        driveOffPlatform();
        if (shouldTurn) {
            turnToCrypto(alliance);
        }
        stopMotors();
    }

    protected void grabCube() {
        // grab the cube..
        cubemotor.setPower(-0.5);
        vikiWait(CUBE_GRIP_MILLISEC);
    }

    protected void liftCube() {
        liftMotor.setPower(1);
        vikiWait(LIFT_CUBE_MILLISECONDS);
        liftMotor.setPower(0);
    }

    public void vikiWait(long milliseconds) {

        double waitTimeSeconds = milliseconds / 1000.0;
        double starttime = getRuntime();
        do {
            telemetry.addLine("waiting...");
        }
        while (getRuntime() < starttime + waitTimeSeconds);
    }
    public void setSwitch() {
        servoPosition = Servo.MIN_POSITION;
    }
}
