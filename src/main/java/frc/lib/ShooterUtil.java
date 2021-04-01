package frc.lib;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    /**
     * Calculate the velocity needed to fire with accuracy.

     * @param x Distance between robot and target.
     * @return Velocity required to shoot at target.
     */
    public static double calculateVelocity(double x) {
        return Math.sqrt(((Math.pow(7.0, 2) / Math.pow((Math.sin(Math.atan(2.5 / x))) * SmartDashboard.getNumber("shooter_constant", 0.0), 2))));
    }
}
