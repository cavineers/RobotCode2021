package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.Robot;

public class ToggleIntake extends CommandBase {
    private Intake intake;

    // Constructor
    public ToggleIntake(Intake intake) {
        addRequirements(intake);
        this.intake = intake;
    }

    // Set Motor State to ON / OFF
    @Override
    public void initialize() {
        Robot.logger.addInfo("ToggleIntake", "Intake Toggle");
        if (this.intake.getMotorState() == Intake.IntakeMotorState.OFF) {
            this.intake.setMotorState(Intake.IntakeMotorState.ON);
        } else {
            this.intake.setMotorState(Intake.IntakeMotorState.OFF);
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