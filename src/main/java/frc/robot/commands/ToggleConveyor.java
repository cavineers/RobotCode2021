package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Transportation;
import frc.robot.Robot;

public class ToggleConveyor extends CommandBase {

    // Constructor
    public ToggleConveyor() {
        addRequirements(Robot.transportation);
    }

    // Set Motor State to ON / OFF
    @Override
    public void initialize() {
        Robot.logger.addInfo("ToggleConveyor", "Conveyor Toggle");
        if (Robot.transportation.getConveyorMotorState() == Transportation.TransportMotorState.OFF) {
            Robot.transportation.setMotorStateConveyor(Transportation.TransportMotorState.ON);
        } else {
            Robot.transportation.setMotorStateConveyor(Transportation.TransportMotorState.OFF);
        }
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }
}
