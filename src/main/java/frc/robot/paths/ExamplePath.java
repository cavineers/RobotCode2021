package frc.robot.paths;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import frc.lib.PathUtil;
import frc.lib.autonomous.Path;

public class ExamplePath {
    public static Path getPath() {
        Pose2d[] route = new Pose2d[]{
            PathUtil.plot(1.0, 1.0, 0.0),
            PathUtil.plot(2.0, 5.0, 0.0),
            PathUtil.plot(3.0, 6.0, 0.0),
            PathUtil.plot(1.0, 2.0, 0.0)
        };

        return new Path(route);
    }
}
