package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
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

    // Motor Imports
    private TalonSRX m_conveyorMotor = new TalonSRX(Constants.Transportation.kConveyorID);
    private TalonSRX m_feederMotor = new TalonSRX(Constants.Transportation.kFeederID);

    // Current transportation mode
    private TransportMotorState m_currentModeFeeder = TransportMotorState.OFF;
    private TransportMotorState m_currentModeConveyor = TransportMotorState.OFF;

    private DigitalInput m_sensorOne = new DigitalInput(Constants.DIO.kConveyorSensor1);
    private DigitalInput m_sensorTwo = new DigitalInput(Constants.DIO.kConveyorSensor2);
    private DigitalInput m_sensorThree = new DigitalInput(Constants.DIO.kFeederSensor);

    public Transportation() {
        this.setMotorStateConveyor(TransportMotorState.OFF);
        this.setMotorStateFeeder(TransportMotorState.OFF);

        Robot.logger.addInfo("Transportation", "Created Transportation subsystem");
    }

    /**
     * set the desired transport state
     * @param state wanted transport state
     */
    public void setMotorStateConveyor(TransportMotorState state) {
        // set the current state
        this.m_currentModeConveyor = state;
        
        // Log
        Robot.logger.addInfo("Transportation", "Conveyor Motor State Changed To " + state);

        // set motor state
        switch (state) {
            case ON:
                // On
                this.m_conveyorMotor.set(ControlMode.PercentOutput, Constants.Transportation.kInSpeedConveyor);
                break;
            case OFF:
                // Off
                this.m_conveyorMotor.set(ControlMode.PercentOutput, 0);
                break;
            case REVERSED:
                // Reversed
                this.m_conveyorMotor.set(ControlMode.PercentOutput, Constants.Transportation.kOutSpeedConveyor);
                break;
        }
    }

    /**
     * set the desired transport state
     * @param state wanted transport state
     */
    public void setMotorStateFeeder(TransportMotorState state) {
        // set the current state
        this.m_currentModeFeeder = state;
        
        // Log
        Robot.logger.addInfo("Transportation", "Feeder Motor State Changed To " + state);

        // set motor state
        switch (state) {
            case ON:
                // On
                this.m_feederMotor.set(ControlMode.PercentOutput, Constants.Transportation.kInSpeedFeeder);
                break;
            case OFF:
                // Off
                this.m_feederMotor.set(ControlMode.PercentOutput, 0);
                break;
            case REVERSED:
                // Reversed
                this.m_feederMotor.set(ControlMode.PercentOutput, Constants.Transportation.kOutSpeedFeeder);
                break;
        }
    }

    /**
     * get the current transport state
     * @return conveyor state
     */
    public TransportMotorState getConveyorMotorState() {
        return this.m_currentModeConveyor;
    }

    /**
     * get the current transport state
     * @return transport state
     */
    public TransportMotorState getFeederMotorState() {
        return this.m_currentModeFeeder;
    }
}
