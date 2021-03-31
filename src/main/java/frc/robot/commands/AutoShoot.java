package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.ShooterUtil;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrive.SwerveDriveState;
import frc.robot.subsystems.Transportation;

/**
 * Automatically shoot.
 */
public class AutoShoot extends CommandBase {
    // PIDs
    private PIDController m_adjustmentPid = new PIDController(Constants.Shooter.kAdjustPIDp, Constants.Shooter.kAdjustPIDi, Constants.Shooter.kAdjustPIDd);
    private PIDController m_rotatePid = new PIDController(Constants.Shooter.kAnglePIDp, Constants.Shooter.kAnglePIDi, Constants.Shooter.kAnglePIDd);

    // Finished command
    private boolean m_finished = false;

    // Previous Sensor Reading
    private boolean m_prevSensor = false;

    // Start timestamp
    private double m_timestamp;

    // Ever reached adjustment
    private boolean m_everAchievedAdj = false;

    // Ever reached rotation
    private boolean m_everAchievedRot = false;

    public AutoShoot() {
        this.addRequirements(Robot.shooter, Robot.transportation);
    }
    
    @Override
    public void initialize() {
        Robot.logger.addInfo("AutoShoot", "Starting AutoShoot.");

        // Setup adjustment
        this.m_adjustmentPid.setSetpoint(0.0);
        this.m_adjustmentPid.setTolerance(1.0);

        // Setup rotation
        this.m_rotatePid.setSetpoint(0.0);
        this.m_rotatePid.setTolerance(1.0);

        // Set state to swerve
        Robot.swerveDrive.setState(SwerveDriveState.OTHER_AUTO);

        // Disable transportation autonomous
        Robot.transportation.disable();

        // Reset setpoint
        this.m_everAchievedAdj = false;
        this.m_everAchievedRot = false;

        // Set start timestamp
        this.m_timestamp = Timer.getFPGATimestamp();

        // Set finished to false
        this.m_finished = false;
    }
    
    @Override
    public void execute() {
        double adj = this.m_adjustmentPid.calculate(Robot.limelight.getHorizontalOffset());
        double rot = this.m_rotatePid.calculate(Robot.swerveDrive.getAngle().getDegrees());

        SmartDashboard.putNumber("shootRot", rot);
        SmartDashboard.putNumber("shootAdj", -adj);

        if (this.m_adjustmentPid.atSetpoint()) {
            this.m_everAchievedAdj = true;
        }

        if (this.m_rotatePid.atSetpoint()) {
            this.m_everAchievedRot = true;
        }

        // if (Robot.transportation.getFeederMotorState() != Transportation.TransportMotorState.ON) {
        Robot.swerveDrive.heldSwerve(0.0, this.m_adjustmentPid.atSetpoint() ? 0.0 : -adj, this.m_rotatePid.atSetpoint() ? 0.0 : rot, false);
        // }

        if (Robot.shooter.getCurrentMode() != Shooter.ShooterMode.ENABLED) { 
            Robot.shooter.setSpeed(Constants.Shooter.kMaxRPM * 0.9);
            Robot.shooter.enable();
        }

        SmartDashboard.putBoolean("check_shooter", Robot.shooter.closeEnough());
        SmartDashboard.putBoolean("check_adjustment", this.m_adjustmentPid.atSetpoint());
        SmartDashboard.putBoolean("check_rotation", this.m_rotatePid.atSetpoint());

        // If we've reached adjustment & rotation setpoint
        System.out.println(this.m_everAchievedAdj + " " + this.m_everAchievedRot);
        if ((this.m_everAchievedAdj && this.m_everAchievedRot) && Timer.getFPGATimestamp() - this.m_timestamp >= 5.0) {
            Robot.logger.addInfo("AutoShoot", "At setpoint");
            
            // Set velocity to matching distance
            Robot.shooter.setSpeed(ShooterUtil.calculateVelocity(Robot.limelight.getDistance()));

            // If the shooter is within 120rpm
            if (Robot.shooter.closeEnough()) {
                // Turn on feeder if not
                if (Robot.transportation.getFeederMotorState() != Transportation.TransportMotorState.ON) {
                    Robot.transportation.setFeederMotorState(Transportation.TransportMotorState.ON);
                }

                // Turn on conveyor if not
                if (Robot.transportation.getConveyorMotorState() != Transportation.TransportMotorState.ON) {
                    Robot.transportation.setConveyorMotorState(Transportation.TransportMotorState.ON);
                }

                // Decrement ball count when PowerCell passes 3rd sensor
                if (Robot.transportation.getSensorThreeState() == false && this.m_prevSensor) {
                    Robot.transportation.setBallCount(Robot.transportation.getBallCount() - 1);
                }

                // Save state
                this.m_prevSensor = Robot.transportation.getSensorThreeState();

                // Finish if all balls are empty
                // if (Robot.transportation.getBallCount() <= 0) {
                //     this.m_finished = true;
                // }
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        // Disable shooter
        Robot.shooter.disable();

        // Turn off feeder
        Robot.transportation.setFeederMotorState(Transportation.TransportMotorState.OFF);

        // Turn off conveyor
        Robot.transportation.setConveyorMotorState(Transportation.TransportMotorState.OFF);

        // Switch back to swerve
        Robot.swerveDrive.setState(SwerveDriveState.SWERVE);

        // Reset ball count
        Robot.transportation.setBallCount(0);

        // Enable transportation autonomous actions
        Robot.transportation.enable();
    }
    
    @Override
    public boolean isFinished() {
        // Finished if 30secs elapses or is finished
        return Timer.getFPGATimestamp() - this.m_timestamp >= 20.0 || this.m_finished;
    }
}
