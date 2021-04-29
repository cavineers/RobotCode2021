package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.State;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.lib.autonomous.Path;
import frc.lib.control.Deadzone;
import frc.lib.swerve.SwerveModule;
import frc.lib.swerve.SwerveSettings;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 * Swerve Drive Subsystem.
 */
public class SwerveDrive extends SubsystemBase {
    private SwerveModule m_left = new SwerveModule(new SwerveSettings().setDriveMotorId(Constants.Swerve.kLeftDriveID)
            .setRotationMotorId(Constants.Swerve.kLeftRotateID).setRotationEncoderId(Constants.Swerve.kLeftEncoderID)
            .setRotationOffset(Rotation2d.fromDegrees(Constants.Swerve.kLeftOffset)).setInverted(false)
            .setCommonName("left_"));
    private SwerveModule m_right = new SwerveModule(new SwerveSettings().setDriveMotorId(Constants.Swerve.kRightDriveID)
            .setRotationMotorId(Constants.Swerve.kRightRotateID).setRotationEncoderId(Constants.Swerve.kRightEncoderID)
            .setRotationOffset(Rotation2d.fromDegrees(Constants.Swerve.kRightOffset)).setInverted(false)
            .setCommonName("right_"));

    /**
     * Swerve Drive state.
     */
    public enum SwerveDriveState {
        SWERVE, CURVATURE, PATH, OTHER_AUTO
    }

    // Current Swerve State
    private SwerveDriveState m_state = SwerveDriveState.SWERVE;

    // Swerve Kinematics
    private SwerveDriveKinematics m_kinematics;

    // Swerve Odometry
    private SwerveDriveOdometry m_odometry;

    // 2D Field
    private Field2d m_field = new Field2d();

    // Simulation Angle
    private double m_simulationAngle = 0.0;

    // Current Path
    private Path m_path;

    // X, Y, Rot. PID Controllers
    private ProfiledPIDController m_xPidController = new ProfiledPIDController(Constants.Swerve.kPositionPID_P,
            Constants.Swerve.kPositionPID_I, Constants.Swerve.kPositionPID_D, new TrapezoidProfile.Constraints(Constants.Swerve.kMaxVelocity, Constants.Swerve.kMaxAcceleration));
    private ProfiledPIDController m_yPidController = new ProfiledPIDController(Constants.Swerve.kPositionPID_P,
            Constants.Swerve.kPositionPID_I, Constants.Swerve.kPositionPID_D, new TrapezoidProfile.Constraints(Constants.Swerve.kMaxVelocity, Constants.Swerve.kMaxAcceleration));
    private PIDController m_rPidController = new PIDController(Constants.Swerve.kAnglePID_P,
            Constants.Swerve.kAnglePID_I, Constants.Swerve.kAnglePID_D);

    // Whether the path is relative to the last point
    private boolean m_isRelative = false;
    private Pose2d m_initialPosition;
    private Rotation2d m_initialRotation;

    private double m_pathStartTime = 0.0;

    /**
     * Swerve Drive Constructor.
    */
    public SwerveDrive() {
        this.m_kinematics = new SwerveDriveKinematics(new Translation2d(-Constants.Swerve.kTrackWidth, 0.0),
                new Translation2d(Constants.Swerve.kTrackWidth, 0.0));

        this.m_odometry = new SwerveDriveOdometry(this.m_kinematics, this.getAngle(),
                new Pose2d(0, 0, new Rotation2d()));

        Robot.logger.addInfo("SwerveDrive", "Created SwerveDrive subsystem");
    }

    /**
     * Drive the swerve motors like a differential drive using curvature. (Used for
     * autonomous purposes)

     * @param speed    Drive Speed
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

    /**
     * Drive subsystem with swerve.

     * @param forward Forward values (-1...1)
     * @param strafe Strafe value (-1...1)
     * @param rotate Rotate value (-1...1)
     * @param isFieldOriented Whether it's based on the field or robot
     */
    public void swerve(double forward, double strafe, double rotate, boolean isFieldOriented) {
        if (this.m_state == SwerveDriveState.SWERVE) {
            this.localSwerve(forward, strafe, rotate, isFieldOriented);
        }
    }

    /**
     * Drive subsystem with swerve.

     * @param forward Forward values (-1...1)
     * @param strafe Strafe value (-1...1)
     * @param rotate Rotate value (-1...1)
     * @param isFieldOriented Whether it's based on the field or robot
     */
    public void heldSwerve(double forward, double strafe, double rotate, boolean isFieldOriented) {
        if (this.m_state == SwerveDriveState.OTHER_AUTO) {
            this.localSwerve(forward, strafe, rotate, isFieldOriented);
        }
    }

