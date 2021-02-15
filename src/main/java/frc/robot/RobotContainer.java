package frc.robot;
import frc.robot.subsystems.Intake;
public class RobotContainer {
	private static RobotContainer m_instance;

	// Subsystems
	public Intake intake = new Intake(this);

	public RobotContainer() {
		Logger.getInstance().addInfo("RobotContainer", "Created RobotContainer instance");
		mapButtonBindings();
	}


    public static RobotContainer getInstance() {
        if (m_instance == null) {
            m_instance = new RobotContainer();
        }

        return m_instance;
    }

	private void mapButtonBindings() {
		Logger.getInstance().addInfo("RobotContainer", "Start to map button bindings");
	}

	// public Command getAutonomousCommand() {
	// 	return m_autoCommand;
	// }
}
