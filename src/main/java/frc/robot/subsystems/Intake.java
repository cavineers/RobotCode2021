package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 * Intake subsystem.
 */
public class Intake extends SubsystemBase {
    /**
     * Intake subsystem motor state.
     */
    public enum IntakeMotorState {
        ON,
        OFF,
        REVERSED
    }

    // Talon SRX Motor
    private WPI_TalonSRX m_intakeMotor = new WPI_TalonSRX(Constants.Intake.kIntakeID);

    // Current intake mode
    private IntakeMotorState m_currentMode = IntakeMotorState.OFF;

    /**
     * Intake constructor.
     */
    public Intake() {
        this.setMotorState(IntakeMotorState.OFF);

        Robot.logger.addInfo("Intake", "Created Intake subsystem");
    }

    /**
     * Set the desired intake state.

     * @param state wanted intake state
     */
    public void setMotorState(IntakeMotorState state) {
        // set the current state
        this.m_currentMode = state;
        
        // Log
        Robot.logger.addInfo("Intake", "Motor State Changed To " + state);

        // set motor state
        switch (state) {
            case ON:
                // On
                this.m_intakeMotor.set(ControlMode.PercentOutput, Constants.Intake.kInSpeed);
                break;
            case OFF:
                // Off
                this.m_intakeMotor.set(ControlMode.PercentOutput, 0);
                break;
            case REVERSED:
                // Reversed
                this.m_intakeMotor.set(ControlMode.PercentOutput, Constants.Intake.kOutSpeed);
                break;
            default:
                this.setMotorState(IntakeMotorState.OFF);
        }
    }

    /**
     * Get the current intake state.

     * @return intake state
     */
    public IntakeMotorState getMotorState() {
        // return the current motor state
        return this.m_currentMode;
    }
}
