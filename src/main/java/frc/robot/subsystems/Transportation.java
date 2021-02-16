package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class Transportation extends SubsystemBase {

    // Transport motor state
    public enum TransportMotorState {
        ON,
        OFF,
        REVERSED
    }

    // Talon SRX Motors
    private TalonSRX m_conveyerMotor = new TalonSRX(Constants.Transportation.kConveyerID);
    private TalonSRX m_feederMotor = new TalonSRX(Constants.Transportation.kFeederID);

    // Current transportation mode
    private TransportMotorState m_currentMode = TransportMotorState.OFF;

    public Transportation() {
        this.setMotorState(TransportMotorState.OFF);

        Robot.logger.addInfo("Transportation", "Created Transportation subsystem");
    }

     /**
     * set the desired transport state
     * @param state wanted transport state
     */
    public void setMotorState(TransportMotorState state) {
        // set the current state
        this.m_currentMode = state;
        
        // Log
        Robot.logger.addInfo("Transportation", "Motor State Changed To " + state);

        // set motor state
        switch (state) {
            case ON:
                // On
                this.m_conveyerMotor.set(ControlMode.PercentOutput, Constants.Transportation.kInSpeedConveyer);
                this.m_feederMotor.set(ControlMode.PercentOutput, Constants.Transportation.kInSpeedFeeder);
                break;
            case OFF:
                // Off
                this.m_conveyerMotor.set(ControlMode.PercentOutput, 0);
                this.m_feederMotor.set(ControlMode.PercentOutput, 0);
                break;
            case REVERSED:
                // Reversed
                this.m_conveyerMotor.set(ControlMode.PercentOutput, Constants.Transportation.kOutSpeedConveyer);
                this.m_feederMotor.set(ControlMode.PercentOutput, Constants.Transportation.kOutSpeedFeeder);
                break;
        }
    }

    /**
     * get the current transport state
     * @return transport state
     */
    public TransportMotorState getMotorState() {
        // return the current motor state
        return this.m_currentMode;
    }
}
