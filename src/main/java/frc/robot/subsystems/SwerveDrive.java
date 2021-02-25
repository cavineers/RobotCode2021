package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
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

    private TrapezoidProfile.State m_xSetpoint;
    private TrapezoidProfile.State m_ySetpoint;
    private TrapezoidProfile.State m_rSetpoint;

	private TrapezoidProfile m_xProfile;
	private TrapezoidProfile m_yProfile;
	private TrapezoidProfile m_rProfile;

    private TrapezoidProfile.State m_xGoal;
    private TrapezoidProfile.State m_yGoal;
    private TrapezoidProfile.State m_rGoal;

	private TrapezoidProfile.State m_xInitial;
    private TrapezoidProfile.State m_yInitial;
    private TrapezoidProfile.State m_rInitial;

	private boolean m_xFinish = false;
	private boolean m_yFinish = false;
	private boolean m_rFinish = false;

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
		// If the robot is field oriented
		if (isFieldOriented) {
			// Find conversions based on gyro angles
			double sin = Math.sin(this.getAngle().getRadians());
			double cos = Math.cos(this.getAngle().getRadians());

			// Translate forward/strafe based on conversions
			double T = (forward * cos) + (strafe * sin);
			strafe = (-forward * sin) + (strafe * cos);
			forward = T;
		}

		// Update simulation angle
		this.m_simulationAngle += rotate*3.0;

		// Get A/B
		double A = forward-rotate;
		double B = forward+rotate;

		// Get motor speeds
		double rSpeed = Math.sqrt(Math.pow(strafe, 2)+Math.pow(A, 2));
		double lSpeed = Math.sqrt(Math.pow(strafe, 2)+Math.pow(B, 2));

		// Get max of the two
		double max = Math.max(rSpeed, lSpeed);

		// Normalize speeds
		if (max > 1.0) {
			rSpeed /= max;
			lSpeed /= max;
		}

		// Get the rotation angles
		double rAngle = Math.atan2(strafe, A) * 180 / Math.PI;
		double lAngle = Math.atan2(strafe, B) * 180 / Math.PI;

		// Send to modules
		this.m_right.set(rAngle, rSpeed);
		this.m_left.set(lAngle, lSpeed);
	}

	public void resetPosition(double x, double y) {
		this.m_odometry = new SwerveDriveOdometry(
			this.m_kinematics,
			this.getAngle(),
			new Pose2d(x, y, new Rotation2d())
		);
		this.m_simulationAngle = 0.0;
		Robot.logger.addInfo("Swerve", "Reset robot's position");
	}

	public Pose2d getPosition() {
		// Get the robot's position from odometry
		return this.m_odometry.getPoseMeters();
	}

	public Rotation2d getAngle() {
		// If the robot is not in simulation
		if (Robot.isReal()) {
			// Print the negative value of the gyroscope
			return Rotation2d.fromDegrees(-Robot.gyro.getAngle());
		} else {
			// Print the negative value of the simulation gyro
			return Rotation2d.fromDegrees(-this.m_simulationAngle);
		}
	}

	public void setState(SwerveDriveState state) {
		// Update subsystem state
		this.m_state = state;
	}

	public SwerveDriveState getState() {
		// Return subsystem state
		return this.m_state;
	}

	public void followPath(Path path) {
		// Set the state to PATH (disables teleop swerve and backup curvature)
		this.m_state = SwerveDriveState.PATH;

		// Save path
		this.m_path = path;

		// Generate profiles
		this.generateProfile();
	}

	private void generateProfile() {
		// Determine velocity trapezoids for x,y,rot
		this.m_xGoal = new TrapezoidProfile.State(Robot.swerveDrive.getPosition().getX()+this.m_path.getCurrent().getX(), (this.m_path.getCurrent().getX() != 0.0 && this.m_path.getAhead().getX() != 0.0 && this.m_path.next()) ? Constants.AutoPath.kSmoothTransition : 0.0);
		this.m_yGoal = new TrapezoidProfile.State(Robot.swerveDrive.getPosition().getY()+this.m_path.getCurrent().getY(), (this.m_path.getCurrent().getY() != 0.0 && this.m_path.getAhead().getY() != 0.0 && this.m_path.next()) ? Constants.AutoPath.kSmoothTransition : 0.0);
		this.m_rGoal = new TrapezoidProfile.State(Robot.swerveDrive.getPosition().getRotation().getDegrees()+this.m_path.getCurrent().getRotation().getDegrees(), 0.0);

		// Save current position of x,y,rot
		this.m_xInitial = new TrapezoidProfile.State(Robot.swerveDrive.getPosition().getX(), 0);
		this.m_yInitial = new TrapezoidProfile.State(Robot.swerveDrive.getPosition().getY(), 0);
		this.m_rInitial = new TrapezoidProfile.State(Robot.swerveDrive.getPosition().getRotation().getDegrees(), 0);

		// Create profiles from current position and desired position using max accl/max vel
		this.m_xProfile = new TrapezoidProfile(this.m_translationalConstraints, this.m_xGoal, this.m_xInitial);
		this.m_yProfile = new TrapezoidProfile(this.m_translationalConstraints, this.m_yGoal, this.m_yInitial);
		this.m_rProfile = new TrapezoidProfile(this.m_rotationalConstraints, this.m_rGoal, this.m_rInitial);

		// Initial times
		System.out.println("xt"+this.m_xProfile.totalTime());
		System.out.println("yt"+this.m_yProfile.totalTime());

		// Normalize Profiles
		double max = Math.max(this.m_xProfile.totalTime(), this.m_yProfile.totalTime());

		double xRatio = this.m_xProfile.totalTime() == max ? 1.0 : Math.pow(this.m_xProfile.totalTime()/max, 2.035);
		double yRatio = this.m_yProfile.totalTime() == max ? 1.0 : Math.pow(this.m_yProfile.totalTime()/max, 2.035);

		System.out.println("xr "+xRatio);
		System.out.println("yr "+yRatio);

		this.m_xProfile = new TrapezoidProfile(new TrapezoidProfile.Constraints(Constants.Swerve.kMaxVelocity*xRatio, Constants.Swerve.kMaxAcceleration*xRatio), this.m_xGoal, this.m_xInitial);
		this.m_yProfile = new TrapezoidProfile(new TrapezoidProfile.Constraints(Constants.Swerve.kMaxVelocity*yRatio, Constants.Swerve.kMaxAcceleration*yRatio), this.m_yGoal, this.m_yInitial);

		// Normalized times
		System.out.println("nxt"+this.m_xProfile.totalTime());
		System.out.println("nyt"+this.m_yProfile.totalTime());

		// Save current time for calculating position in profile
		this.m_time = Timer.getFPGATimestamp();
	}

	@Override
	public void periodic() {
		// If swerve is following a path
		if (this.m_state == SwerveDriveState.PATH) {
			// If the robot is in the tolerance of the desired setpoint along the route
			// if (this.m_xFinish == false && PathUtil.withinTolerance2(this.m_xInitial.position, Robot.swerveDrive.getPosition().getX(), this.m_path.getCurrent().getX(), Constants.AutoPath.kTranslationTolerance)) {
			// 	this.m_xFinish = true;
			// }

			// if (this.m_yFinish == false && PathUtil.withinTolerance3(this.m_yInitial.position, Robot.swerveDrive.getPosition().getY(), this.m_path.getCurrent().getY(), Constants.AutoPath.kTranslationTolerance)) {
			// 	this.m_yFinish = true;
			// }

			// if (this.m_rFinish == false && PathUtil.withinTolerance2(this.m_rInitial.position, Robot.swerveDrive.getPosition().getRotation().getDegrees(), this.m_path.getCurrent().getRotation().getDegrees(), Constants.AutoPath.kRotationalTolerance)) {
			// 	this.m_rFinish = true;
			// }

			if (Timer.getFPGATimestamp()-this.m_time>=this.m_xProfile.totalTime()) {
				this.m_xFinish = true;
			}

			if (Timer.getFPGATimestamp()-this.m_time>=this.m_yProfile.totalTime()) {
				this.m_yFinish = true;
			}

			if (Timer.getFPGATimestamp()-this.m_time>=this.m_rProfile.totalTime()) {
				this.m_rFinish = true;
			}
			

			// System.out.println("xF " + this.m_xFinish);
			// System.out.println("yF " + this.m_yFinish);
			// System.out.println("rF " + this.m_rFinish);

			// If all movement is finished
			if(this.m_xFinish == true && this.m_yFinish == true && this.m_rFinish == true) {
				// If so, check if there is another point to target
				if (this.m_path.next()) {
					System.out.println("Next");
					
					// Increment route
					this.m_path.up();

					this.generateProfile();
				} else {
					// Since the robot is finished
					System.out.println("Path finished");

					// Stop the bot
					this.localSwerve(0, 0, 0, false);

					// Reset state to SWERVE, allowing teleop to take over when ready
					this.m_state = SwerveDriveState.SWERVE;
				}
				this.m_xFinish = false;
				this.m_yFinish = false;
				this.m_rFinish = false;
			}

			// Re-check if the robot is still in the PATH state since state is reset above if the path ends
			if (this.m_state == SwerveDriveState.PATH) {
				// Get the setpoint along the route at desired time
				this.m_xSetpoint = this.m_xFinish ? new TrapezoidProfile.State() : this.m_xProfile.calculate(Timer.getFPGATimestamp()-this.m_time);
				this.m_ySetpoint = this.m_yFinish ? new TrapezoidProfile.State() : this.m_yProfile.calculate(Timer.getFPGATimestamp()-this.m_time);
				this.m_rSetpoint = this.m_rFinish ? new TrapezoidProfile.State() : this.m_rProfile.calculate(Timer.getFPGATimestamp()-this.m_time);

				// Pass to swerve
				this.localSwerve(this.m_ySetpoint.velocity/Constants.Swerve.kMaxVelocity, this.m_xSetpoint.velocity/Constants.Swerve.kMaxVelocity, this.m_rSetpoint.velocity/Constants.Swerve.kMaxRotateSpeed, true);
	
				// Log to SmartDashboard
				SmartDashboard.putNumber("z_fwd", this.m_ySetpoint.velocity/Constants.Swerve.kMaxVelocity);
				SmartDashboard.putNumber("z_str", this.m_xSetpoint.velocity/Constants.Swerve.kMaxVelocity);
				SmartDashboard.putNumber("z_rot", this.m_rSetpoint.velocity/Constants.Swerve.kMaxRotateSpeed);
			}
		}

		// Update odometry
		this.m_odometry.update(
			this.getAngle(),
			this.m_left.getState(),
			this.m_right.getState()
		);

		// Update 2d field with robot position
		this.m_field.setRobotPose(this.getPosition());

		// Save to SmartDashboard
		SmartDashboard.putNumber("zr_x", this.getPosition().getX());
		SmartDashboard.putNumber("zr_y", this.getPosition().getY());
		SmartDashboard.putNumber("zr_rot", this.getPosition().getRotation().getDegrees());

		// Send field
		SmartDashboard.putData(this.m_field);
	}
}
