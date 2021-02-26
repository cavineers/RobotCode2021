package frc.robot.paths;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import frc.lib.PathUtil;
import frc.lib.autonomous.Path;

public class SlalomPathSimple extends Path {
    public SlalomPathSimple() {
        super(
            new Pose2d[] {
                PathUtil.plotInches(6.0, 50.0, 0.0),
                PathUtil.plotInches(54.0, 45.0, 0.0),
                PathUtil.plotInches(18.0, 60.0, 0.0),
                PathUtil.plotInches(-25.0, 68.0, 0.0),
                PathUtil.plotInches(-55.0, 48.0, 0.0),
                PathUtil.plotInches(31.0, 45.0, 60.0),
                PathUtil.plotInches(37.0, -42.0, 60.0),
                PathUtil.plotInches(-60.0, -51.0, 60.0),
                PathUtil.plotInches(-14.0, -66.0, 0.0),
                PathUtil.plotInches(11.0, -66.0, 0.0),
                PathUtil.plotInches(60.0, -95.0, 0.0),
            }
        );
    }
}
