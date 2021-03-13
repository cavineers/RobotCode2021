package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Transportation;

/**
 * Toggle the conveyor.
 */
public class ToggleConveyor extends CommandBase {

    // Constructor
    public ToggleConveyor() {
        // addRequirements(Robot.transportation);
    }

    // Set Motor State to ON / OFF
    @Override
    public void initialize() {
        Robot.logger.addInfo("ToggleConveyor", "Conveyor Toggle");
        // if (Robot.transportation.getConveyorMotorState() == Transportation.TransportMotorState.OFF) {
        //     Robot.transportation.setMotorStateConveyor(Transportation.TransportMotorState.ON);
        // } else {
        //     Robot.transportation.setMotorStateConveyor(Transportation.TransportMotorState.OFF);
        // }
    }

    @Override
    public void execute() {
        // Robot.transportation.setPCLocation();
        // if (Robot.transportation.getPCLocation() == 2.0 || Robot.transportation.getPCLocation() == 3.0) {
        //     Robot.transportation.setMotorStateConveyor(Transportation.TransportMotorState.ON);
        // }
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }
}
