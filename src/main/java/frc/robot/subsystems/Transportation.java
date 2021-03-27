package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Intake.IntakeMotorState;

/**
 * Transport subsystem.
 */
public class Transportation extends SubsystemBase {

    private int m_numPowerCells = 0;

    /**
     * Motor state of the transport subsystem.
     */
    public enum TransportMotorState {
        ON,
        OFF,
        REVERSED,
        SLOW
    }

    // Motor Imports
    private WPI_TalonSRX m_conveyorMotor = new WPI_TalonSRX(Constants.Transportation.kConveyorID);
    private WPI_TalonSRX m_feederMotor = new WPI_TalonSRX(Constants.Transportation.kFeederID);

    // Current transportation mode
    private TransportMotorState m_currentFeederMode = TransportMotorState.OFF;
    private TransportMotorState m_currentConveyorMode = TransportMotorState.OFF;

    // Three Sensors for the conveyor / feeder belt
    private DigitalInput m_sensorOne = new DigitalInput(Constants.Dio.kConveyorSensor1);
    private DigitalInput m_sensorTwo = new DigitalInput(Constants.Dio.kConveyorSensor2);
    private DigitalInput m_sensorThree = new DigitalInput(Constants.Dio.kFeederSensor);

    private double m_offsetStart = 0.0;
    private double m_feederOffsetStart = 0.0;

    private boolean m_enabled = true;

    private boolean m_sensorTwoTripped = false;
    private boolean m_sensorThreeTripped = false;

    /**
     * Transportation constructor.
     */
    public Transportation() {
        this.setConveyorMotorState(TransportMotorState.OFF);
        this.setFeederMotorState(TransportMotorState.OFF);

        Robot.logger.addInfo("Transportation", "Created Transportation subsystem");
    }

    /**
     * Set the desired transport state.

     * @param state wanted transport state
     */
    public void setConveyorMotorState(TransportMotorState state) {
        // Don't set the same state repeatedly (also prevents spamming logs)
        if (this.m_currentConveyorMode == state) {
            return;
        }

        // set the current state
        this.m_currentConveyorMode = state;

        // Log
        Robot.logger.addInfo("Transportation", "Conveyor Motor State Changed To " + state);

        // set motor state
        switch (state) {
            case ON:
            case SLOW:
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
            default:
                this.setConveyorMotorState(TransportMotorState.OFF);
                break;
        }
    }

    public void enable() {
        this.m_enabled = true;
    }

    public void disable() {
        this.m_enabled = false;
    }

