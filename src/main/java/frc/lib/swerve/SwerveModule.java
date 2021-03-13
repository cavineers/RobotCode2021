package frc.lib.swerve;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 * Swerve Module.
 */
public class SwerveModule {
    // Drive Motor
    private CANSparkMax m_driveMotor;

    // Rotation Motor
    private CANSparkMax m_rotationMotor;

    // CANCoder
    private CANCoder m_encoder;

    // PID
    private PIDController m_pidController; 

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
        this.m_driveMotor = new CANSparkMax(this.m_settings.getDriveMotorId(), MotorType.kBrushless);
        this.m_rotationMotor = new CANSparkMax(this.m_settings.getRotationMotorId(), MotorType.kBrushless);

        // Init encoder
        this.m_encoder = new CANCoder(this.m_settings.getEncoderId());

        // Restore factory defaults
        this.m_rotationMotor.restoreFactoryDefaults();

        // Set to coast mode
        this.m_rotationMotor.setIdleMode(IdleMode.kCoast);

        this.m_pidController = new PIDController(Constants.Swerve.kRotationPID_P, Constants.Swerve.kRotationPID_I, Constants.Swerve.kRotationPID_D);

        // Send default angle to smart dashboard
        SmartDashboard.putNumber(this.m_settings.commonName() + "_Angle", 0.0);

        // Send default PID values to SmartDashboard for testing
        // TODO: Comment this when done tuning
        SmartDashboard.putNumber("rot_kP", Constants.Swerve.kRotationPID_P);
        SmartDashboard.putNumber("rot_kI", Constants.Swerve.kRotationPID_I);
        SmartDashboard.putNumber("rot_kD", Constants.Swerve.kRotationPID_D);

        // Set encoder absolute position when started
        SmartDashboard.putNumber(this.m_settings.commonName() + "_absoluteValue", this.getRotation().getDegrees());
    }

    /**
     * Get the current rotational offset.

     * @return current offset
     */
    public Rotation2d getRotation() {
        if (Robot.isReal()) {
            return Rotation2d.fromDegrees(this.m_encoder.getAbsolutePosition() - this.m_settings.getRotationOffset().getDegrees());
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
        // Read angle for tuning
        // TODO: Comment this when finished tuning
        // angle = SmartDashboard.getNumber(this.m_settings.commonName() + "_Angle", 0.0);
        // speed = 0.5;

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

        if (Math.abs(difference) > 178.0 && Math.abs(difference) < 182.0) {
            difference -= (360.0 * Math.signum(difference));
        }

        if (Math.abs(difference) > 90.0) {
            difference -= (180.0 * Math.signum(difference));
            speed = -speed;
        }
        
        // Set PID Values
        // TODO: Comment this when done tuning
        this.m_pidController.setP(SmartDashboard.getNumber("rot_kP", 0.0));
        this.m_pidController.setI(SmartDashboard.getNumber("rot_kI", 0.0));
        this.m_pidController.setD(SmartDashboard.getNumber("rot_kD", 0.0));

        // Current Setpoint
        this.m_rotationSetpoint = this.getRotation().plus(Rotation2d.fromDegrees(difference));

        // Output rotation motor
        this.m_rotationMotor.set(this.m_pidController.calculate(this.getRotation().getDegrees(), this.m_rotationSetpoint.getDegrees()));

        // Test
        this.m_currentSpeed = this.m_settings.isInverted() ? -speed : speed;

        // Output drive motor
        this.m_driveMotor.set(this.m_currentSpeed);

        // System.out.println(this.m_currentSpeed);

        SmartDashboard.putNumber(this.m_settings.commonName() + "_percentOut", this.m_currentSpeed);
        SmartDashboard.putNumber(this.m_settings.commonName() + "_rotationSetpoint", this.m_rotationSetpoint.getDegrees());
        SmartDashboard.putNumber(this.m_settings.commonName() + "_rotationPosition", currentOffset);
        SmartDashboard.putNumber(this.m_settings.commonName() + "_encoderVal", this.getRotation().getDegrees());
    }

    /**
     * Get the current state of the module.

     * @return Swerve module state
     */
    public SwerveModuleState getState() {
        SmartDashboard.putNumber(this.m_settings.commonName() + "_encoderVal", this.getRotation().getDegrees());

        return new SwerveModuleState(
            this.m_currentSpeed * Constants.Swerve.kMaxVelocity,
            this.getRotation()
        );
    }
}