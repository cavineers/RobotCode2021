package frc.lib.autonomous;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.util.Units;

public class Path {
    protected Plot[] m_plots;
    private int m_current = 0;
    public double m_TranslationTolerance;
    public double m_RotationalTolerance;
    private ArrayList<Plot> m_points = new ArrayList<Plot>(0);

    public Path() {}

    public Path(Plot[] path) {
        this.m_plots = path;
    }

    public Plot getCurrent() {
        return this.m_plots[this.m_current];
    }

    public void reset() {
        this.m_current = 0;
    }

    public Plot getAhead() {
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

    //

    public void add(Plot point) {
        this.m_points.add(point);
    }

    public void addPlot(double x, double y, double r, double transitionTolerance, double rotationalTolerance) {
        this.add(new Plot(Units.inchesToMeters(x), Units.inchesToMeters(y), Rotation2d.fromDegrees(r), Units.inchesToMeters(transitionTolerance), rotationalTolerance));
        // this.add(new Pose2d(x, y, Rotation2d.fromDegrees(r))); // inches to debug
    }

    public void finish() {
        // this.m_plots = this.m_points;
        this.m_plots = this.m_points.toArray(new Plot[this.m_points.size()]);

        for (Pose2d pose2d : m_plots) {
            System.out.println("plot "+pose2d.getX()+" "+pose2d.getY());
        }
    }
}
