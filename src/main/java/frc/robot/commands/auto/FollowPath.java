package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Transform2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.PathUtil;
import frc.lib.autonomous.Path;
import frc.robot.Robot;

public class FollowPath extends CommandBase {
    private Path m_path; 

    public FollowPath(Path path) {
        this.m_path = path;
    }

    @Override
    public void initialize() {
        this.m_path.applyOffset(Robot.swerveDrive.getPosition());
    }

    @Override
    public void execute() {
        Transform2d transform = Robot.swerveDrive.getPosition().minus(this.m_path.getCurrent());
        
    }

    @Override
    public void end(boolean interrupted) {
        Robot.swerveDrive.swerve(0, 0, 0, false);
    }

    @Override
    public boolean isFinished() {
        return (!this.m_path.next() && PathUtil.withinTolerance(Robot.swerveDrive.getPosition(), this.m_path.getCurrent(), 5, 2));
    }
}
