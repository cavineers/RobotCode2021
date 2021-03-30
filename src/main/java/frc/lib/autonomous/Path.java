package frc.lib.autonomous;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.util.Units;
import frc.robot.Robot;
import java.util.ArrayList;

/**
 * Path container.
 */
public class Path {
    protected Plot[] m_plots;
    private int m_current = 0;
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

    /**
     * Move to the next plot.
     */
    public void up() {
        if (next()) {
            this.m_current++;
        }
    }

    public boolean next() {
        return this.m_current + 1 != this.m_plots.length;
    }

    public void add(double x, double y, double r, double xVel, double yVel, double transitionTolerance, double rotationalTolerance) {
        this.m_points.add(new Plot(Units.inchesToMeters(x), Units.inchesToMeters(y), Rotation2d.fromDegrees(r), Units.feetToMeters(xVel), Units.feetToMeters(yVel), Units.inchesToMeters(transitionTolerance), rotationalTolerance));
    }

    /**
     * Complete creation of path.
     */
    protected void finish() {
        // this.m_plots = this.m_points;
        this.m_plots = this.m_points.toArray(new Plot[this.m_points.size()]);

        for (Pose2d pose2d : m_plots) {
            Robot.logger.addInfo("Path", "plot " + pose2d.getX() + " " + pose2d.getY());
        }
    }
}
