package frc.lib.autonomous;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Transform2d;
import edu.wpi.first.wpilibj.util.Units;

public class Path {
    protected Plot[] m_plots;
    private int m_current = 0;
    public double m_TranslationTolerance;
    public double m_RotationalTolerance;

    public Path() {}

    public Path(Plot[] path) {
        this.m_plots = path;
    }

    public Pose2d getCurrent() {
        return this.m_plots[this.m_current];
    }

    public void reset() {
        this.m_current = 0;
    }

    public Pose2d getAhead() {
        if (this.m_current+1 != this.m_plots.length) {
            return this.m_plots[this.m_current+1];
        } else {
            return this.getCurrent();
        }
    }

    public void up() {
        if (next()) {
            this.m_current++;
        }
    }

    public boolean next() {
        return this.m_current+1 != this.m_plots.length;
    }

    public void setTolerance(double TransitionTolerance, double RotationalTolerance) {
        this.m_TranslationTolerance = Units.inchesToMeters(TransitionTolerance);
        this.m_RotationalTolerance = Units.inchesToMeters(RotationalTolerance);
    }

    public double getRotationalTolerance() {
        return m_RotationalTolerance;
    }

    public double getTranslationTolerance() {
        return m_TranslationTolerance;
    }
}
