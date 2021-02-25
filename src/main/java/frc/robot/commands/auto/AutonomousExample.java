package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.paths.ExamplePath;
import frc.robot.subsystems.SwerveDrive.SwerveDriveState;

public class AutonomousExample extends CommandBase {
    public AutonomousExample() {}

    @Override
    public void initialize() {
        Robot.logger.addInfo("AutonomousExample", "Autonomous example command initialized");

        Robot.swerveDrive.followPath(ExamplePath.getPath());
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {
        Robot.logger.addWarn("AutonomousExample", "Autonomous example command ended");
    }

    @Override
    public boolean isFinished() {
        return Robot.swerveDrive.getState() == SwerveDriveState.SWERVE;
    }
}