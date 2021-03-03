package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

/**
 * Flush the robot class.
 */
public class Flush extends CommandBase {
    private TimedReverseIntake m_intakeRev;

    // Constructor
    public Flush() {
        addRequirements(Robot.intake, Robot.transportation);
    }

    // Set Motor State to OFF / REVERSED
    @Override
    public void initialize() {
        Robot.logger.addInfo("Flush", "Flush Systems Starting");

        // Reverse the Intake systems
        this.m_intakeRev = new TimedReverseIntake(10);

        // Schedule tasks
        this.m_intakeRev.schedule();
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            this.m_intakeRev.cancel();
        }
    }

    @Override
    public boolean isFinished() {
        return this.m_intakeRev.isFinished();
    }
}
