package frc.lib.swerve;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
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

    // Settings object
    public SwerveSettings m_settings;

    // Rotation Controller
    private PIDController m_rotationController; 
    private CANCoder m_encoder;

    // Drive Controller
    private CANPIDController m_driveController;
    private CANEncoder m_driveEncoder;

    // Save these values for later
    private double m_currentSpeed = 0.0;
    private Rotation2d m_rotationSetpoint = new Rotation2d();

    private double m_angle = 0.0;
    private double m_speed = 0.0;

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

        this.m_driveEncoder = this.m_driveMotor.getEncoder();

        // Configure Motors
        this.m_driveMotor.setIdleMode(IdleMode.kBrake);
        this.m_rotationMotor.setIdleMode(IdleMode.kBrake);

        this.m_driveMotor.setSmartCurrentLimit(38);
        this.m_rotationMotor.setSmartCurrentLimit(38);

        this.m_driveController = this.m_driveMotor.getPIDController();
        this.m_rotationController = new PIDController(Constants.Swerve.kRotationPID_P, Constants.Swerve.kRotationPID_I, Constants.Swerve.kRotationPID_D);

        // Configure drive controller
        this.m_driveController.setIZone(0.0);
        this.m_driveController.setOutputRange(-1.0, 1.0);

        // Drive PIDs
        SmartDashboard.putNumber(this.m_settings.commonName() + "_drive_p", Constants.Swerve.kVelocityPIDp);
        SmartDashboard.putNumber(this.m_settings.commonName() + "_drive_i", Constants.Swerve.kVelocityPIDi);
        SmartDashboard.putNumber(this.m_settings.commonName() + "_drive_d", Constants.Swerve.kVelocityPIDd);
        SmartDashboard.putNumber(this.m_settings.commonName() + "_drive_f", Math.abs(0.000182));

        // Send default angle to smart dashboard
        SmartDashboard.putNumber(this.m_settings.commonName() + "_Angle", 0.0);

        // Set PID values
        this.m_rotationController.setP(Constants.Swerve.kRotationPID_P);
        this.m_rotationController.setI(Constants.Swerve.kRotationPID_I);
        this.m_rotationController.setD(Constants.Swerve.kRotationPID_D);
        this.m_rotationController.setTolerance(Constants.Swerve.kRotationPID_T);

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
        // angle = SmartDashboard.getNumber(this.m_settings.commonName() + "_Angle", 0.0);
        // speed = 0.5;

        this.m_angle = angle;
        this.m_speed = speed;

        this.periodic();
    }

    /**
     * Run module periodic.
     */
    public void periodic() {
        double angle = this.m_angle;
        double speed = this.m_speed;

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

        // Current Setpoint
        this.m_rotationSetpoint = this.getRotation().plus(Rotation2d.fromDegrees(difference));

        // Output rotation motor
        this.m_rotationMotor.set(this.m_rotationController.calculate(this.getRotation().getDegrees(), this.m_rotationSetpoint.getDegrees()));

        // Invert speed
        this.m_currentSpeed = this.m_settings.isInverted() ? -speed : speed;

        this.m_driveController.setP(SmartDashboard.getNumber(this.m_settings.commonName() + "_drive_p", 0));
        this.m_driveController.setI(SmartDashboard.getNumber(this.m_settings.commonName() + "_drive_i", 0));
        this.m_driveController.setD(SmartDashboard.getNumber(this.m_settings.commonName() + "_drive_d", 0));
        this.m_driveController.setFF(Math.abs(SmartDashboard.getNumber(this.m_settings.commonName() + "_drive_f", 0)));

        // Output drive motor
        this.m_driveController.setReference(this.m_currentSpeed * 5500, ControlType.kVelocity);

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
            -((this.m_driveEncoder.getVelocity() / 5500) * Constants.Swerve.kMaxVelocity),
            this.getRotation()
        );
    }
}