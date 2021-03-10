package frc.lib;

/**
 * Shoot stuff.
 */
public class ShooterUtil {
    public static double calculateHoodAngle(double v, double h) {
        return ((180 / Math.PI) * Math.sin(Math.sqrt((2 * 9.8 * h) / Math.pow(v, 2))));
    }
}
