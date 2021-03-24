package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Intake;

/**
 * Temp javadoc.
 */
public class DropIntake extends CommandBase {
    private double m_startTime;

    public DropIntake() {
        this.addRequirements(Robot.intake);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        this.m_startTime = Timer.getFPGATimestamp();
        Robot.intake.setMotorState(Intake.IntakeMotorState.ON);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {}

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.intake.setMotorState(Intake.IntakeMotorState.OFF);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() - this.m_startTime > 0.2;
    }
}
