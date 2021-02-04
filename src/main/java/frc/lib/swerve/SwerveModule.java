package frc.lib.swerve;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.Constants;

public class SwerveModule extends SubsystemBase {
    // Drive Motor
    private WPI_TalonSRX driveMotor;

    // Rotation Motor
    private WPI_TalonSRX rotationMotor;

    // Settings object
    public SwerveSettings settings;

    // Save these values for later
    private double m_currentSpeed = 0;

    /**
     * Constructor
     * @param settings Swerve settings object
     */
    public SwerveModule(SwerveSettings settings) {
        // Store settings
        this.settings = settings;
        
        // Init motors
        this.driveMotor = new WPI_TalonSRX(this.settings.getDriveMotorID());
        this.rotationMotor = new WPI_TalonSRX(this.settings.getRotationMotorID());

        // Neutral modes
        this.driveMotor.setNeutralMode(NeutralMode.Brake);
        this.rotationMotor.setNeutralMode(NeutralMode.Brake);

        // Configure PID on the rotation motor
        this.rotationMotor.configFactoryDefault();
        this.rotationMotor.config_kP(0, Constants.Swerve.kRotationPID_P);
        this.rotationMotor.config_kI(0, Constants.Swerve.kRotationPID_I);
        this.rotationMotor.config_kD(0, Constants.Swerve.kRotationPID_D);

        // Set rotation offset based on settings
        this.setOffset(this.settings.getRotationOffset());

        SmartDashboard.putNumber(this.settings.commonName()+"_Angle", 0.0);
    }

    /**
     * Set the rotational offset to the current position
     */
    public void resetOffset() {
        this.rotationMotor.setSelectedSensorPosition(0);
    }

    /**
     * Get the current rotational offset
     * @return current offset
     */
    public double getOffset() {
        return this.rotationMotor.getSelectedSensorPosition();
    }

    /**
     * Set the rotation offset
     * @param offset The desired offset (encoder offset: 4096 per 360deg)
     */
    public void setOffset(int offset) {
        this.rotationMotor.setSelectedSensorPosition(offset);
    }

    /**
     * Drive the swerve module using speed & rotational angle
     * @param angle rotational angle
     * @param speed wheel speed
     */
    public void set(double angle, double speed) {
        // angle = SmartDashboard.getNumber(this.settings.commonName()+"_Angle", 0.0);
        // speed = 0.0;

        // Current offset
        double currentOffset = getOffset()/(4096.0/360.0);
        
        // Normalize offset
        while (currentOffset > 180.0) currentOffset -= 360.0;
        while (currentOffset < -180.0) currentOffset += 360.0;

        // Normalize angle
        while (angle > 180.0) angle -= 360.0;
        while (angle < -180.0) angle += 360.0;

        // Setting and updating the difference between the offset and the angel
        double difference = angle-currentOffset;

        if (Math.abs(difference) > 180.0) {
            difference -= (360.0*Math.signum(difference));
        }

        if (Math.abs(difference) > 90.0) {
			difference -= (180.0*Math.signum(difference));
			speed = -speed;
        }
        
        // Current Setpoint
        double rSetpoint = getOffset()+(difference*(4096.0/360.0));

        // Output rotation motor
        this.rotationMotor.set(ControlMode.Position, rSetpoint);

        // Output drive motor
        this.driveMotor.set(ControlMode.PercentOutput, this.settings.isInverted() ? -speed : speed);

        SmartDashboard.putNumber(this.settings.commonName()+"_percentOut", this.settings.isInverted() ? -speed : speed);
        SmartDashboard.putNumber(this.settings.commonName()+"_rotationSetpoint", rSetpoint);
        SmartDashboard.putNumber(this.settings.commonName()+"_rotationPosition", currentOffset);
        SmartDashboard.putNumber(this.settings.commonName()+"_encoderVal", this.getOffset());
    }

    public SwerveModuleState getState() {
		return new SwerveModuleState(
            this.m_currentSpeed*Constants.Swerve.kMaxDriveSpeed,
            Rotation2d.fromDegrees(getOffset()/(4096.0/360.0))
        );
    }

    @Override
    public void periodic() {}
}