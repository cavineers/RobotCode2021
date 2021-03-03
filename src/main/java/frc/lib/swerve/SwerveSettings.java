package frc.lib.swerve;

import edu.wpi.first.wpilibj.geometry.Rotation2d;

/**
 * SwerveDrive Settings.
 */
public class SwerveSettings {
    private int m_rotationMotorId;
    private int m_driveMotorId;
    private int m_encoderId;
    private Rotation2d m_offset;
    private boolean m_inverted = false;
    private String m_commonName = "";

    public SwerveSettings() {}

    public SwerveSettings setRotationMotorId(int id) {
        this.m_rotationMotorId = id;
        return this;
    }

    public SwerveSettings setDriveMotorId(int id) {
        this.m_driveMotorId = id;
        return this;
    }

    public SwerveSettings setRotationEncoderId(int id) {
        this.m_encoderId = id;
        return this;
    }

    public SwerveSettings setRotationOffset(Rotation2d offset) {
        this.m_offset = offset;
        return this;
    }

    public SwerveSettings setInverted(boolean inverted) {
        this.m_inverted = inverted;
        return this;
    }

    public SwerveSettings setCommonName(String name) {
        this.m_commonName = name;
        return this;
    }

    public int getRotationMotorId() {
        return this.m_rotationMotorId;
    }

    public int getDriveMotorId() {
        return this.m_driveMotorId;
    }

    public int getEncoderId() {
        return this.m_encoderId;
    }

    public Rotation2d getRotationOffset() {
        return this.m_offset;
    }

    public boolean isInverted() {
        return this.m_inverted;
    }

    public String commonName() {
        return this.m_commonName;
    }
}