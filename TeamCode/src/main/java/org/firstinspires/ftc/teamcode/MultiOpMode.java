package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Multiple Op Modes in One
 * Extend this class to create an op mode made up of many op modes.
 */

public abstract class MultiOpMode extends OpMode {

    final private List<OpMode> opModeList = new ArrayList<>();

    @Override
    public void loop() {
        for (OpMode opMode : opModeList) {
            try {
                opMode.loop();
            } catch (Exception e) {
                telemetry.addLine("Failed loop() on " + opMode.toString());
                telemetry.addData("Exception", e);
            }
        }
    }

    final protected void addOpMode(OpMode op) {
        telemetry.addLine("Adding mini op mode " + op.toString());
        op.hardwareMap = this.hardwareMap;
        op.telemetry = this.telemetry;
        op.gamepad1 = this.gamepad1;
        op.gamepad2 = this.gamepad2;

        if (tryInit(op)) {
            opModeList.add(op);
        } else {
            telemetry.addLine("OP MODE IS NOT USABLE!!!" + op.getClass().getSimpleName());
        }
    }

    private boolean tryInit(OpMode opMode) {
        telemetry.addLine("Calling init() -> " + opMode.getClass().getSimpleName());

        try {
            opMode.init();
            return true;
        } catch (Exception e) {
            telemetry.addLine("FAILED init() on " + opMode.getClass().getSimpleName());
            telemetry.addData("Exception: ", e);
            return false;
        }
    }
}
