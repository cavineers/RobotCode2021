package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Target;
import frc.robot.Robot;

/**
 * Galactic Search autonomous command.
 */
public class GalacticSearch extends CommandBase {
    private Target m_closestPowerCell = Robot.vision.getPowerCellTarget();

    public GalacticSearch() {}

    @Override
    public void initialize() {
        Robot.logger.addInfo("GalacticSearch", "Galactic Search Autonomous Command Activated.");

        double cameraLengthToBall = m_closestPowerCell.getDistance() / Math.sin(90 - m_closestPowerCell.getTx()); // hypotenuse (c) value in law of sines

        // TODO initialize set path
        // TODO initialize dynamic path based on incoming distances that will change over time
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
