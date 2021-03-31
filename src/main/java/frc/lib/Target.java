package frc.lib;

/**
 * Target data.
 */
public class Target {
    private boolean m_set = false;

    private double m_ty;
    private double m_tx;
    private double m_distance;
    private double m_offset;

    public Target() {}

    /**
     * Create target.

     * @param distance Distance from robot.
     * @param ty X in degrees.
     * @param tx Y in degrees.
     */
    public Target(double distance, double ty, double tx) {
        this.m_ty = ty;
        this.m_tx = tx;
        this.m_distance = distance;
        this.m_set = true;
        this.m_offset = 0.0;
    }

    public double getTy() {
        return this.m_ty;
    }

    public double getTx() {
        return this.m_tx;
    }

    public double getDistance() {
        return this.m_distance + this.m_offset;
    }

    public double getRawDistance() {
        return this.m_distance;
    }
    
    public boolean isSet() {
        return this.m_set;
    }

    public Target setOffset(double offset) {
        this.m_offset = offset;
        return this;
    }

    public double getOffset() {
        return this.m_offset;
    }
}
