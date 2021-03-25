package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Target;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.SwerveDrive.SwerveDriveState;

/**
 * Galactic Search autonomous command.
 * Note: Tolerance for Vision calcs is +- 2 inches
 */
public class GalacticSearch extends CommandBase {
    // Finished
    private boolean m_finished = false;

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

        //Turns Intake Subsystem On.
        // new ToggleIntake();

        // PID setup.
        m_td.setTolerance(Constants.ObjVision.kDistancePID_Tolerance);
        m_a.setTolerance(Constants.ObjVision.kXPID_Tolerance);

        m_td.setSetpoint(0);
        m_a.setSetpoint(0);

        Robot.swerveDrive.setState(SwerveDriveState.OTHER_AUTO);
    }

    @Override
    public void execute() {
        if (Robot.transportation.getBallCount() < 3) {
            Target closestPowerCell = Robot.vision.getPowerCellTarget();

            // Calculations
            double td = closestPowerCell.getDistance(); // distance y (b) value in law of sines
            double tx = closestPowerCell.getTx(); // A angle in law of sines
            double a = Math.sqrt(Math.pow((td / Math.sin(90 - tx)), 2) + Math.pow(td, 2)); // distance x (a) in law of sines

            double vtd = this.m_td.calculate(td);
            double va = -this.m_a.calculate(a);

            SmartDashboard.putNumber("gs_td", td);
            SmartDashboard.putNumber("gs_tx", tx);
            SmartDashboard.putNumber("gs_a", a);
            SmartDashboard.putNumber("gs_vtd", vtd);
            SmartDashboard.putNumber("gs_va", va);

            // Drive the robot based on the coordinates of power cell
            Robot.swerveDrive.heldSwerve(vtd, va, 0.0, false);
        } else {
            // Finish command if more than three balls are in the chamber
            this.m_finished = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        // Robot.swerveDrive.swerve(3?00, 0, 0, false);
        Robot.logger.addInfo("GalacticSearch", "Autonomous Galactic Search Command Ended");
    }

    @Override
    public boolean isFinished() {
        return this.m_finished;
    }
}
