package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Target;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Intake.IntakeMotorState;
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

    private PIDController m_rotatePid = new PIDController(Constants.ObjVision.kAnglePIDp,
            Constants.ObjVision.kAnglePIDi, Constants.ObjVision.kAnglePIDd);


    public GalacticSearch() {
        this.addRequirements(Robot.swerveDrive);
    }

    @Override
    public void initialize() {
        Robot.logger.addInfo("GalacticSearch", "Galactic Search Autonomous Command Activated.");

        //Turns Intake Subsystem On.
        // new ToggleIntake();
        Robot.intake.setMotorState(IntakeMotorState.ON);

        // PID setup.
        this.m_td.setTolerance(Constants.ObjVision.kDistancePID_Tolerance);
        this.m_a.setTolerance(Constants.ObjVision.kXPID_Tolerance);
        this.m_rotatePid.setTolerance(1.0);

        this.m_td.setSetpoint(0.0);
        this.m_a.setSetpoint(0.0);
        this.m_rotatePid.setSetpoint(0.0);

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
            double vRotatePid = this.m_rotatePid.calculate(Robot.swerveDrive.getAngle().getDegrees());

            SmartDashboard.putNumber("gs_td", td);
            SmartDashboard.putNumber("gs_tx", tx);
            SmartDashboard.putNumber("gs_a", a);
            SmartDashboard.putNumber("gs_vtd", vtd);
            SmartDashboard.putNumber("gs_va", va);
            SmartDashboard.putNumber("gs_ty", closestPowerCell.getTy());

            // Drive the robot based on the coordinates of power cell
            Robot.swerveDrive.heldSwerve(vtd, va, vRotatePid, false);
        } else {
            // Finish command if more than three balls are in the chamber
            this.m_finished = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        // Robot.swerveDrive.swerve(3?00, 0, 0, false);
        Robot.logger.addInfo("GalacticSearch", "Autonomous Galactic Search Command Ended");
        Robot.intake.setMotorState(IntakeMotorState.OFF);
    }

    @Override
    public boolean isFinished() {
        return this.m_finished;
    }
}
