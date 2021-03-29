package frc.lib.autonomous;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

/**
 * Plot for autonomous path that includes robot's position and tolerances.
 */
public class Plot extends Pose2d {
    private double m_translationTolerance;
    private double m_rotationalTolerance;
    private double m_xVelocity;
    private double m_yVelocity;

    /**
     * Create Plot.

     * @param x X Position
     * @param y Y Position
     * @param rotation2d Rotation
     * @param xVel X Velocity
     * @param yVel Y Velocity
     * @param translationTolerance Translation Tolerance
     * @param rotationTolerance Rotation Tolerance
     */
    public Plot(double x, double y, Rotation2d rotation2d, double xVel, double yVel, double translationTolerance, double rotationTolerance) {
        super(x, y, rotation2d);
        this.m_xVelocity = xVel;
        this.m_yVelocity = yVel;
        this.m_translationTolerance = translationTolerance;
        this.m_rotationalTolerance = rotationTolerance;
    }

    public double getTranslationTolerance() {
        return this.m_translationTolerance;
    }

    public double getRotationTolerance() {
        return this.m_rotationalTolerance;
    }

    public double getXVelocity() {
        return this.m_xVelocity;
    }

    public double getYVelocity() {
        return this.m_yVelocity;
    }
}
