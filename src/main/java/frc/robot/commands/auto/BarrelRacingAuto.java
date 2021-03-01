package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.paths.BarrelRacingPath;
import frc.robot.subsystems.SwerveDrive.SwerveDriveState;

public class BarrelRacingAuto extends CommandBase {
    public BarrelRacingAuto() {}

    @Override
    public void initialize() {
        Robot.logger.addInfo("BarrelRacingAuto", "Autonomous barrel racing path command initialized");

        Robot.swerveDrive.followPath(new BarrelRacingPath(), false);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {
        Robot.logger.addInfo("BarrelRacingAuto", "Autonomous barrel racing path command ended");
    }

    @Override
    public boolean isFinished() {
        return Robot.swerveDrive.getState() == SwerveDriveState.SWERVE;
    }
}
