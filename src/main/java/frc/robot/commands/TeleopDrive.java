package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Deadzone;
import frc.robot.Robot;

public class TeleopDrive extends CommandBase {
    public TeleopDrive() {
        addRequirements(Robot.swerveDrive);
    }

    @Override
    public void initialize() {
		Robot.logger.addInfo("TeleopDrive", "Starting Teleop Driving");
    }

    @Override
    public void execute() {
        Robot.swerveDrive.swerve(
            -Deadzone.apply(Robot.robotContainer.joy.getRawAxis(1), 0.1), 
            -Deadzone.apply(Robot.robotContainer.joy.getRawAxis(0), 0.1), 
            Deadzone.apply(Robot.robotContainer.joy.getRawAxis(3), 0.1), 
            false);
    }

    @Override
    public void end(boolean interrupted) {
        Robot.logger.addInfo("TeleopDrive", "Stopped Teleop Driving");
        Robot.swerveDrive.swerve(0.0, 0.0, 0.0, false);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
