package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Transportation.TransportMotorState;

public class LowGoalShoot extends CommandBase {
    private double m_timestamp;

    public LowGoalShoot() {}

    @Override
    public void initialize() {
        Robot.shooter.enable();
        Robot.shooter.setSpeed(2000);

        this.m_timestamp = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        if (Timer.getFPGATimestamp() - this.m_timestamp >= 2.0) {
            Robot.transportation.setConveyorMotorState(TransportMotorState.ON);
            Robot.transportation.setFeederMotorState(TransportMotorState.ON);
        }
    }

    @Override
    public void end(boolean interrupted) {
        Robot.shooter.disable();
        Robot.transportation.setConveyorMotorState(TransportMotorState.OFF);
        Robot.transportation.setFeederMotorState(TransportMotorState.OFF);
    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() - this.m_timestamp >= 15.0;
    }
}
