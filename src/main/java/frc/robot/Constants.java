package frc.robot;

import edu.wpi.first.wpilibj.util.Units;
public final class Constants {
    public static class Robot {
        public static String kYear = "2021";
        public static String kName = "MIG"; // "Mechanical is garbage"
    }
    public static class CANIds {
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

    public static class DIO {
        public static int kConveyorSensor1 = 0;
        public static int kConveyorSensor2 = 1;
        public static int kFeederSensor = 2;
    }

    // DANK
    public static class DANK {
        public static int kPORT = 5808;
    }

    // Vision
    public static class Vision {
        public static double kFieldGoalHeightFromGround = 98.0; // 31 inches used for testing
        public static double kLimelightHeightFromGround = 18.0; // vertical distance from limelight to ground
        public static double kLimelightMountingAngle    = 25.0; // TODO: Update mounting angle when complete
    }

    // Shooter
    public static class Shooter {
        public static int kShootID = CANIds.kShooterMotor;
        public static double kPIDp = 0.0005;
        public static double kPIDi = 0.0;
        public static double kPIDd = 0.0;
        public static double kPIDf = 0.00002;
        public static double kMaxRPM = 5500;
        public static int kCurrentLimit = 39;
    }

    // Intake
    public static class Intake {
        public static int kIntakeID = CANIds.kIntakeMotor;
        public static double kInSpeed = 1;
        public static double kOutSpeed = -0.12;
    }

    public static class Transportation {
        public static int kConveyorID = CANIds.kTransportConveyor;
        public static int kFeederID = CANIds.kTransportFeeder;
        public static double kInSpeedFeeder = 1;
        public static double kOutSpeedFeeder = -0.12;
        public static double kInSpeedConveyor = 1;
        public static double kOutSpeedConveyor = -0.12;
        public static double kBeltLength = (2.0*15.748)*Math.PI;
        public static double kPulleyCircumference = 2 * Math.PI;
        public static double kGearRatio = 1;
        public static double kDistancePerRotation = (1*kGearRatio)*(kPulleyCircumference*kBeltLength);
    }

    public static class Sizing {
        public static double kWheelBaseInches = 29.5;   // Distance between the front and back wheels in inches
        public static double kTrackWidthInches = 29.5; // Distance between the inside of the left and right 2 wheels in inches

        public static double kWheelBaseMeters = 0.7493;   // Distance between the front and back wheels in meters
        public static double kTrackWidthMeters = 0.7493; // Distance between the inside of the left and right 2 wheels in meters
    }
    // Swerve
    public static class Swerve {
        // Left 
        public static int kLeftDriveID = CANIds.kLeftDriveMotor;
        public static int kLeftRotateID = CANIds.kLeftRotateMotor;
        public static int kLeftEncoderID = CANIds.kLeftRotateEncoder;
        public static int kLeftOffset = 0; // In Degrees
        
        // Right
        public static int kRightDriveID = CANIds.kRightDriveMotor;
        public static int kRightRotateID = CANIds.kRightRotateMotor;
        public static int kRightEncoderID = CANIds.kRightRotateEncoder;
        public static int kRightOffset = 0; // In Degrees

        // Kinematics & Odometry
        // TODO: GET ACTUAL MAX VELOCITY
        public static double kMaxVelocity = Units.feetToMeters(10.0); // In Meters Per Second
        // public static double kMaxAcceleration = Units.feetToMeters(2.0); // In meters per second // TODO: Find max accel

        public static double kMaxRotateSpeed = 150.0; // In Degrees Per Second
        public static double kMaxRotateAcceleration = 30.0;

        // Rotation PID
        // TODO: TUNE THESE
        public static double kRotationPID_P = 1.0;
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

    // Object Detection Vision
    public static class ObjVision {
        // Constants (in inches)
        public static double kCameraHeight      = Units.inchesToMeters(3.0);
        public static double kCameraAngle       = 0.0; // In degrees

        public static double kBallHeight        = Units.inchesToMeters(3.5);

        public static double kCameraResolutionY = 1080; // In pixels
        public static double kCameraResolutionX = 1920; // In pixels
        public static double kCameraFieldOfView = 75 * 2; // In degrees
    }

    // Hood
    public static class Hood {
        public static int kMotorID = CANIds.kShooterHood;
        public static double kHomingSpeed = 0.15;
        public static double kP = 0.0007; // TODO: Calculate PID values to make testing easier
        public static double kI = 0.0;
        public static double kD = 0.00000;
        public static double kTolerance = 5.0; // ~0.5 deg
        public static double kMaxSpeed = 0.9;
    }

    public static class AutoPath {
        // public static double kTranslationTolerance = Units.inchesToMeters(1);
        // public static double kRotationalTolerance = 1.0;
        public static double kSmoothTransition = 0.0;
    }
}
