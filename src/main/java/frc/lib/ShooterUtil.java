package frc.lib;

import frc.robot.Constants;

/**
 * Shoot stuff.
 */
public class ShooterUtil {
    public static double calculateHoodAngle(double v, double h) {
        return 0.85 * (90 - (((180 / Math.PI) * Math.sin(Math.sqrt((2 * 9.8 * h) / Math.pow(v, 2))))));
    }

    /**
     * Check if angle is within the allowed bounds.

     * @param x Value to check
     * @return True if within minimum & maximum bounds
     */
    public static boolean withinBounds(double x) {
        return (x >= Constants.Hood.kMinimumAngle && x <= Constants.Hood.kMaximumAngle);
    }
}
