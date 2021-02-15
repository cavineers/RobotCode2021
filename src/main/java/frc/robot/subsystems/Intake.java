package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode; 
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Intake extends SubsystemBase {

    // Intake motor state
    public enum IntakeMotorState {
        ON,
        OFF,
        REVERSED
    }

    // Talon SRX Motor
    private TalonSRX m_intakeMotor = new TalonSRX(Constants.Intake.IntakeID);

    // Current intake mode
    private IntakeMotorState m_currentMode = IntakeMotorState.OFF;
    
    private double lastTime = 0.0;

    private double time;

    public Intake(RobotContainer rc) {
        this.setMotorState(IntakeMotorState.OFF);
    }

     /**
     * set the desired intake state
     * @param state wanted intake state
     */
    public void setMotorState(IntakeMotorState state) {
        // set the current state
        this.m_currentMode = state;

        // set motor state
        switch (state) {
            case ON:
                // On
                this.m_intakeMotor.set(ControlMode.PercentOutput, Constants.Intake.InSpeed);
                break;
            case OFF:
                // Off
                this.m_intakeMotor.set(ControlMode.PercentOutput, 0);
                break;
            case REVERSED:
                // Reversed
                this.m_intakeMotor.set(ControlMode.PercentOutput, Constants.Intake.OutSpeed);
                break;
        }
    }

    /**
     * get the current intake state
     * @return intake state
     */
    public IntakeMotorState getMotorState() {
        // return the current motor state
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

        // Turn off after reverse
        if (this.time != 0 && Timer.getFPGATimestamp()-this.time > 1) {
            this.setMotorState(IntakeMotorState.OFF);
            this.time = 0;
        }
    }
}
