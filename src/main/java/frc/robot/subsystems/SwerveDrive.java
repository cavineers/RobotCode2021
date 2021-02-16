package frc.robot.subsystems;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.lib.Deadzone;
import frc.lib.swerve.SwerveModule;
import frc.lib.swerve.SwerveSettings;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class SwerveDrive extends SubsystemBase {
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
            .setCommonName("right_")

    );

	private SwerveDriveKinematics m_kinematics;

	private SwerveDriveOdometry m_odometry;

	private Field2d m_field = new Field2d();

	private double m_simulationAngle = 0.0;

    public SwerveDrive() {
		this.m_kinematics = new SwerveDriveKinematics(
			new Translation2d(-Constants.Swerve.kTrackWidth, 0),
			new Translation2d(Constants.Swerve.kTrackWidth, 0)
		);

		this.m_odometry = new SwerveDriveOdometry(
			this.m_kinematics,
			this.getAngle(),
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
		if(isFieldOriented) {
			double gyroAngle = RobotContainer.getInstance().gyro.getAngle();

			double sin = Math.sin(Math.toRadians(gyroAngle));
			double cos = Math.cos(Math.toRadians(gyroAngle));

			double T = (forward * cos) + (strafe * sin);
			strafe = (-forward * sin) + (strafe * cos);
			forward = T;
		}

		this.m_simulationAngle += rotate*2.0;

		double A = forward-rotate;
		double B = forward+rotate;

		double rSpeed = Math.sqrt(Math.pow(strafe, 2)+Math.pow(A, 2));
		double lSpeed = Math.sqrt(Math.pow(strafe, 2)+Math.pow(B, 2));

		double max = Math.max(rSpeed, lSpeed);

		if (max > 1.0) {
			rSpeed /= max;
			lSpeed /= max;
		}

		double rAngle = Math.atan2(strafe, A) * 180 / Math.PI;
		double lAngle = Math.atan2(strafe, B) * 180 / Math.PI;

		this.m_right.set(rAngle, rSpeed);
		this.m_left.set(lAngle, lSpeed);
	}

	public Pose2d getPosition() {
		return this.m_odometry.getPoseMeters();
	}

	public Rotation2d getAngle() {
		if (Robot.isReal()) {
			return Rotation2d.fromDegrees(-RobotContainer.getInstance().gyro.getAngle());
		} else {
			return Rotation2d.fromDegrees(-this.m_simulationAngle);
		}
	}

	@Override
	public void periodic() {
		this.m_odometry.update(
			this.getAngle(),
			this.m_left.getState(),
			this.m_right.getState()
		);

		this.m_field.setRobotPose(this.getPosition());

		SmartDashboard.putData(this.m_field);
	}
}