    /**
     * Set the desired transport state.

     * @param state wanted transport state
     */
    public void setFeederMotorState(TransportMotorState state) {
        // Don't set the same state repeatedly (also prevents spamming logs)
        if (this.m_currentFeederMode == state) {
            return;
        }

        // set the current state
        this.m_currentFeederMode = state;
        
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
            case SLOW:
                // Slow
                this.m_feederMotor.set(ControlMode.PercentOutput, Constants.Transportation.kSlowFeeder);
                break;
            default:
                this.setFeederMotorState(TransportMotorState.OFF);
                break;
        }
    }

    /**
     * Toggle the conveyor belt.
     */
    public void toggleConveyor() {
        Robot.logger.addInfo("ToggleConveyor", "Conveyor Toggle");
        if (Robot.transportation.getConveyorMotorState() == TransportMotorState.OFF) {
            Robot.transportation.setConveyorMotorState(TransportMotorState.ON);
        } else {
            Robot.transportation.setConveyorMotorState(TransportMotorState.OFF);
        }
    }

    /**
     * Toggle the feeder wheels.
     */
    public void toggleFeeder() {
        Robot.logger.addInfo("ToggleFeeder", "Feeder Toggle");
        if (Robot.transportation.getFeederMotorState() == TransportMotorState.OFF) {
            Robot.transportation.setFeederMotorState(TransportMotorState.ON);
        } else {
            Robot.transportation.setFeederMotorState(TransportMotorState.OFF);
        }
    }

    public boolean getSensorOneState() {
        return !m_sensorOne.get();
    }

    public boolean getSensorTwoState() {
        return !m_sensorTwo.get();
    }

    public boolean getSensorThreeState() {
        return !m_sensorThree.get();
    }

    /**
     * Get the current transport state.

     * @return conveyor state
     */
    public TransportMotorState getConveyorMotorState() {
        return this.m_currentConveyorMode;
    }

    /**
     * Get the current transport state.

     * @return transport state
     */
    public TransportMotorState getFeederMotorState() {
        return this.m_currentFeederMode;
    }

    /**
     * Gets current PowerCell Count.

     * @return current count
     */
    public int getBallCount() {
        return this.m_numPowerCells;
    }

    public void setBallCount(int ballCount) {
        this.m_numPowerCells = ballCount;
    }

    /**
     * Transportation periodic.
     */
    @Override
    public void periodic() {
        // Set PowerCell count
        SmartDashboard.putNumber("Current PowerCell Count", this.getBallCount());

        SmartDashboard.putBoolean("sensorOneState", this.getSensorOneState());
        SmartDashboard.putBoolean("sensorTwoState", this.getSensorTwoState());
        SmartDashboard.putBoolean("sensorThreeState", this.getSensorThreeState());

        // Move PowerCell positions autonomously via sensor inputs
        if (this.m_enabled) {
            if (this.m_sensorTwoTripped != true) {
                this.m_sensorTwoTripped = this.getSensorTwoState();
            }
            if (this.m_sensorThreeTripped != true) {
                this.m_sensorThreeTripped = this.getSensorThreeState();
            }
            if (this.m_feederOffsetStart != 0.0) {
                if (Timer.getFPGATimestamp() - this.m_feederOffsetStart > 0.2) {
                    this.setFeederMotorState(TransportMotorState.OFF);
                    this.m_feederOffsetStart = 0.0;
                }
            } else if (this.m_offsetStart != 0.0) {
                if (Timer.getFPGATimestamp() - this.m_offsetStart > 0.35) {
                    this.setConveyorMotorState(TransportMotorState.OFF);
                    this.m_offsetStart = 0.0;
                }
            } else if (this.getBallCount() == 0) {
                // Check sensor one input.
                if (this.getSensorOneState()) {
                    // Turn on conveyor systems.
                    this.setConveyorMotorState(TransportMotorState.ON);
                } else if (this.getConveyorMotorState() == TransportMotorState.ON) {
                    // Turn off conveyor systems.
                    this.m_offsetStart = Timer.getFPGATimestamp();
                    this.setBallCount(1);
                }
            } else if (this.getBallCount() == 1) {
                // Check sensor one input.
                if (this.getSensorOneState()) {
                    // Turn on conveyor systems.
                    this.setConveyorMotorState(TransportMotorState.ON);
                } else if (!this.getSensorTwoState() && this.getConveyorMotorState() == TransportMotorState.ON) {
                    // Turn off conveyor systems.
                    this.m_offsetStart = Timer.getFPGATimestamp();
                    this.setBallCount(2);
                }
            } else if (this.getBallCount() == 2) {
                // Check sensor one input.
                if (this.getSensorOneState()) {
                    // Turn on conveyor / feeder systems.
                    this.setConveyorMotorState(TransportMotorState.ON);
                    this.setFeederMotorState(TransportMotorState.SLOW);
                }
                if (this.getSensorThreeState()) {
                    // Turn off conveyor / feeder systems.
                    // this.setFeederMotorState(TransportMotorState.OFF);
                    this.m_feederOffsetStart = Timer.getFPGATimestamp();
                    this.setConveyorMotorState(TransportMotorState.OFF);
                    Robot.intake.setMotorState(IntakeMotorState.OFF);
                    this.setBallCount(3);
                }
            }
        }
    }
}
