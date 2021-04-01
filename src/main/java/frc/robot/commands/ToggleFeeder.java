package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.subsystems.Transportation;

/**
 * A quick javadoc to actually put one in later.
 */
public class ToggleFeeder extends InstantCommand {
    public ToggleFeeder() {
        // this.addRequirements(Robot)
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        if (Robot.transportation.getFeederMotorState() == Transportation.TransportMotorState.ON) {
            Robot.transportation.setFeederMotorState(Transportation.TransportMotorState.OFF);
        } else {
            Robot.transportation.setFeederMotorState(Transportation.TransportMotorState.ON);
        }
    }
}