    private void localSwerve(double forward, double strafe, double rotate, boolean isFieldOriented) {
        // forward = MathUtil.clamp(forward, -1.0, 1.0);
        // strafe = MathUtil.clamp(strafe, -1.0, 1.0);
        // rotate = MathUtil.clamp(rotate, -1.0, 1.0);

        // If the robot is field oriented
        if (isFieldOriented) {
            // Find conversions based on gyro angles
            double sin = Math.sin(this.getAngle().getRadians());
            double cos = Math.cos(this.getAngle().getRadians());

            // Translate forward/strafe based on conversions
            double vT = (forward * cos) + (strafe * sin);
            strafe = (-forward * sin) + (strafe * cos);
            forward = vT;
        }

        // Update simulation angle
        this.m_simulationAngle += rotate * 3.0;

        // Get A/B
        double aValue = forward - rotate;
        double bValue = forward + rotate;

        // Get motor speeds
        double rSpeed = Math.sqrt(Math.pow(strafe, 2.0) + Math.pow(aValue, 2.0));
        double lSpeed = Math.sqrt(Math.pow(strafe, 2.0) + Math.pow(bValue, 2.0));

        // Get max of the two
        double max = Math.max(rSpeed, lSpeed);

        // Normalize speeds
        if (max > 1.0) {
            rSpeed /= max;
            lSpeed /= max;
        }

        // If robot is in teleop swerve, then apply deadzone to output to resolve drift
        if (this.m_state == SwerveDriveState.SWERVE) {
            rSpeed = Deadzone.apply(rSpeed, 0.1);
            lSpeed = Deadzone.apply(lSpeed, 0.1);
        }

        // Get the rotation angles
        double rAngle = Math.atan2(strafe, aValue) * 180.0 / Math.PI;
        double lAngle = Math.atan2(strafe, bValue) * 180.0 / Math.PI;

        // Send to modules
        SmartDashboard.putNumber("rAngle", rAngle);
        SmartDashboard.putNumber("lAngle", lAngle);

        this.m_right.set(rAngle, rSpeed);
        this.m_left.set(lAngle, lSpeed);
    }

    /**
     * Reset the position of the robot.
     */
    public void resetPosition(double x, double y, double r) {
        this.m_odometry = new SwerveDriveOdometry(this.m_kinematics, Rotation2d.fromDegrees(r),
                new Pose2d(x, y, new Rotation2d()));
        this.m_simulationAngle = r;
        Robot.logger.addInfo("Swerve", "Reset robot's position");
    }

    public Pose2d getPosition() {
        // Get the robot's position from odometry
        return this.m_odometry.getPoseMeters();
    }

    /**
     * Retrieve the Angle.
     */
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

    /**
     * Follow a path.

     * @param path Path to follow
     * @param isRelative Relative to robot
     */
    public void followPath(Path path, boolean isRelative) {
        // Set the state to PATH (disables teleop swerve and backup curvature)
        this.m_state = SwerveDriveState.PATH;

        // Save path
        this.m_path = path;

        // Save relative vs absolute
        this.m_isRelative = isRelative;

        // Save initial position
        this.m_initialPosition = this.getPosition();

        // Save initial rotation
        this.m_initialRotation = this.getAngle();

        // Set start time
        this.m_pathStartTime = Timer.getFPGATimestamp();

        // Generate profiles
        this.generateProfile();
    }

    private void generateProfile() {
        // System.out.println(this.m_path.getCurrent().getX());
        // System.out.println(this.m_path.getCurrent().getY());
        this.m_xPidController.setTolerance(this.m_path.getCurrent().getTranslationTolerance(), Constants.Swerve.kVelocityTolerance);
        this.m_yPidController.setTolerance(this.m_path.getCurrent().getTranslationTolerance(), Constants.Swerve.kVelocityTolerance);
        this.m_rPidController.setTolerance(this.m_path.getCurrent().getRotationTolerance());
        if (this.m_isRelative) {
            this.m_xPidController.setGoal(new State(this.getPosition().getX() + this.m_path.getCurrent().getX(), this.m_path.getCurrent().getXVelocity()));
            this.m_yPidController.setGoal(new State(this.getPosition().getY() + this.m_path.getCurrent().getY(), this.m_path.getCurrent().getYVelocity()));
            this.m_rPidController.setSetpoint(this.getPosition().getRotation().getDegrees()
                    + this.m_path.getCurrent().getRotation().getDegrees());
        } else {
            this.m_xPidController.setGoal(new State(this.m_initialPosition.getX() + this.m_path.getCurrent().getX(), this.m_path.getCurrent().getXVelocity()));
            this.m_yPidController.setGoal(new State(this.m_initialPosition.getY() + this.m_path.getCurrent().getY(), this.m_path.getCurrent().getYVelocity()));
            this.m_rPidController.setSetpoint(
                    this.m_initialRotation.getDegrees() + this.m_path.getCurrent().getRotation().getDegrees());
        }
    }

