package frc.robot;

public final class Constants {
    public static class CANIds {
        public static int kLeftDriveMotor    = 1;
        public static int kLeftRotateMotor   = 2;
        public static int kRightDriveMotor   = 3;
        public static int kRightRotateMotor  = 4;
        public static int kShooterMotor      = 5;
        public static int kIntakeMotor       = 6;
        public static int kPowerDistributionPanel = 50;
    }

    // DANK
    public static class DANK {
        public static int PORT = 5808;
    }

    // Vision
    public static class Vision {
        // TODO: These are last years numbers, need updated for 2021
        public static double kFieldGoalHeightFromGround = 98.0; // 31 inches used for testing
        public static double kLimelightHeightFromGround = 18.0; // vertical distance from limelight to ground
        public static double kLimelightMountingAngle    = 25.0;
    }

    // Shooter
    public static class Shooter {
        public static int ShootID = CANIds.kShooterMotor;
        public static double PIDp = 0.0005;
        public static double PIDi = 0.0;
        public static double PIDd = 0.0;
        public static double PIDf = 0.00002;
        public static double MaxRPM = 5500;
        public static int CurrentLimit = 39;
    }

    // Intake
    public static class Intake {
        public static int IntakeID = CANIds.kIntakeMotor;
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
        // TODO: GET THESE VALUES
        public static int kModuleDistanceFromCenter = 0; // In Meters
        public static int kMaxDriveSpeed = 0; // In Meters

        // Rotation PID
        // TODO: TUNE THESE
        public static double kRotationPID_P = 1.0;
        public static double kRotationPID_I = 0.0;
        public static double kRotationPID_D = 0.0;

        // Value for swerving
        // TODO: I NEED NUMBERS
        public static double kWheelBase = 0.00; // Distance between the front and back wheels in inches
        public static double kTrackWidth = 12.75; // Distance between the inside of the left and right 2 wheels in inches
        public static double kTurnRadius = kTrackWidth/2;
    }
}
