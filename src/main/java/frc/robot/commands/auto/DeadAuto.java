package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class DeadAuto extends CommandBase {
    public DeadAuto() {}

    @Override
    public void initialize() {
        Robot.logger.addWarn("DeadAuto", "Dead autonomous command initialized");
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }
}
