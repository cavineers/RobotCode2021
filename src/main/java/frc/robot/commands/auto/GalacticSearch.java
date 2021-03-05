package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Target;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.commands.TimedToggleIntake;
import frc.robot.subsystems.SwerveDrive.SwerveDriveState;

/**
 * Galactic Search autonomous command.
 * Note: Tolerance for Vision calcs is +- 2 inches
 */
public class GalacticSearch extends CommandBase {
    // ToggleIntake Command
    private Command m_toggleIntake = new TimedToggleIntake(5); // TODO update will tuned value

    // PIDs
    private PIDController m_td = new PIDController(Constants.ObjVision.kDistancePID_P, 
            Constants.ObjVision.kDistancePID_I, Constants.ObjVision.kDistancePID_D);
    private PIDController m_a = new PIDController(Constants.ObjVision.kXPID_P, 
            Constants.ObjVision.kXPID_I, Constants.ObjVision.kXPID_D);

    public GalacticSearch() {
        addRequirements(Robot.swerveDrive);
    }

    @Override
    public void initialize() {
        Robot.logger.addInfo("GalacticSearch", "Galactic Search Autonomous Command Activated.");
        m_td.setTolerance(Constants.ObjVision.kDistancePID_Tolerance);
        m_a.setTolerance(Constants.ObjVision.kXPID_Tolerance);
    }

    @Override
    public void execute() {
        Target m_closestPowerCell = Robot.vision.getPowerCellTarget();

        // Calculations
        double td = m_closestPowerCell.getDistance(); // distance y (b) value in law of sines
        double tx = m_closestPowerCell.getTx(); // A angle in law of sines
        double a = Math.sqrt(Math.pow((td / Math.sin(90 - tx)), 2) + Math.pow(td, 2)); // distance x (a) in law of sines

        this.m_td.calculate(td);
        this.m_a.calculate(a);

        if(td >= 2 && !m_toggleIntake.isScheduled()) {
            // Drive the robot based on the coordinates of power cell
            Robot.swerveDrive.swerve(this.m_td.getSetpoint(), this.m_a.getSetpoint(), 0, false);
        } else if(!m_toggleIntake.isScheduled()) {
            this.m_toggleIntake.schedule();
        }
    }

    @Override
    public void end(boolean interrupted) {
        Robot.swerveDrive.swerve(0, 0, 0, false);
        Robot.logger.addInfo("GalacticSearch", "Autonomous Galactic Search Command Ended");
    }

    @Override
    public boolean isFinished() {
        return Robot.swerveDrive.getState() == SwerveDriveState.SWERVE;
    }
}
