package frc.lib;

import edu.wpi.first.wpilibj.geometry.Pose2d;

public class PathUtil {
    public static boolean withinTolerance(Pose2d current, Pose2d goal, double tTolerance, double rTolerance) {
        return (Math.abs(current.getX()-goal.getX())<=tTolerance) && (Math.abs(current.getRotation().getDegrees()-goal.getRotation().getDegrees())<=rTolerance);
    }
}
