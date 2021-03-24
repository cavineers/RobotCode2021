package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.subsystems.Transportation;

/**
 * A quick javadoc to actually put one in later.
 */
public class ToggleConveyor extends InstantCommand {
    public ToggleConveyor() {
        // this.addRequirements(Robot)
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        if (Robot.transportation.getConveyorMotorState() == Transportation.TransportMotorState.ON) {
            Robot.transportation.setConveyorMotorState(Transportation.TransportMotorState.OFF);
        } else {
            Robot.transportation.setConveyorMotorState(Transportation.TransportMotorState.ON);
        }
    }
}
