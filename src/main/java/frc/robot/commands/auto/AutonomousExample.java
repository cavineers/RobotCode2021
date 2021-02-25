package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.paths.ExamplePath2;
import frc.robot.subsystems.SwerveDrive.SwerveDriveState;

public class AutonomousExample extends CommandBase {
    public AutonomousExample() {}

    @Override
    public void initialize() {
        Robot.logger.addInfo("AutonomousExample", "Autonomous example command initialized");

        Robot.swerveDrive.followPath(new ExamplePath2());
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
