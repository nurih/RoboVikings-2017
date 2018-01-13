package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * This class contains all the controllable robot parts that we control
 **/

public enum RobotPart {

    /**
    * Left Drive Motor
    */
    leftMotor(DcMotor.class),

    /**
     * Right Drive Motor
     */
    rightMotor(DcMotor.class),

    /**
     * Front Right Drive Motor
     */
    rightFront(DcMotor.class),

    /**
     * Back Right Drive Motor
     */
    rightBack(DcMotor.class),

    /**
     * Front Left Drive Motor
     */
    leftFront(DcMotor.class),

    /**
     * Back Left Drive Motor
     */
    leftBack(DcMotor.class),

    /**
     * Jewel servo
     */
    jewelServo(Servo.class),

    /**
    * Relic Extender Motor
    */
    relicExtenderMotor(DcMotor.class),

    /**
     * cube grabber Motor
     */
    cubeMotor(DcMotor.class),


    /**
     * Relic Elevator Motor and Cube lift Motor
     */
    changedMotors(DcMotor.class),

    /**
    * Servo to grab relic
    */
    relicGripServo(Servo.class),

    /**
     * Servo to grab relic
     */
    motorChangerServo(Servo.class),

    /**
     * Servo to grab cubes
     */
    cubeLiftClaw(Servo.class),

    /**
    * Color Sensor
    */
    colorSensor(NormalizedColorSensor.class),;

    private Class<? extends HardwareDevice> _partType;

    RobotPart(Class<? extends HardwareDevice> partType) {
        _partType = partType;
    }

    public Class<? extends HardwareDevice> getPartType() {
        return _partType;
    }
}
