package frc.lib.autonomous;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.util.Units;

public class ExactPath extends Path {
    private ArrayList<Pose2d> m_points = new ArrayList<Pose2d>(0);

    private double m_dx = 0.0;
    private double m_dy = 0.0;
    private double m_dr = 0.0;

    public void add(Pose2d point) {
        this.m_points.add(new Pose2d(point.getX()-this.m_dx, point.getY()-this.m_dy, Rotation2d.fromDegrees(point.getRotation().getDegrees()-this.m_dr)));

        System.out.println("xdiff "+(point.getX()-this.m_dx));
        System.out.println("ydiff "+(point.getY()-this.m_dy));

        this.m_dx += point.getX()-this.m_dx;
        this.m_dy += point.getY()-this.m_dy;
        
        this.m_dr += point.getRotation().getDegrees()-this.m_dr;
    }

    public void addPlot(double x, double y, double r) {
        this.add(new Pose2d(Units.inchesToMeters(x), Units.inchesToMeters(y), Rotation2d.fromDegrees(r)));
        // this.add(new Pose2d(x, y, Rotation2d.fromDegrees(r))); // inches to debug
    }

    public void finish() {
        // this.m_plots = this.m_points;
        this.m_plots = this.m_points.toArray(new Pose2d[this.m_points.size()]);

        for (Pose2d pose2d : m_plots) {
            System.out.println("plot "+pose2d.getX()+" "+pose2d.getY());
        }
    }
}
