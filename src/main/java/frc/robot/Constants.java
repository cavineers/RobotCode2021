package frc.robot;

import edu.wpi.first.wpilibj.util.Units;

/**
 * Robot Constants.
 */
public final class Constants {
    /**
     * Constants in direct reference to the idea/concept of the robot.
     */
    public static class Robot {
        public static String kYear = "2021";
        public static String kName = "MIG"; // "Mechanical is garbage"
    }

    /**
     * Constants in direct reference to their location on the CAN bus.
     */
    public static class CanIds {
        public static int kLeftDriveMotor         = 1;
        public static int kLeftRotateMotor        = 2;
        public static int kLeftRotateEncoder      = 3;
        public static int kRightDriveMotor        = 4;
        public static int kRightRotateMotor       = 5;
        public static int kRightRotateEncoder     = 6;
        public static int kShooterMotor           = 7;
        public static int kIntakeMotor            = 8;
        public static int kTransportConveyor      = 9;
        public static int kTransportFeeder        = 10;
        public static int kShooterHood            = 11;
        public static int kPowerDistributionPanel = 50;
    }

    /**
     * Constants in direct reference to their location on the Digital In/Out pins on the RIO.
     */
    public static class Dio {
        public static int kConveyorSensor1 = 0;
        public static int kConveyorSensor2 = 1;
        public static int kFeederSensor    = 2;
    }

    /**
     * Constants in direct reference to the Dashboard and Navigation Kit.
     */
    public static class Dank {
        public static int kPORT = 5808;
    }

    /**
     * Constants in direct reference to the Vision system.
     */
    public static class Vision {
        public static double kFieldGoalHeightFromGround = Units.inchesToMeters(98.0); // 31 inches used for testing
        public static double kLimelightHeightFromGround = Units.inchesToMeters(18.0); // vertical distance from limelight to ground
        public static double kLimelightMountingAngle    = 25.0; // TODO: Update mounting angle when complete
    }

    /**
     * Constants in direct reference to the Shooter subsystem.
     */
    public static class Shooter {
        public static int kShootID = CanIds.kShooterMotor;
        public static double kPIDp = 0.0005;
        public static double kPIDi = 0.0;
        public static double kPIDd = 0.0;
        public static double kPIDf = 0.00002;
        public static double kMaxRPM = 5500;
        public static int kCurrentLimit = 39;
    }

    /**
     * Constants in direct reference to the Intake subsystem.
     */
    public static class Intake {
        public static int kIntakeID = CanIds.kIntakeMotor;
        public static double kInSpeed = 1;
        public static double kOutSpeed = -0.12;
    }

    /**
     * Constants in direct reference to the Transportation subsystem.
     */
    public static class Transportation {
        public static int kConveyorID = CanIds.kTransportConveyor;
        public static int kFeederID = CanIds.kTransportFeeder;
        public static double kInSpeedFeeder = 1;
        public static double kOutSpeedFeeder = -0.12;
        public static double kInSpeedConveyor = 1;
        public static double kOutSpeedConveyor = -0.12;
        public static double kBeltLength = (2.0 * 15.748) * Math.PI;
        public static double kPulleyCircumference = 2 * Math.PI;
        public static double kGearRatio = 1;
        public static double kDistancePerRotation = (1 * kGearRatio) * (kPulleyCircumference * kBeltLength);
    }

    /**
     * Constants in direct reference to the sizing and dimensions of the Robot.
     */
    public static class Sizing {
        public static double kWheelBaseInches = 29.5;   // Distance between the front and back wheels in inches
        public static double kTrackWidthInches = 29.5; // Distance between the inside of the left and right 2 wheels in inches

        public static double kWheelBaseInchesBumpered = 36.0;   // Distance between the front and back wheels in inches
        public static double kTrackWidthInchesBumpered = 36.0; 

        public static double kWheelBaseMeters = Units.inchesToMeters(kWheelBaseInches);   // Distance between the front and back wheels in meters
        public static double kTrackWidthMeters = Units.inchesToMeters(kTrackWidthInches); // Distance between the inside of the left and right 2 wheels in meters

        public static double kWheelBaseMetersBumpered = Units.inchesToMeters(kWheelBaseInchesBumpered);   // Distance between the front and back wheels in meters
        public static double kTrackWidthMetersBumpered = Units.inchesToMeters(kTrackWidthInchesBumpered); // Distance between the inside of the left and right 2 wheels in meters
    }

    /**
     * Constants in direct reference to the Swerve subsystem.
     */
    public static class Swerve {
        // Left 
        public static int kLeftDriveID = CanIds.kLeftDriveMotor;
        public static int kLeftRotateID = CanIds.kLeftRotateMotor;
        public static int kLeftEncoderID = CanIds.kLeftRotateEncoder;
        public static int kLeftOffset = 217; // In Degrees
        
        // Right
        public static int kRightDriveID = CanIds.kRightDriveMotor;
        public static int kRightRotateID = CanIds.kRightRotateMotor;
        public static int kRightEncoderID = CanIds.kRightRotateEncoder;
        public static int kRightOffset = 274; // In Degrees

        // Kinematics & Odometry
        // TODO: GET ACTUAL MAX VELOCITY
        public static double kMaxVelocity = Units.feetToMeters(7.0); // In Feet Per Second (converted to meters)

        public static double kMaxRotateSpeed = 150.0; // In Degrees Per Second
        public static double kMaxRotateAcceleration = 30.0;

        // Rotation PID
        public static double kRotationPID_P = 0.0071;
        public static double kRotationPID_I = 0.0;
        public static double kRotationPID_D = 0.0;

        // Position PID
        // TODO: TUNE THESE
        public static double kPositionPID_P = 10.0;
        public static double kPositionPID_I = 0.0;
        public static double kPositionPID_D = 0.00000005;

        // Angle PID
        // TODO: TUNE THESE
        public static double kAnglePID_P = 0.03;
        public static double kAnglePID_I = 0.0;
        public static double kAnglePID_D = 0.0;

        // Track Width
        public static double kTrackWidth = Sizing.kTrackWidthMeters;
    }

    /**
     * Constants in direct reference to the object detection system.
     */
    public static class ObjVision {
        // Constants (in inches)
        public static double kCameraHeight = Units.inchesToMeters(3.0);
        public static double kCameraAngle  = 0.0; // In degrees

        public static double kBallHeight = Units.inchesToMeters(3.5);

        public static double kCameraResolutionY = 1080; // In pixels
        public static double kCameraResolutionX = 1920; // In pixels
        public static double kCameraFieldOfView = 180; // In degrees

        public static double kCameraInset   = Units.inchesToMeters(13);
        public static double kDistancePID_P = 0.001;
        public static double kDistancePID_I = 0.0;
        public static double kDistancePID_D = 0.0;
        public static double kDistancePID_Tolerance = 1.0;

        public static double kXPID_P = 0.001;
        public static double kXPID_I = 0.0;
        public static double kXPID_D = 0.0;
        public static double kXPID_Tolerance = 1.0;
    }

    /**
     * Constants in direct reference to the Hood subsystem.
     */
    public static class Hood {
        public static int kMotorID = CanIds.kShooterHood;
        public static double kHomingSpeed = 0.15;
        public static double kP = 0.0007; // TODO: Calculate PID values to make testing easier
        public static double kI = 0.0;
        public static double kD = 0.00000;
        public static double kTolerance = 5.0; // ~0.5 deg
        public static double kMaxSpeed = 0.9;
        public static double kMinimumAngle = 27.0;
        public static double kMaximumAngle = 90.0;
    }
}
