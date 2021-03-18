package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Intake;

/**
 * Toggle the intake into reverse mode.
 */
public class ToggleReverseIntake extends CommandBase {
    
    // Constructor
    public ToggleReverseIntake() {
        this.addRequirements(Robot.intake);
    }

    // Set Motor State to OFF / REVERSED
    @Override
    public void initialize() {
        Robot.logger.addInfo("ToggleReverseIntake", "Reverse Intake Toggle");
        if (Robot.intake.getMotorState() == Intake.IntakeMotorState.OFF) {
            Robot.intake.setMotorState(Intake.IntakeMotorState.REVERSED);
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
