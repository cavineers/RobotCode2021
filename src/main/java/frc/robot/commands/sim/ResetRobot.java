package frc.robot.commands.sim;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Robot;

public class ResetRobot extends InstantCommand {
    private double m_x;
    private double m_y;

    public ResetRobot(double x, double y) {
        this.m_x = x;
        this.m_y = y;
    }

    @Override
    public void initialize() {
        Robot.swerveDrive.resetPosition(this.m_x, this.m_y);
    }
}
