package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.paths.BouncePath;
import frc.robot.subsystems.SwerveDrive.SwerveDriveState;

/**
 * Bounce path autonomous command.
 */
public class BouncePathAuto extends CommandBase {
    public BouncePathAuto() {
        this.addRequirements(Robot.swerveDrive);
    }

    @Override
    public void initialize() {
        Robot.logger.addInfo("BouncePathAuto", "Autonomous bounce path command initialized");

        // Robot.swerveDrive.followPath(new BouncePath(), false);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {
        Robot.logger.addInfo("BouncePathAuto", "Autonomous bounce path command ended");
    }

    @Override
    public boolean isFinished() {
        return Robot.swerveDrive.getState() == SwerveDriveState.SWERVE;
    }
}
