package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode; 
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

    // Intake motor state
    public enum IntakeMotorState {
        ON,
        OFF
    }

    // Intake State
    public enum IntakeMode {
        ENABLED, DISABLED
    }

    // Talon SRX Motor
    private TalonSRX intakeMotor = new TalonSRX(Constants.Intake.IntakeID);
    private IntakeMotorState currentMotorState;

    // Current intake mode
    private IntakeMode m_currentMode = IntakeMode.DISABLED;

    public Intake() {
        this.setMotorState(IntakeMotorState.OFF);
    }

    // Robot
    private double lastTime = 0.0;

     /**
     * set the desired intake state
     * @param state wanted intake state
     */
    public void setMotorState(IntakeMotorState state) {
        // set the current state
        this.currentMotorState = state;

        // set motor state
        switch (state) {
            case ON:
                // On
                this.intakeMotor.set(ControlMode.PercentOutput, Constants.Intake.InSpeed);
                break;
            case OFF:
                // Off
                this.intakeMotor.set(ControlMode.PercentOutput, 0);
                break;
        }
    }

    /**
     * get the current intake state
     * @return intake state
     */
    public IntakeMotorState getMotorState() {
        // return the current motor state
        return this.currentMotorState;
    }

    /**
     * Turn on the intake
     */
    public void enable() {
        this.m_currentMode = IntakeMode.ENABLED;
    }

    /**
     * Turn off the intake
     */
    public void disable() {
        this.m_currentMode = IntakeMode.DISABLED;
    }

    /**
     * Get the current intake mode
     * @return IntakeMode enum
     */
    public IntakeMode getCurrentMode() {
        return this.m_currentMode;
    }

    /**
     * Intake periodic
     */
    @Override
    public void periodic() {
        if (Timer.getFPGATimestamp()-this.lastTime>0.75) {
            this.lastTime = Timer.getFPGATimestamp();
        }
    }
}
