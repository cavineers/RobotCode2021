package frc.robot.paths;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import frc.lib.PathUtil;
import frc.lib.autonomous.Path;

public class ExamplePath {
    public static Path getPath() {
        Pose2d[] route = new Pose2d[]{
            PathUtil.plot(0.0, 20.0, 0.0),
            PathUtil.plot(20.0, 50.0, 0.0),
            PathUtil.plot(30.0, 40.0, 0.0),
            PathUtil.plot(10.0, 80.0, 0.0)
        };

        return new Path(route);
    }
}
