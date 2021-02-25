package frc.robot.paths;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import frc.lib.PathUtil;
import frc.lib.autonomous.Path;

public class ExamplePath2 extends Path {
    public ExamplePath2() {
        super(
            new Pose2d[] {
                PathUtil.plot(5.0, 5.0, 0.0),
                PathUtil.plot(2.0, 5.0, 90.0),
                PathUtil.plot(-3.0, -6.0, 180.0),
                PathUtil.plot(2.0, 2.0, -45.0),
                PathUtil.plot(1.0, 0.0, 0.0)
            }
        );
    }
}
