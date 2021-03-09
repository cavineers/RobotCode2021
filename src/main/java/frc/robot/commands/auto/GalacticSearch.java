package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Target;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.commands.TimedToggleIntake;

/**
 * Galactic Search autonomous command.
 * Note: Tolerance for Vision calcs is +- 2 inches
 */
public class GalacticSearch extends CommandBase {
    // ToggleIntake Command
    private Command m_toggleIntake = new TimedToggleIntake(5); // TODO Should be replaced with a command that uses feedback

    // PIDs
    private PIDController m_td = new PIDController(Constants.ObjVision.kDistancePID_P, 
            Constants.ObjVision.kDistancePID_I, Constants.ObjVision.kDistancePID_D);
    private PIDController m_a = new PIDController(Constants.ObjVision.kXPID_P, 
            Constants.ObjVision.kXPID_I, Constants.ObjVision.kXPID_D);

    public GalacticSearch() {
        this.addRequirements(Robot.swerveDrive);
    }

    @Override
    public void initialize() {
        Robot.logger.addInfo("GalacticSearch", "Galactic Search Autonomous Command Activated.");

        m_td.setTolerance(Constants.ObjVision.kDistancePID_Tolerance);
        m_a.setTolerance(Constants.ObjVision.kXPID_Tolerance);

        m_td.setSetpoint(0);
        m_a.setSetpoint(0);
    }

    @Override
    public void execute() {
        Target closestPowerCell = Robot.vision.getPowerCellTarget();

        // Calculations
        double td = closestPowerCell.getDistance(); // distance y (b) value in law of sines
        double tx = closestPowerCell.getTx(); // A angle in law of sines
        double a = Math.sqrt(Math.pow((td / Math.sin(90 - tx)), 2) + Math.pow(td, 2)); // distance x (a) in law of sines

        this.m_td.calculate(td);
        this.m_a.calculate(a);

        if (!this.m_toggleIntake.isScheduled() && this.m_td.atSetpoint() && this.m_a.atSetpoint()) {
            this.m_toggleIntake.schedule();
        } else if (this.m_toggleIntake.isFinished()) {
            this.m_toggleIntake = new TimedToggleIntake(5); // TODO Should be replaced with a command that uses feedback
        } else if (!this.m_toggleIntake.isScheduled()) {
            // Drive the robot based on the coordinates of power cell
            Robot.swerveDrive.swerve(this.m_td.calculate(td), this.m_a.calculate(a), 0, false);
        }
    }

    @Override
    public void end(boolean interrupted) {
        Robot.swerveDrive.swerve(0, 0, 0, false);
        Robot.logger.addInfo("GalacticSearch", "Autonomous Galactic Search Command Ended");
    }

    @Override
    public boolean isFinished() {
        return false;
        // return Robot.transportation.getBallCount() == 3; // TODO: Implement this missing method
    }
}
