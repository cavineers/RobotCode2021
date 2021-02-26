package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.paths.SlalomPath;
import frc.robot.paths.SlalomPathSimple;
import frc.robot.subsystems.SwerveDrive.SwerveDriveState;

public class SlalomPathAuto extends CommandBase {
    public SlalomPathAuto() {}

    @Override
    public void initialize() {
        Robot.logger.addInfo("SlalomPathAuto", "Autonomous slalom path command initialized");

        Robot.swerveDrive.followPath(new SlalomPath(), false);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {
        Robot.logger.addInfo("SlalomPathAuto", "Autonomous slalom path command ended");
    }

    @Override
    public boolean isFinished() {
        return Robot.swerveDrive.getState() == SwerveDriveState.SWERVE;
    }
}
