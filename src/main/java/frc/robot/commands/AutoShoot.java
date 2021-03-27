package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;
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
    private PIDController m_adjustmentPid = new PIDController(Constants.Shooter.kAdjustPIDp, Constants.Shooter.kAdjustPIDi, Constants.Shooter.kAdjustPIDd);
    private PIDController m_rotatePid = new PIDController(Constants.Shooter.kAnglePIDp, Constants.Shooter.kAnglePIDi, Constants.Shooter.kAnglePIDd);

    private boolean m_finished = false;

    private boolean m_prevSensor = false;

    private double m_timestamp;

    private boolean m_everAchievedAdj = false;

    private boolean m_everAchievedRot = false;

    public AutoShoot() {
        this.addRequirements(Robot.shooter, Robot.transportation);
    }
    
    @Override
    public void initialize() {
        this.m_adjustmentPid.setSetpoint(0.0);
        this.m_adjustmentPid.setTolerance(0.5);

        this.m_rotatePid.setSetpoint(0.0);
        this.m_rotatePid.setTolerance(1.0);

        Robot.swerveDrive.setState(SwerveDriveState.OTHER_AUTO);

        Robot.logger.addInfo("AutoShoot", "Starting AutoShoot.");

        Robot.transportation.disable();

        this.m_everAchievedAdj = false;

        this.m_everAchievedRot = false;

        this.m_timestamp = Timer.getFPGATimestamp();
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

        if (Robot.transportation.getFeederMotorState() != Transportation.TransportMotorState.ON) {
            Robot.swerveDrive.heldSwerve(0.0, this.m_everAchievedAdj ? 0.0 : -adj, this.m_everAchievedRot ? 0.0 : rot, false);
        }

        if (Robot.shooter.getCurrentMode() != Shooter.ShooterMode.ENABLED) { 
            Robot.shooter.setSpeed(Constants.Shooter.kMaxRPM * 0.9);
            Robot.shooter.enable();
        }

        SmartDashboard.putBoolean("check_shooter", Robot.shooter.closeEnough());
        SmartDashboard.putBoolean("check_adjustment", this.m_adjustmentPid.atSetpoint());
        SmartDashboard.putBoolean("check_rotation", this.m_rotatePid.atSetpoint());

        if (this.m_everAchievedAdj && this.m_everAchievedRot) {
            Robot.logger.addInfo("AutoShoot", "At setpoint");
            
            double msVel = (Units.inchesToMeters(4) * Math.PI) * (Robot.shooter.getSpeed() / 120);
            double angle = ShooterUtil.calculateHoodAngle(msVel, Constants.Vision.kFieldGoalHeightFromGround);
            SmartDashboard.putNumber("angle", angle);
            angle = MathUtil.clamp(angle, Constants.Hood.kMinimumAngle, Constants.Hood.kMaximumAngle);
            System.out.println("unclamped_angle " + angle);
            System.out.println(ShooterUtil.withinBounds(angle));
            SmartDashboard.putNumber("clamped_angle", angle);
            SmartDashboard.putBoolean("angle_bounds", ShooterUtil.withinBounds(angle));
            if (ShooterUtil.withinBounds(angle)) {
                Robot.hood.findTargetPosition(msVel);
                if (Robot.shooter.closeEnough()) {
                    System.out.println("fuclk yea");
                    if (Robot.transportation.getFeederMotorState() != Transportation.TransportMotorState.ON) {
                        Robot.transportation.setFeederMotorState(Transportation.TransportMotorState.ON);
                    }
                    if (Robot.transportation.getConveyorMotorState() != Transportation.TransportMotorState.ON) {
                        Robot.transportation.setConveyorMotorState(Transportation.TransportMotorState.ON);
                    }
                    if (Robot.transportation.getSensorThreeState() == false && this.m_prevSensor) {
                        Robot.transportation.setBallCount(Robot.transportation.getBallCount() - 1);
                    }
                    this.m_prevSensor = Robot.transportation.getSensorThreeState();
                    if (Robot.transportation.getBallCount() <= 0) {
                        this.m_finished = true;
                    }
                }
            } else {
                if (angle > Constants.Hood.kMaximumAngle) {
                    Robot.shooter.setSpeed(Robot.shooter.getSpeed() - 100);
                } else {
                    Robot.shooter.setSpeed(Robot.shooter.getSpeed() + 100);
                }
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        Robot.shooter.disable();
        Robot.transportation.setFeederMotorState(Transportation.TransportMotorState.OFF);
        Robot.transportation.setConveyorMotorState(Transportation.TransportMotorState.OFF);
        Robot.swerveDrive.setState(SwerveDriveState.SWERVE);
        Robot.transportation.setBallCount(0);
        Robot.transportation.enable();
    }
    
    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() - this.m_timestamp > 30.0 || this.m_finished;
    }
}
