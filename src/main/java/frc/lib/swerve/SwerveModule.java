package frc.lib.swerve;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 * Swerve Module.
 */
public class SwerveModule extends SubsystemBase {
    // Drive Motor
    private WPI_TalonSRX m_driveMotor;

    // Rotation Motor
    private WPI_TalonSRX m_rotationMotor;

    // CANCoder
    private CANCoder m_encoder;

    // Settings object
    public SwerveSettings m_settings;

    // Save these values for later
    private double m_currentSpeed = 0.0;
    private Rotation2d m_rotationSetpoint = new Rotation2d();

    /**
     * Constructor.

     * @param settings Swerve settings object
     */
    public SwerveModule(SwerveSettings settings) {
        // Store settings
        this.m_settings = settings;
        
        // Init motors
        this.m_driveMotor = new WPI_TalonSRX(this.m_settings.getDriveMotorId());
        this.m_rotationMotor = new WPI_TalonSRX(this.m_settings.getRotationMotorId());

        // Init encoder
        this.m_encoder = new CANCoder(this.m_settings.getEncoderId());

        // Neutral modes
        this.m_driveMotor.setNeutralMode(NeutralMode.Brake);
        this.m_rotationMotor.setNeutralMode(NeutralMode.Brake);

        // Configure PID on the rotation motor
        this.m_rotationMotor.configFactoryDefault();
        this.m_rotationMotor.config_kP(0, Constants.Swerve.kRotationPID_P);
        this.m_rotationMotor.config_kI(0, Constants.Swerve.kRotationPID_I);
        this.m_rotationMotor.config_kD(0, Constants.Swerve.kRotationPID_D);

        // Reset to the absolute offset
        this.m_rotationMotor.set(ControlMode.Position, this.m_settings.getRotationOffset().getDegrees());

        SmartDashboard.putNumber(this.m_settings.commonName() + "_Angle", 0.0);
    }

    /**
     * Get the current rotational offset.

     * @return current offset
     */
    public Rotation2d getRotation() {
        if (Robot.isReal()) {
            return Rotation2d.fromDegrees(this.m_encoder.getAbsolutePosition());
        } else {
            return this.m_rotationSetpoint;
        }
    }

    /**
     * Drive the swerve module using speed & rotational angle.

     * @param angle rotational angle
     * @param speed wheel speed
     */
    public void set(double angle, double speed) {
        // angle = SmartDashboard.getNumber(this.settings.commonName()+"_Angle", 0.0);
        // speed = 0.0;

        // Current offset
        double currentOffset = this.getRotation().getDegrees();

        // Normalize offset
        while (currentOffset > 180.0) {
            currentOffset -= 360.0;
        }

        while (currentOffset < -180.0) {
            currentOffset += 360.0;
        }

        // Normalize angle
        while (angle > 180.0) {
            angle -= 360.0;
        }
        while (angle < -180.0) {
            angle += 360.0;
        }

        // Setting and updating the difference between the offset and the angel
        double difference = angle - currentOffset;

        if (Math.abs(difference) > 180.0) {
            difference -= (360.0 * Math.signum(difference));
        }

        if (Math.abs(difference) > 90.0) {
            difference -= (180.0 * Math.signum(difference));
            speed = -speed;
        }
        
        // Current Setpoint
        this.m_rotationSetpoint = this.getRotation().plus(Rotation2d.fromDegrees(difference));

        // Output rotation motor
        this.m_rotationMotor.set(ControlMode.Position, this.m_rotationSetpoint.getDegrees());

        // Output drive motor
        this.m_driveMotor.set(ControlMode.PercentOutput, this.m_settings.isInverted() ? -speed : speed);

        // Save current speed
        this.m_currentSpeed = this.m_settings.isInverted() ? -speed : speed;

        SmartDashboard.putNumber(this.m_settings.commonName() + "_percentOut", this.m_settings.isInverted() ? -speed : speed);
        SmartDashboard.putNumber(this.m_settings.commonName() + "_rotationSetpoint", this.m_rotationSetpoint.getDegrees());
        SmartDashboard.putNumber(this.m_settings.commonName() + "_rotationPosition", currentOffset);
        SmartDashboard.putNumber(this.m_settings.commonName() + "_encoderVal", this.getRotation().getDegrees());
    }

    /**
     * Get the current state of the module.

     * @return Swerve module state
     */
    public SwerveModuleState getState() {
        return new SwerveModuleState(
            this.m_currentSpeed * Constants.Swerve.kMaxVelocity,
            this.getRotation()
        );
    }

    @Override
    public void periodic() {}
}