package frc.lib;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.util.Units;

/**
 * Path Util Class.
 */
public class PathUtil {
    
    /**
     * Check if swerve bot is within simulation.
     */
    public static boolean withinTolerance(Pose2d current, Pose2d goal, double tTolerance, double rTolerance) {
        // System.out.println("wt_x" + Math.abs(current.getX()-goal.getX()));
        // System.out.println("wt_y" + );
        // System.out.println("wt_r" + );
        return (Math.abs(current.getX() - goal.getX()) <= tTolerance) && (Math.abs(current.getY() - goal.getY()) <= tTolerance) && (Math.abs(current.getRotation().getDegrees() - goal.getRotation().getDegrees()) <= rTolerance);
    }

    public static boolean withinTolerance2(double initial, double current, double goal, double tolerance) {
        return (Math.abs(current - (initial + goal)) <= tolerance);
    }

    /**
     * Check if swerve bot is within simulation.
     */
    public static boolean withinTolerance3(double initial, double current, double goal, double tolerance) {
        System.out.println("i " + initial);
        System.out.println("c " + current);
        System.out.println("g " + goal);
        System.out.println("a " + Math.abs(current - (initial + goal)));
        return (Math.abs(current - (initial + goal)) <= tolerance);
    }

    public static Pose2d plot(double x, double y, double rotation) {
        return new Pose2d(x, y, Rotation2d.fromDegrees(rotation));
    }

    public static Pose2d plotInches(double x, double y, double rotation) {
        return new Pose2d(Units.inchesToMeters(x), Units.inchesToMeters(y), Rotation2d.fromDegrees(rotation));
    }

    /**
     * Find and fix degree.
     */
    public static double fixDegreeOverflow(double value, boolean wrap) {
        if (wrap) {
            if (value <= -180) {
                // Add stuff.
            }
        }

        return value;
    }
}
