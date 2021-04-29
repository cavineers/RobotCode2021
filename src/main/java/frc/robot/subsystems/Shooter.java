package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 * Shooter subsystem.
 */
public class Shooter extends SubsystemBase {
    /**
     * Shooter mode enum.
     */
    public enum ShooterMode {
        ENABLED,
        DISABLED
    }

    // Fly wheel motor
    private CANSparkMax m_shootMotor = new CANSparkMax(Constants.Shooter.kShootID, MotorType.kBrushless);

    // Fly wheel PID
    private CANPIDController m_pidController = m_shootMotor.getPIDController();
    
    // Fly wheel encoder
    private CANEncoder m_encoder = m_shootMotor.getEncoder();

    // Current shooter mode
    private ShooterMode m_currentMode = ShooterMode.DISABLED;

    // Current speed
    private double m_speed = 0;

    /**
     * Shooter constructor.
     */
    public Shooter() {
        // Restore factory defaults
        this.m_shootMotor.restoreFactoryDefaults();

        // Set to coast mode
        this.m_shootMotor.setIdleMode(IdleMode.kCoast);

        // Current limit
        this.m_shootMotor.setSmartCurrentLimit(Constants.Shooter.kCurrentLimit);

        // Add PID values to controller
        this.m_pidController.setIZone(0.0);
        this.m_pidController.setOutputRange(-1.0, 1.0);

        // Log to smart dashboard for debugging
        SmartDashboard.putNumber("shooter_p", Constants.Shooter.kPIDp);
        SmartDashboard.putNumber("shooter_i", Constants.Shooter.kPIDi);
        SmartDashboard.putNumber("shooter_d", Constants.Shooter.kPIDd);
        SmartDashboard.putNumber("shooter_f", Math.abs(0.000182));

        // Log
        Robot.logger.addInfo("Shooter", "Created Shooter subsystem");
    }

    /**
     * Turn on the shooter.
     */
    public void enable() {
        this.m_currentMode = ShooterMode.ENABLED;
        Robot.logger.addInfo("Shooter", "Enabled shooter PID loop");
    }

    /**
     * Turn off the shooter.
     */
    public void disable() {
        this.m_currentMode = ShooterMode.DISABLED;
        Robot.logger.addInfo("Shooter", "Disabled shooter PID loop");
        this.setSpeed(0.0);
    }

    /**
     * Get the current shooting mode.

     * @return ShooterMode enum
     */
    public ShooterMode getCurrentMode() {
        return this.m_currentMode;
    }

    /**
     * Set the shooter speed.

     * @param speed wanted speed
     */
    public void setSpeed(double speed) {
        this.m_speed = speed;
    }

    /**
     * Get the desired speed of the shooter.

     * @return Get RPM.
     */
    public double getSpeed() {
        return this.m_speed;
    }

    /**
     * Is the speed at the setpoint.

     * @return at setpoint
     */
    public boolean atSetpoint() {
        return (Math.abs(this.m_speed - Math.abs(this.m_encoder.getVelocity())) < 120);
    }

    /**
     * Shooter periodic.
     */
    @Override
    public void periodic() {
        if (this.m_currentMode == ShooterMode.ENABLED) {
            this.m_pidController.setP(SmartDashboard.getNumber("shooter_p", 0));
            this.m_pidController.setI(SmartDashboard.getNumber("shooter_i", 0));
            this.m_pidController.setD(SmartDashboard.getNumber("shooter_d", 0));
            this.m_pidController.setFF(Math.abs(SmartDashboard.getNumber("shooter_f", 0)));

            this.m_pidController.setReference(-this.m_speed, ControlType.kVelocity);
        } else {
            this.m_pidController.setP(0.001);
            this.m_pidController.setI(0.0);
            this.m_pidController.setD(0.0001);
            this.m_pidController.setFF(Math.abs(SmartDashboard.getNumber("shooter_f", 0)));

            this.m_pidController.setReference(0.0, ControlType.kVelocity);
        }

        // Add the setpoint and actual to smart dashboard
        SmartDashboard.putNumber("SetPoint", this.m_speed);
        SmartDashboard.putNumber("Actual", this.m_encoder.getVelocity());
    }
}