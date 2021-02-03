package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.lib.Deadzone;
import frc.lib.swerve.SwerveModule;
import frc.lib.swerve.SwerveSettings;
import frc.robot.Constants;

public class SwerveDrive extends SubsystemBase {
	private AHRS m_gyro;

	private SwerveModule m_left = new SwerveModule(
        new SwerveSettings()
            .setDriveMotorID(Constants.Swerve.kLeftDriveID)
            .setRotationMotorID(Constants.Swerve.kLeftRotateID)
            .setRotationOffset(0)
            .setInverted(false)
            .setCommonName("left_")
    );
    private SwerveModule m_right = new SwerveModule(
        new SwerveSettings()
            .setDriveMotorID(Constants.Swerve.kRightDriveID)
            .setRotationMotorID(Constants.Swerve.kRightRotateID)
            .setRotationOffset(0)
            .setInverted(true)
            .setCommonName("fr_")

    );

	private SwerveDriveKinematics m_kinematics;

	private SwerveDriveOdometry m_odometry;

    public SwerveDrive(AHRS gyro) {
        this.m_gyro = gyro;

		this.m_kinematics = new SwerveDriveKinematics(
			new Translation2d(-Constants.Swerve.kModuleDistanceFromCenter, 0),
			new Translation2d(Constants.Swerve.kModuleDistanceFromCenter, 0)
		);

		this.m_odometry = new SwerveDriveOdometry(
			this.m_kinematics,
			Rotation2d.fromDegrees(-this.m_gyro.getAngle()),
			new Pose2d(0, 0, new Rotation2d())
		);
    }

/**
     * Drive the swerve motors like a differential drive using curvature. (Used for autonomous purposes)
     * @param speed Drive Speed
     * @param rotation Turning speed
     */
    public void curvatureDrive(double speed, double rotation) {
        // Prep speed
        speed = MathUtil.clamp(Deadzone.apply(speed, 0.05), -1.0, 1.0);

        // Prep rotation
        rotation = MathUtil.clamp(Deadzone.apply(rotation, 0.05), -1.0, 1.0);

        // Ang power
        double angularPower = Math.abs(speed) * rotation;

        // Left & right
        double leftMotorOutput = speed + angularPower;
        double rightMotorOutput = speed - angularPower;

        // Verify we don't overpower
        double maxMagnitude = Math.max(Math.abs(leftMotorOutput), Math.abs(rightMotorOutput));
        if (maxMagnitude > 1.0) {
            leftMotorOutput /= maxMagnitude;
            rightMotorOutput /= maxMagnitude;
        }

        // Set motor output
        this.m_left.set(0, leftMotorOutput);
        this.m_right.set(0, leftMotorOutput);
    }

	public void swerve(double forward, double strafe, double rotate, boolean isFieldOriented) {
		// TODO: Transition from 4 module to 2 module
		double gyroAngle = this.m_gyro.getAngle();

		double sin = Math.sin(Math.toRadians(gyroAngle));
		double cos = Math.cos(Math.toRadians(gyroAngle));

		if(isFieldOriented) {
			double T = (forward * cos) + (strafe * sin);
			strafe = (-forward * sin) + (strafe * cos);
			forward = T;
		}

		double J = Constants.Swerve.kWheelBase / Constants.Swerve.kTurnRadius;
		double K = Constants.Swerve.kTrackWidth / Constants.Swerve.kTurnRadius;

		double A = strafe - (rotate * J);
		double B = strafe + (rotate * J);
		double C = forward - (rotate * K);
		double D = forward + (rotate * K);

		double frSpeed = Math.hypot(B, C);
        double flSpeed = Math.hypot(B, D);
		double brSpeed = Math.hypot(A, C);
		double blSpeed = Math.hypot(A, D);

		double frAngle = Math.atan2(B, C) * 180 / Math.PI;
		double flAngle = Math.atan2(B, D) * 180 / Math.PI;
		double brAngle = Math.atan2(A, C) * 180 / Math.PI;
		double blAngle = Math.atan2(A, D) * 180 / Math.PI;

		double max = Math.max(Math.max(flSpeed, frSpeed), Math.max(blSpeed, brSpeed));

		if(max > 1) {
            flSpeed /= max;
            frSpeed /= max;
            blSpeed /= max;
            brSpeed /= max;
        }

		// m_fl.set(flAngle, flSpeed);
		// m_fr.set(frAngle, frSpeed);
		// m_bl.set(blAngle, blSpeed);
		// m_br.set(brAngle, brSpeed);
	}

	public Pose2d getPosition() {
		return this.m_odometry.getPoseMeters();
	}

	@Override
	public void periodic() {
		this.m_odometry.update(
			Rotation2d.fromDegrees(-this.m_gyro.getAngle()),
			this.m_left.getState(),
			this.m_right.getState()
		);
	}
}
