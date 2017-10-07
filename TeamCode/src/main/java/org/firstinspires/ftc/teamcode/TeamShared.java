package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class TeamShared {
    public static final String VisionKey = "AbQajKn/////AAAAGU7i3QBuOUjSp+FIwylUn4R1t6lifGcZsFjRQgSVrzy6o0q2+awGv2OiUTS+JJDAP1cPjzy8Qqaa+W0Kp1y+wDyNNJzXPKTk9zpoeA6tCnaH1N7xsfUz8DxBRZmipkzHUWSCwCkslVlvf71X4HXh3tqIJetRchP55t26A3yfgQHBZN6aMMGXR/DWLNv1zI8+t7O4dml5kmHkZLG8yLOr9G8jWUUt7A7e4eoWLxkFm7JE+DTBdIH3dSekVfcSx4tZ09/bDL4fATsN6oom4YzDeWDUaC9M4C+/7MBDLaG7dhtSs6aXhhcSfD3GF1mb1KMju4nO9xuM9ehbCTNJlyt/uHAihVmRtVu08IeVSTO+XsvS";


    public static <T> T getRobotPart(HardwareMap map, RobotPart part) {
        String deviceName = part.name();

        try {
            T result = (T) map.get(part.getPartType(), deviceName);
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}

