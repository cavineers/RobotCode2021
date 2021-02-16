package frc.robot;

public final class Constants {
    public static class CANIds {
        public static int kLeftDriveMotor    = 1;
        public static int kLeftRotateMotor   = 2;
        public static int kRightDriveMotor   = 3;
        public static int kRightRotateMotor  = 4;
        public static int kShooterMotor      = 5;
        public static int kIntakeMotor       = 6;
        public static int kTransportConveyer = 7;
        public static int kTransportFeeder   = 8;
        public static int kPowerDistributionPanel = 50;
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
        public static int kConveyerID = CANIds.kTransportConveyer;
        public static int kFeederID = CANIds.kTransportFeeder;
        public static double kInSpeedFeeder = 1; // TODO Update Vals
        public static double kOutSpeedFeeder = 1; // TODO Update Vals
        public static double kInSpeedConveyer = 1; // TODO Update Vals
        public static double kOutSpeedConveyer = 1; // TODO Update Vals
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
        
        // Right
        public static int kRightDriveID = CANIds.kRightDriveMotor;
        public static int kRightRotateID = CANIds.kRightRotateMotor;

        // Kinematics & Odometry
        // TODO: GET ACTUAL MAX VELOCITY
        public static double kMaxDriveSpeed = 5.0; // In Meters Per Second
        public static double kMaxAccel = 0.0; // In meters per second // TODO: Find max accel

        // Rotation PID
        // TODO: TUNE THESE
        public static double kRotationPID_P = 1.0;
        public static double kRotationPID_I = 0.0;
        public static double kRotationPID_D = 0.0;

        // Track Width
        public static double kTrackWidth = Sizing.kTrackWidthInches;
    }
}
