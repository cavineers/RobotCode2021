package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Transportation;

/**
 * Toggle the feeder.
 */
public class ToggleFeeder extends CommandBase {

    // Constructor
    public ToggleFeeder() {
        addRequirements(Robot.transportation);
    }

    // Set Motor State to ON / OFF
    @Override
    public void initialize() {
        Robot.logger.addInfo("ToggleFeeder", "Feeder Toggle");
        if (Robot.transportation.getFeederMotorState() == Transportation.TransportMotorState.OFF) {
            Robot.transportation.setMotorStateFeeder(Transportation.TransportMotorState.ON);
        } else {
            Robot.transportation.setMotorStateFeeder(Transportation.TransportMotorState.OFF);
        }
    }

    @Override
    public void execute() {
        Robot.transportation.setPCLocation();
        if (Robot.transportation.getPCLocation() == 1.0) {
            Robot.transportation.setMotorStateFeeder(Transportation.TransportMotorState.ON);
        } else {
            Robot.transportation.setMotorStateFeeder(Transportation.TransportMotorState.OFF);
        }
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }
}
