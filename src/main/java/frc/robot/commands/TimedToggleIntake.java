// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Intake.IntakeMotorState;

/**
 * Turn on intake for desired time.
 */
public class TimedToggleIntake extends CommandBase {
    private double m_start;
    private double m_time;

    /**
     * Turn on intake for desired time.

     * @param time Time in seconds
     */
    public TimedToggleIntake(double time) {
        this.addRequirements(Robot.intake);
        this.m_time = time;
    }

    @Override
    public void initialize() {
        this.m_start = Timer.getFPGATimestamp();
        Robot.intake.setMotorState(IntakeMotorState.ON);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {
        Robot.intake.setMotorState(IntakeMotorState.OFF);
    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() - this.m_start >= this.m_time;
    }
}
