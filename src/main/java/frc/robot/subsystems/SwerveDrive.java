package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.lib.control.ControllerDriveInput;
import frc.lib.control.Deadzone;
import frc.lib.swerve.SwerveModule;
import frc.lib.swerve.SwerveSettings;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 * Swerve Drive Subsystem.
 */
public class SwerveDrive extends SubsystemBase {
    // Swerve Modules
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
    private double m_desiredRotation = 0.0;

    // Field Oriented
    private boolean m_isFieldOriented = false;

    // Rotation PID
    private PIDController m_rPidController = new PIDController(
        Constants.Swerve.kAnglePIDp,
        Constants.Swerve.kAnglePIDi,
        Constants.Swerve.kAnglePIDd
    );

    /**
     * Swerve Drive Constructor.
    */
    public SwerveDrive() {
        this.m_kinematics = new SwerveDriveKinematics(
            new Translation2d(-Constants.Swerve.kTrackWidth, 0.0),
            new Translation2d(Constants.Swerve.kTrackWidth, 0.0)
        );

        this.m_odometry = new SwerveDriveOdometry(
            this.m_kinematics,
            this.getAngle(),
            new Pose2d(0, 0, new Rotation2d())
        );

        Robot.logger.addInfo("SwerveDrive", "Created SwerveDrive subsystem");
    }

    public boolean isFieldOriented() {
        return this.m_isFieldOriented;
    }

    public void setFieldOriented(boolean fo) {
        this.m_isFieldOriented = fo;
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

     * @param input Controller input.
     */
    public void swerve(ControllerDriveInput input) {
        if (this.m_state == SwerveDriveState.SWERVE) {
            this.localSwerve(input);
        }
    }

    /**
     * Drive subsystem with swerve.

     * @param input Controller input. 
     */
    public void heldSwerve(ControllerDriveInput input) {
        if (this.m_state == SwerveDriveState.OTHER_AUTO) {
            this.localSwerve(input);
        }
    }

    private void localSwerve(ControllerDriveInput input) {
        // If the robot is field oriented
        if (this.m_isFieldOriented) {
            // Find conversions based on gyro angles
            double sin = Math.sin(this.getAngle().getRadians());
            double cos = Math.cos(this.getAngle().getRadians());

            // Translate forward/strafe based on conversions
            double vT = (input.getFwd() * cos) + (input.getStf() * sin);
            input.setStf((-input.getFwd() * sin) + (input.getStf() * cos));
            input.setFwd(vT);
        }

        // Update simulation angle
        this.m_desiredRotation += input.getRot() * Constants.Swerve.kRotationConstant;

        double dRot = this.m_rPidController.calculate(this.getAngle().getDegrees(), this.m_desiredRotation);

        // Get A/B
        double aValue = input.getFwd() - dRot;
        double bValue = input.getFwd() + dRot;

        // Get motor speeds
        double rSpeed = Math.sqrt(Math.pow(input.getStf(), 2.0) + Math.pow(aValue, 2.0));
        double lSpeed = Math.sqrt(Math.pow(input.getStf(), 2.0) + Math.pow(bValue, 2.0));

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
        double rAngle = Math.atan2(input.getStf(), aValue) * 180.0 / Math.PI;
        double lAngle = Math.atan2(input.getStf(), bValue) * 180.0 / Math.PI;

        this.m_right.set(rAngle, rSpeed);
        this.m_left.set(lAngle, lSpeed);
    }

    /**
     * Reset the position of the robot.
     */
    public void resetPosition(double x, double y, double r) {
        this.m_odometry = new SwerveDriveOdometry(this.m_kinematics, Rotation2d.fromDegrees(r),
                new Pose2d(x, y, new Rotation2d()));
        this.m_desiredRotation = r;
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
            return Rotation2d.fromDegrees(-this.m_desiredRotation);
        }
    }

    public void setAngle(Rotation2d angle) {
        this.m_desiredRotation = angle.getDegrees();
    }

    public void setState(SwerveDriveState state) {
        // Update subsystem state
        this.m_state = state;
    }

    public SwerveDriveState getState() {
        // Return subsystem state
        return this.m_state;
    }

    @Override
    public void periodic() {
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
