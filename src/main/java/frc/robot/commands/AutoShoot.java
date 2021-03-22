package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.ShooterUtil;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Transportation;

/**
 * Automatically shoot.
 */
public class AutoShoot extends CommandBase {
    private PIDController m_adjustmentPid = new PIDController(Constants.Shooter.kAdjustPIDp, Constants.Shooter.kAdjustPIDi, Constants.Shooter.kAdjustPIDd);

    private boolean m_finished = false;

    private boolean m_prevSensor = false;

    private double m_timestamp;

    public AutoShoot() {
        this.addRequirements(Robot.shooter, Robot.transportation);
    }
    
    @Override
    public void initialize() {
        this.m_adjustmentPid.setSetpoint(0.0);
        this.m_adjustmentPid.setTolerance(1.0);
    }
    
    @Override
    public void execute() {
        double adj = this.m_adjustmentPid.calculate(Robot.limelight.getHorizontalOffset());
        Robot.swerveDrive.swerve(0.0, adj, 0.0, false);
        if (Robot.shooter.getCurrentMode() != Shooter.ShooterMode.ENABLED) { 
            Robot.shooter.enable();
            Robot.shooter.setSpeed(Constants.Shooter.kMaxRPM * 0.9);
        }

        if (this.m_adjustmentPid.atSetpoint()) {
            if (Robot.transportation.getBallCount() >= 1) {
                double angle = ShooterUtil.calculateHoodAngle(Robot.shooter.getSpeed(), Constants.Vision.kFieldGoalHeightFromGround);
                if (ShooterUtil.withinBounds(angle)) {
                    Robot.hood.findTargetPosition(Robot.shooter.getSpeed());
                    if (Robot.hood.atTarget() && Robot.shooter.closeEnough()) {
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

                        if (Robot.transportation.getBallCount() == 0) {
                            this.m_timestamp = Timer.getFPGATimestamp();
                        }
                    }
                } else {
                    if (angle > Constants.Hood.kMaximumAngle) {
                        Robot.shooter.setSpeed(Robot.shooter.getSpeed() - 200);
                    } else {
                        Robot.shooter.setSpeed(Robot.shooter.getSpeed() + 200);
                    }
                }
            } else {
                if (Timer.getFPGATimestamp() - this.m_timestamp > 7.5) {
                    this.m_finished = true;
                }
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        Robot.shooter.disable();
        Robot.transportation.setFeederMotorState(Transportation.TransportMotorState.OFF);
        Robot.transportation.setConveyorMotorState(Transportation.TransportMotorState.OFF);
    }
    
    @Override
    public boolean isFinished() {
        return m_finished;
    }
}
