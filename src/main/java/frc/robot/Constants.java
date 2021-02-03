package frc.robot;

public final class Constants {
    public static class CANIds {
        public static int kLeftDriveMotor    = 1;
        public static int kLeftRotateMotor   = 2;
        public static int kRightDriveMotor   = 3;
        public static int kRightRotateMotor  = 4;

        public static int kPowerDistributionPanel = 50;
    }
    public static class DANK {
        public static int PORT = 5805;
    }

    public static class Vision {
        // TODO: These are last years numbers, need updated for 2021
        public static double kFieldGoalHeightFromGround = 98.0; // 31 inches used for testing
        public static double kLimelightHeightFromGround = 18.0; // vertical distance from limelight to ground
        public static double kLimelightMountingAngle    = 25.0;
    }

    

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
        public static double kWheelBase = 10.00; // Distance between the front and back wheels in inches
        public static double kTrackWidth = 12.75; // Distance between the inside of the left and right 2 wheels in inches
        public static double kTurnRadius = Math.hypot(kWheelBase, kTrackWidth);
    }
}