    @Override
    public void periodic() {
        // If swerve is following a path
        if (this.m_state == SwerveDriveState.PATH) {
            // System.out.println(this.m_path.getCurrent().getRotationTolerance());
            // System.out.println("Check: " + this.m_xPidController.atSetpoint() + " " + this.m_yPidController.atSetpoint() + " " + this.m_rPidController.atSetpoint());
            // If all movement is finished
            if (this.m_xPidController.atGoal() && this.m_yPidController.atGoal()
                    && this.m_rPidController.atSetpoint()) {
                // If so, check if there is another point to target
                if (this.m_path.next()) {
                    Robot.logger.addInfo("SwerveDrive", "Next path segment");

                    // Increment route
                    this.m_path.up();

                    this.generateProfile();
                } else {
                    // Since the robot is finished
                    Robot.logger.addInfo("SwerveDrive", "Path finished");

                    // Stop the bot
                    this.m_left.set(0.0, 0.0);
                    this.m_right.set(0.0, 0.0);

                    // Reset state to SWERVE, allowing teleop to take over when ready
                    this.m_state = SwerveDriveState.SWERVE;

                    Robot.logger.addInfo("SwerveDrive", "Path finished after " + (Timer.getFPGATimestamp() - this.m_pathStartTime) + " seconds");
                }
            }

            // Re-check if the robot is still in the PATH state since state is reset above
            // if the path ends
            if (this.m_state == SwerveDriveState.PATH) {
                double xOutput = this.m_xPidController.calculate(this.getPosition().getX());
                double yOutput = this.m_yPidController.calculate(this.getPosition().getY());
                double rOutput = this.m_rPidController.calculate(this.getAngle().getDegrees());

                SmartDashboard.putNumber("xOutput", xOutput);
                // SmartDashboard.putNumber("xSetpoint", this.m_xPidController.getSetpoint());
                SmartDashboard.putNumber("yOutput", yOutput);
                // SmartDashboard.putNumber("ySetpoint", this.m_yPidController.getSetpoint());
                SmartDashboard.putNumber("rOutput", rOutput);
                // SmartDashboard.putNumber("rSetpoint", this.m_rPidController.getSetpoint());
                // System.out.println("Setpoint: "+this.m_xPIDController.getSetpoint()+"
                // "+this.m_yPIDController.getSetpoint()+"
                // "+this.m_rPIDController.getSetpoint());
                // System.out.println("Current: "+this.getPosition().getX()+"
                // "+this.getPosition().getY()+" "+this.getAngle().getDegrees());
                // System.out.println("Output: "+xOutput+" "+yOutput+" "+rOutput);
                // System.out.println("Percent: "+xOutput/Constants.Swerve.kMaxVelocity+"
                // "+yOutput/Constants.Swerve.kMaxVelocity+" "+rOutput);
                // -(yOutput / Constants.Swerve.kMaxVelocity)
                this.localSwerve(-(xOutput / Constants.Swerve.kMaxVelocity), -(yOutput / Constants.Swerve.kMaxVelocity), rOutput, true);
                // this.localSwerve(-1.0, 0.0, 0.0, true);
                // this.localSwerve(xOutput / Constants.Swerve.kMaxVelocity, 0.0, 0.0, true);
                // this.localSwerve(yOutput / Constants.Swerve.kMaxVelocity, 0.0, 0.0, true);
                // this.localSwerve(-(xOutput / Constants.Swerve.kMaxVelocity), 0.0, 0.0, true);
                // this.localSwerve(0.0, 0.11, 0.0, true);
            }
        }

        this.m_left.periodic();
        this.m_right.periodic();

        // Update odometry
        this.m_odometry.update(this.getAngle(), this.m_left.getState(), this.m_right.getState());

        // Update 2d field with robot position
        this.m_field.setRobotPose(this.getPosition());

        // Save to SmartDashboard
        SmartDashboard.putNumber("zr_x", this.getPosition().getX());
        SmartDashboard.putNumber("zr_y", this.getPosition().getY());
        SmartDashboard.putNumber("zr_rot", this.getPosition().getRotation().getDegrees());

        // Send field
        SmartDashboard.putData(this.m_field);
    }
    
    /** 
     * Periodic Method. 
    */
    public void testPeriodic() {
        if (DriverStation.getInstance().isTest() && Robot.isSimulation()) {
            System.out.println("Simulated X: "
                    + Units.metersToInches(this.m_field.getRobotPose().getX() - Units.inchesToMeters(30)));
            System.out.println("Simulated Y: "
                    + Units.metersToInches(this.m_field.getRobotPose().getY() - Units.inchesToMeters(90)));
            System.out.println("Simulated R: " + this.m_field.getRobotPose().getRotation().getDegrees());
            return;
        }
    }
}
