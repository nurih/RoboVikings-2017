//package org.firstinspires.ftc.teamcode;
//        import android.app.Activity;
//        import android.graphics.Color;
//        import android.view.View;
//       // import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//        //import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//        import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
//        import com.qualcomm.robotcore.hardware.NormalizedRGBA;
//        import com.qualcomm.robotcore.hardware.SwitchableLight;
//
//
//
//
//@TeleOp(name = "Sensor: Color", group = "Sensor")
////@Disabled
//public class ZolorSensor {
//    NormalizedColorSensor colorSensor;
//    View relativeLayout;
//
//
//
//
//    @Override public void runOpMode() throws InterruptedException {
//        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
//        relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
//        try {
//            runSample(); // actually execute the sample
//        } finally {
//            relativeLayout.post(new Runnable() {
//                public void run() {
//                    relativeLayout.setBackgroundColor(Color.WHITE);
//                }
//
//            });
//
//        }
//
//    }
//
//
//
//    protected void runSample() throws InterruptedException {
//        float[] hsvValues = new float[3];
//        final float values[] = hsvValues;
//        // bPrevState and bCurrState keep track of the previous and current state of the button
//        boolean bPrevState = false;
//        boolean bCurrState = false;
//        // Get a reference to our sensor object.
//        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");
//        // If possible, turn the light on in the beginning (it might already be on anyway,
//        // we just make sure it is if we can).
//        if (colorSensor instanceof SwitchableLight) {
//            ((SwitchableLight)colorSensor).enableLight(true);
//        }
//
//
//
//        // Wait for the start button to be pressed.
//
//        waitForStart();
//        // Loop until we are asked to stop
//        while (opModeIsActive()) {
//            // Check the status of the x button on the gamepad
//            bCurrState = gamepad1.x;
//            // If the button state is different than what it was, then act
//            if (bCurrState != bPrevState) {
//                // If the button is (now) down, then toggle the light
//                if (bCurrState) {
//                    if (colorSensor instanceof SwitchableLight) {
//                        SwitchableLight light = (SwitchableLight)colorSensor;
//                        light.enableLight(!light.isLightOn());
//                    }
//
//                }
//
//            }
//
//            bPrevState = bCurrState;
//            // Read the sensor
//            NormalizedRGBA colors = colorSensor.getNormalizedColors();
//            /** Use telemetry to display feedback on the driver station. We show the conversion
//             * of the colors to hue, saturation and value, and display the the normalized values
//             * as returned from the sensor.
//             * @see <a href="http://infohost.nmt.edu/tcc/help/pubs/colortheory/web/hsv.html">HSV</a>*/
//            Color.colorToHSV(colors.toColor(), hsvValues);
//            telemetry.addLine()
//                    .addData("H", "%.3f", hsvValues[0])
//                    .addData("S", "%.3f", hsvValues[1])
//                    .addData("V", "%.3f", hsvValues[2]);
//            telemetry.addLine()
//                    .addData("a", "%.3f", colors.alpha)
//                    .addData("r", "%.3f", colors.red)
//                    .addData("g", "%.3f", colors.green)
//                    .addData("b", "%.3f", colors.blue);
//
//            /** We also display a conversion of the colors to an equivalent Android color integer.
//
//             * @see Color */
//
//            int color = colors.toColor();
//            telemetry.addLine("raw Android color: ")
//                    .addData("a", "%02x", Color.alpha(color))
//                    .addData("r", "%02x", Color.red(color))
//                    .addData("g", "%02x", Color.green(color))
//                    .addData("b", "%02x", Color.blue(color));
//
//
//
//            // Balance the colors. The values returned by getColors() are normalized relative to the
//            // maximum possible values that the sensor can measure. For example, a sensor might in a
//            // particular configuration be able to internally measure color intensity in a range of
//            // [0, 10240]. In such a case, the values returned by getColors() will be divided by 10240
//            // so as to return a value it the range [0,1]. However, and this is the point, even so, the
//            // values we see here may not get close to 1.0 in, e.g., low light conditions where the
//            // sensor measurements don't approach their maximum limit. In such situations, the *relative*
//            // intensities of the colors are likely what is most interesting. Here, for example, we boost
//            // the signal on the colors while maintaining their relative balance so as to give more
//            // vibrant visual feedback on the robot controller visual display.
//            float max = Math.max(Math.max(Math.max(colors.red, colors.green), colors.blue), colors.alpha);
//            colors.red   /= max;
//            colors.green /= max;
//            colors.blue  /= max;
//            color = colors.toColor();
//
//            telemetry.addLine("normalized color:  ")
//                    .addData("a", "%02x", Color.alpha(color))
//                    .addData("r", "%02x", Color.red(color))
//                    .addData("g", "%02x", Color.green(color))
//  ,                  .addData("b", "%02x", Color.blue(color));
//            telemetry.update();
//
//
//
//            // convert the RGB values to HSV values.
//
//            Color.RGBToHSV(Color.red(color), Color.green(color), Color.blue(color), hsvValues);
//
//
//
//            // change the background color to match the color detected by the RGB sensor.
//            // pass a reference to the hue, saturation, and value array as an argument
//            // to the HSVToColor method.
//            relativeLayout.post(new Runnable() {
//                public void run() {
//                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
//
//                }
//
//            });
//
//        }
//
//    }
//
//}