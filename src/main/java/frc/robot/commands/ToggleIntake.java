package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Intake;

/**
 * Toggle the intake.
 */
public class ToggleIntake extends CommandBase {

    /**
     * Toggle Intake.
     */
    public ToggleIntake() {
        this.addRequirements(Robot.intake);
    }

    // Set Motor State to ON / OFF
    @Override
    public void initialize() {
        Robot.logger.addInfo("ToggleIntake", "Intake Toggle");
        if (Robot.intake.getMotorState() == Intake.IntakeMotorState.OFF) {
            Robot.intake.setMotorState(Intake.IntakeMotorState.ON);
        } else {
            Robot.intake.setMotorState(Intake.IntakeMotorState.OFF);
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