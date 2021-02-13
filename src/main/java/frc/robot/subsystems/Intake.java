package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

    // Intake Mode
    public enum IntakeMode {
        ENABLED, DISABLED
    }

    // Talon SRX Motor
    private TalonSRX m_intakeMotor = new TalonSRX(Constants.Intake.IntakeID);

    // Current intake mode
    private IntakeMode m_currentMode = IntakeMode.DISABLED;

    public Intake() {
        // TODO Add motor code based on Intake plan for motor stuff
        // Implement SMART dashboard logging
    }

    /**
     * Turn on the intake
     */
    public void enable() {
        this.m_currentMode = IntakeMode.ENABLED;
    }

    /**
     * Turn off the intake
     */
    public void disable() {
        this.m_currentMode = IntakeMode.DISABLED;
    }

    /**
     * Get the current intake mode
     * @return IntakeMode enum
     */
    public IntakeMode getCurrentMode() {
        return this.m_currentMode;
    }
}
