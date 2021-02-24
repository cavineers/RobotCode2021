package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.geometry.Transform2d;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.PathUtil;
import frc.lib.autonomous.Path;
import frc.robot.Constants;
import frc.robot.Robot;

public class FollowPath extends CommandBase {
    private Path m_path; 

    private TrapezoidProfile.Constraints m_translationalConstraints = new TrapezoidProfile.Constraints(Constants.Swerve.kMaxVelocity, Constants.Swerve.kMaxAcceleration);
    private TrapezoidProfile.Constraints m_rotationalConstraints = new TrapezoidProfile.Constraints(Constants.Swerve.kMaxRotateSpeed, Constants.Swerve.kMaxRotateAcceleration);
    private TrapezoidProfile.State m_xSetpoint = new TrapezoidProfile.State();
    private TrapezoidProfile.State m_ySetpoint = new TrapezoidProfile.State();
    private TrapezoidProfile.State m_rSetpoint = new TrapezoidProfile.State();
    private TrapezoidProfile.State m_xGoal;
    private TrapezoidProfile.State m_yGoal;
    private TrapezoidProfile.State m_rGoal;
    private double m_time;

    public FollowPath(Path path) {
        this.m_path = path;
    }

    @Override
    public void initialize() {
        this.m_path.applyOffset(Robot.swerveDrive.getPosition());
    }

    @Override
    public void execute() {
        if (PathUtil.withinTolerance(Robot.swerveDrive.getPosition(), this.m_path.getCurrent(), Constants.AutoPath.kTranslationTolerance, Constants.AutoPath.kRotationalTolerance)) {
            this.m_path.up();
            Transform2d transform = Robot.swerveDrive.getPosition().minus(this.m_path.getCurrent());
            // double distance = Math.sqrt(Math.pow(transform.getX(), 2)+Math.pow(transform.getY(), 2));
            this.m_xGoal = new TrapezoidProfile.State(transform.getX(), this.m_path.next() ? 0.25 : 0.0);
            this.m_yGoal = new TrapezoidProfile.State(transform.getY(), this.m_path.next() ? 0.25 : 0.0);
            this.m_rGoal = new TrapezoidProfile.State(transform.getRotation().getDegrees(), 0.0);
            this.m_time = Timer.getFPGATimestamp();
        }
        TrapezoidProfile xProfile = new TrapezoidProfile(this.m_translationalConstraints, this.m_xGoal, this.m_xSetpoint);
        TrapezoidProfile yProfile = new TrapezoidProfile(this.m_translationalConstraints, this.m_yGoal, this.m_ySetpoint);
        TrapezoidProfile rProfile = new TrapezoidProfile(this.m_rotationalConstraints, this.m_rGoal, this.m_rSetpoint);
        this.m_xSetpoint = xProfile.calculate(this.m_time);
        this.m_ySetpoint = yProfile.calculate(this.m_time);
        this.m_rSetpoint = rProfile.calculate(this.m_time);
        Robot.swerveDrive.swerve(this.m_ySetpoint.velocity/Constants.Swerve.kMaxVelocity, this.m_xSetpoint.velocity/Constants.Swerve.kMaxVelocity, this.m_rSetpoint.velocity/Constants.Swerve.kMaxRotateSpeed, false);
    }

    @Override
    public void end(boolean interrupted) {
        Robot.swerveDrive.swerve(0, 0, 0, false);
    }

    @Override
    public boolean isFinished() {
        return (!this.m_path.next() && PathUtil.withinTolerance(Robot.swerveDrive.getPosition(), this.m_path.getCurrent(), Constants.AutoPath.kTranslationTolerance, Constants.AutoPath.kRotationalTolerance));
    }
}
