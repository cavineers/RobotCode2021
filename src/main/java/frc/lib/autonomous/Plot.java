package frc.lib.autonomous;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

public class Plot extends Pose2d {
    private double m_transitionTolerance;
    private double m_rotationalTolerance;

    public Plot(double x, double y, Rotation2d rotation2d, double RotationTolerance, double TransitionTolerance) {
        super(x, y, rotation2d);
        setTransitionTolerance(TransitionTolerance);
        setRotationTolerance(RotationTolerance);
    }

    public void setTransitionTolerance(double tolerance) {
        this.m_transitionTolerance = tolerance;
    }

    public void setRotationTolerance(double tolerance) {
        this.m_rotationalTolerance = tolerance;
    }

    public double getTranslationTolerance() {
        return this.m_transitionTolerance;
    }

    public double getRotationTolerance() {
        return this.m_rotationalTolerance;
    }
}
