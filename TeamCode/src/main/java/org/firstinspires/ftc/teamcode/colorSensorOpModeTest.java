package org.firstinspires.ftc.teamcode;

import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

@TeleOp(name = "Color Sensor op mode test", group = "Test")
public class colorSensorOpModeTest extends OpMode {
    private NormalizedColorSensor colorSensor;

    @Override
    public void init() {
        View relativeLayout;
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight) colorSensor).enableLight(false);
        }
    }

    @Override
    public void loop() {

        NormalizedRGBA RGBcolors = colorSensor.getNormalizedColors();

        telemetry.addLine()
                .addData("a", "%02x", RGBcolors.alpha)
                .addData("r", "%02x", RGBcolors.red)
                .addData("g", "%02x", RGBcolors.green)
                .addData("b", "%02x", RGBcolors.blue);

        Float red = RGBcolors.red;
        Float blue = RGBcolors.blue;
        Float green = RGBcolors.green;
        Float alpha = RGBcolors.alpha;
    }
}
