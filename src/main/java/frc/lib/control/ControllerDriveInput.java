package frc.lib.control;

/**
 * A class to act as a record for storing controller input.
 */
public class ControllerDriveInput {
    private double m_fwd;
    private double m_stf;
    private double m_rot;

    /**
     * A class to act as a record for storing controller input.

     * @param fwd Forward (-1...1)
     * @param stf Strafe (-1...1)
     * @param rot Rotation (-1...1)
     */
    public ControllerDriveInput(double fwd, double stf, double rot) {
        this.m_fwd = fwd;
        this.m_stf = stf;
        this.m_rot = rot;
    }

    public double getFwd() {
        return this.m_fwd;
    }

    public double getStf() {
        return this.m_stf;
    }

    public double getRot() {
        return this.m_rot;
    }

    public ControllerDriveInput setFwd(double fwd) {
        this.m_fwd = fwd;
        return this;
    }

    public ControllerDriveInput setStf(double stf) {
        this.m_stf = stf;
        return this;
    }

    public ControllerDriveInput setRot(double rot) {
        this.m_rot = rot;
        return this;
    }
}