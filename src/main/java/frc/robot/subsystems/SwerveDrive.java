package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Transform2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.lib.Deadzone;
import frc.lib.PathUtil;
import frc.lib.autonomous.Path;
import frc.lib.swerve.SwerveModule;
import frc.lib.swerve.SwerveSettings;
import frc.robot.Constants;
import frc.robot.Robot;

public class SwerveDrive extends SubsystemBase {
	private SwerveModule m_left = new SwerveModule(
        new SwerveSettings()
            .setDriveMotorID(Constants.Swerve.kLeftDriveID)
            .setRotationMotorID(Constants.Swerve.kLeftRotateID)
			.setRotationEncoderID(Constants.Swerve.kLeftEncoderID)
            .setRotationOffset(Rotation2d.fromDegrees(Constants.Swerve.kLeftOffset))
            .setInverted(false)
            .setCommonName("left_")
    );
    private SwerveModule m_right = new SwerveModule(
        new SwerveSettings()
            .setDriveMotorID(Constants.Swerve.kRightDriveID)
            .setRotationMotorID(Constants.Swerve.kRightRotateID)
			.setRotationEncoderID(Constants.Swerve.kRightEncoderID)
            .setRotationOffset(Rotation2d.fromDegrees(Constants.Swerve.kRightOffset))
            .setInverted(false)
            .setCommonName("right_")

    );

	public enum SwerveDriveState {
		SWERVE,
		CURVATURE,
		PATH
	}

	private SwerveDriveState m_state = SwerveDriveState.SWERVE;

	private SwerveDriveKinematics m_kinematics;

	private SwerveDriveOdometry m_odometry;

	private Field2d m_field = new Field2d();

	private double m_simulationAngle = 0.0;

    private Path m_path; 

	private TrapezoidProfile.Constraints m_translationalConstraints = new TrapezoidProfile.Constraints(Constants.Swerve.kMaxVelocity, Constants.Swerve.kMaxAcceleration);
    private TrapezoidProfile.Constraints m_rotationalConstraints = new TrapezoidProfile.Constraints(Constants.Swerve.kMaxRotateSpeed, Constants.Swerve.kMaxRotateAcceleration);
    private TrapezoidProfile.State m_xSetpoint = new TrapezoidProfile.State();
    private TrapezoidProfile.State m_ySetpoint = new TrapezoidProfile.State();
    private TrapezoidProfile.State m_rSetpoint = new TrapezoidProfile.State();
    private TrapezoidProfile.State m_xGoal;
    private TrapezoidProfile.State m_yGoal;
    private TrapezoidProfile.State m_rGoal;

    private double m_time;

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

		Robot.logger.addInfo("SwerveDrive", "Created SwerveDrive subsystem");
    }

/**
     * Drive the swerve motors like a differential drive using curvature. (Used for autonomous purposes)
     * @param speed Drive Speed
     * @param rotation Turning speed
     */
    public void curvatureDrive(double speed, double rotation) {
		if (this.m_state == SwerveDriveState.CURVATURE) {
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
    }

	public void swerve(double forward, double strafe, double rotate, boolean isFieldOriented) {
		if (this.m_state == SwerveDriveState.SWERVE) {
			this.localSwerve(forward, strafe, rotate, isFieldOriented);
		}
	}

	private void localSwerve(double forward, double strafe, double rotate, boolean isFieldOriented) {
		if (isFieldOriented) {
			double gyroAngle = Robot.gyro.getAngle();

			double sin = Math.sin(Math.toRadians(gyroAngle));
			double cos = Math.cos(Math.toRadians(gyroAngle));

			double T = (forward * cos) + (strafe * sin);
			strafe = (-forward * sin) + (strafe * cos);
			forward = T;
		}

		this.m_simulationAngle += rotate*3.0;

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

	public void followPath(Path path) {
		this.m_state = SwerveDriveState.PATH;

		this.m_path = path;
	}

	public Pose2d getPosition() {
		return this.m_odometry.getPoseMeters();
	}

	public Rotation2d getAngle() {
		if (Robot.isReal()) {
			return Rotation2d.fromDegrees(-Robot.gyro.getAngle());
		} else {
			return Rotation2d.fromDegrees(-this.m_simulationAngle);
		}
	}

	public void setState(SwerveDriveState state) {
		this.m_state = state;
	}

	public SwerveDriveState getState() {
		return this.m_state;
	}

	@Override
	public void periodic() {
		if (this.m_state == SwerveDriveState.PATH) {
			if (PathUtil.withinTolerance(Robot.swerveDrive.getPosition(), this.m_path.getCurrent(), Constants.AutoPath.kTranslationTolerance, Constants.AutoPath.kRotationalTolerance)) {
				if (this.m_path.next()) {
					this.m_path.up();
					Transform2d transform = Robot.swerveDrive.getPosition().minus(this.m_path.getCurrent());
					// double distance = Math.sqrt(Math.pow(transform.getX(), 2)+Math.pow(transform.getY(), 2));
					this.m_xGoal = new TrapezoidProfile.State(transform.getX(), this.m_path.next() ? 0.25 : 0.0);
					this.m_yGoal = new TrapezoidProfile.State(transform.getY(), this.m_path.next() ? 0.25 : 0.0);
					this.m_rGoal = new TrapezoidProfile.State(transform.getRotation().getDegrees(), 0.0);
					this.m_time = Timer.getFPGATimestamp();
				} else {
					this.localSwerve(0, 0, 0, false);
					this.m_state = SwerveDriveState.SWERVE;
					return;
				}
			}
			TrapezoidProfile xProfile = new TrapezoidProfile(this.m_translationalConstraints, this.m_xGoal, this.m_xSetpoint);
			TrapezoidProfile yProfile = new TrapezoidProfile(this.m_translationalConstraints, this.m_yGoal, this.m_ySetpoint);
			TrapezoidProfile rProfile = new TrapezoidProfile(this.m_rotationalConstraints, this.m_rGoal, this.m_rSetpoint);
			this.m_xSetpoint = xProfile.calculate(this.m_time);
			this.m_ySetpoint = yProfile.calculate(this.m_time);
			this.m_rSetpoint = rProfile.calculate(this.m_time);
			this.localSwerve(this.m_ySetpoint.velocity/Constants.Swerve.kMaxVelocity, this.m_xSetpoint.velocity/Constants.Swerve.kMaxVelocity, this.m_rSetpoint.velocity/Constants.Swerve.kMaxRotateSpeed, false);
		}

		this.m_odometry.update(
			this.getAngle(),
			this.m_left.getState(),
			this.m_right.getState()
		);

		this.m_field.setRobotPose(this.getPosition());

		SmartDashboard.putData(this.m_field);
	}
}
