package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.subsystems.Transportation;

/**
 * A command that toggles the transportation system.
 */
public class ToggleTransportation extends InstantCommand {
    public ToggleTransportation() {
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
        if (Robot.transportation.getConveyorMotorState() == Transportation.TransportMotorState.ON) {
            Robot.transportation.setConveyorMotorState(Transportation.TransportMotorState.OFF);
        } else {
            Robot.transportation.setConveyorMotorState(Transportation.TransportMotorState.ON);
        }
    }
}
