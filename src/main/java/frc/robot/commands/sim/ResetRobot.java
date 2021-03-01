package frc.robot.commands.sim;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Robot;

/**
 * Reset robot to defined position.
 */
public class ResetRobot extends InstantCommand {
    private double m_xpos;
    private double m_ypos;

    public ResetRobot(double x, double y) {
        this.m_xpos = x;
        this.m_ypos = y;
    }

    @Override
    public void initialize() {
        Robot.swerveDrive.resetPosition(this.m_xpos, this.m_ypos);
    }
}
