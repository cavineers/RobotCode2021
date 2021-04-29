package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.control.ControllerDriveInput;
import frc.lib.control.Deadzone;
import frc.robot.Robot;
import frc.robot.subsystems.SwerveDrive.SwerveDriveState;

/**
 * Teleop Drive command.
 */
public class TeleopDrive extends CommandBase {
    private boolean m_finished = false;

    public TeleopDrive() {
        this.addRequirements(Robot.swerveDrive);
    }

    @Override
    public void initialize() {
        if (Robot.swerveDrive.getState() == SwerveDriveState.PATH) {
            Robot.logger.addInfo("TeleopDrive", "Can't start teleop swerve when a path is running.");
            this.m_finished = true;
            return;
        }
        Robot.swerveDrive.setState(SwerveDriveState.SWERVE);
        Robot.logger.addInfo("TeleopDrive", "Starting Teleop Driving");
    }

    @Override
    public void execute() {
        Robot.swerveDrive.swerve(new ControllerDriveInput(
            Deadzone.apply(Robot.robotContainer.m_joy.getRawAxis(1),  0.05),
            Deadzone.apply(Robot.robotContainer.m_joy.getRawAxis(0),  0.05),
            -Deadzone.apply(Robot.robotContainer.m_joy.getRawAxis(4), 0.05)
        ));
    }

    @Override
    public void end(boolean interrupted) {
        Robot.logger.addInfo("TeleopDrive", "Stopped Teleop Driving");
        Robot.swerveDrive.swerve(new ControllerDriveInput(0.0, 0.0, 0.0));
    }

    @Override
    public boolean isFinished() {
        return this.m_finished;
    }
}
