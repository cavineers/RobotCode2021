package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Deadzone;
import frc.robot.Logger;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class TeleopDrive extends CommandBase {
    public TeleopDrive() {
        addRequirements(Robot.swerveDrive);
    }

    @Override
    public void initialize() {
		Logger.getInstance().addInfo("TeleopDrive", "Starting Teleop Driving");
    }

    @Override
    public void execute() {
        Robot.swerveDrive.swerve(
            -Deadzone.apply(RobotContainer.joy.getRawAxis(1), 0.1), 
            Deadzone.apply(RobotContainer.joy.getRawAxis(0), 0.1), 
            Deadzone.apply(RobotContainer.joy.getRawAxis(3), 0.1), 
            false);
    }

    @Override
    public void end(boolean interrupted) {
        Logger.getInstance().addInfo("TeleopDrive", "Stopped Teleop Driving");
        Robot.swerveDrive.swerve(0.0, 0.0, 0.0, false);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
