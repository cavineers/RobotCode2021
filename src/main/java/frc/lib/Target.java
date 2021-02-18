package frc.lib;

public class Target {
    private boolean m_set = false;

    private double m_ty;
    private double m_tx;
    private double m_distance;

    public Target() {

    }

    public Target(double distance, double ty, double tx) {
        this.m_ty = ty;
        this.m_tx = tx;
        this.m_distance = distance;
        this.m_set = true;
    }

    public double getTy() {
        return this.m_ty;
    }

    public double getTx() {
        return this.m_tx;
    }

    public double getDistance() {
        return this.m_distance;
    }
    
    public boolean isSet() {
        return this.m_set;
    }
}
