package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * This class contains all the controllable robot parts that we control
 **/

public enum RobotPart {

    /*
    * Left Drive Motor
    * */
    leftMotor(DcMotor.class),

    /**
     * Right Drive Motor

     */
    rightMotor(DcMotor.class),

    /*
    * Cube Lift Motor*/
    cubeLiftMotor(DcMotor.class),
    /*
    * Color Sensor
    * */
    colorSensor(NormalizedColorSensor.class),

    ;

    private Class<? extends HardwareDevice> _partType;

    RobotPart(Class<? extends HardwareDevice> partType) {
        _partType = partType;
    }

    public Class<? extends HardwareDevice> getPartType() {
        return _partType;
    }
}
