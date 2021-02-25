package frc.robot.paths;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import frc.lib.PathUtil;
import frc.lib.autonomous.Path;
import edu.wpi.first.wpilibj.util.Units;

public class SlalomPath {
    public static Path getPath() {
        Pose2d[] route = new Pose2d[]{
            PathUtil.plot(Units.feetToMeters(30), Units.feetToMeters(30), 0.0), // Star
            PathUtil.plot(Units.feetToMeters(30), Units.feetToMeters(30), 0.0),
        };

        return new Path(route);
    }
}
