package frc.robot.commands.auto;

import frc.robot.Robot;
import frc.lib.Target;
import frc.robot.subsystems.SwerveDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class GalacticSearch extends CommandBase {
    private Target m_closestPowerCell = Robot.vision.getPowerCellTarget();

    public GalacticSearch() {}

    @Override
    public void initialize() {
        double cameraLengthToBall = m_closestPowerCell.getDistance() / Math.sin(90 - m_closestPowerCell.getTx()); // hypotenuse (c) value in law of sines

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
