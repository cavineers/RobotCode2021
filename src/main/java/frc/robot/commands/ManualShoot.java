package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Transportation.TransportMotorState;

/**
 * Turns on shooting systems and conveyors.
 */
public class ManualShoot extends CommandBase {
    private double m_timestamp;

    public ManualShoot() {}

    @Override
    public void initialize() {
        Robot.shooter.enable();
        Robot.shooter.setSpeed(4500); // Change to update speed. (1000 - 5000 safe bounds)

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
        // Turns off after 10 seconds.
        return Timer.getFPGATimestamp() - this.m_timestamp >= 10.0;
    }
}
