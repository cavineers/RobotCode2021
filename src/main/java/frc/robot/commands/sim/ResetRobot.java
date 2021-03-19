package frc.robot.commands.sim;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Robot;

/**
 * Reset robot to defined position.
 */
public class ResetRobot extends InstantCommand {
    private double m_xPos;
    private double m_yPos;
    private double m_rPos;

    /**
     * Set the robot's position (simulation usage only).

     * @param x X Position
     * @param y Y Position
     * @param r R Angle
     */
    public ResetRobot(double x, double y, double r) {
        this.m_xPos = x;
        this.m_yPos = y;
        this.m_rPos = r;
    }

    @Override
    public void initialize() {
        Robot.swerveDrive.resetPosition(this.m_xPos, this.m_yPos, this.m_rPos);
    }
}
