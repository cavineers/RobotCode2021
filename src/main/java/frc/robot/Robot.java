package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
	private final RobotContainer m_robotContainer = RobotContainer.getInstance();
	private final Logger m_logger = Logger.getInstance();

	public Robot() {
		super(0.02);
	}

	@Override
	public void robotInit() {
		this.m_logger.addInfo("robot", "Initializing...");
	}

	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();
	}

	@Override
	public void disabledInit() {
		this.m_logger.addInfo("robot", "Robot Disabled");
	}

	@Override
	public void disabledPeriodic() {}

	@Override
	public void autonomousInit() {
		this.m_logger.addInfo("robot", "Robot running in Autonomous");
		// m_autonomousCommand = m_robotContainer.getAutonomousCommand();

		// if (m_autonomousCommand != null) {
		// 	m_autonomousCommand.schedule();
		// }
	}

	@Override
	public void autonomousPeriodic() {}

	@Override
	public void teleopInit() {
		this.m_logger.addInfo("robot", "Robot running in Teleop");
		// if (m_autonomousCommand != null) {
		// 	m_autonomousCommand.cancel();
		// }
	}

	@Override
	public void teleopPeriodic() {}

	@Override
	public void testInit() {
		this.m_logger.addInfo("robot", "Robot running in Test");

		CommandScheduler.getInstance().cancelAll();
	}

	@Override
	public void testPeriodic() {}

	@Override
	public void simulationInit() {
		this.m_logger.addInfo("robot", "Robot running in Simulation");
		System.out.println("*** Running in Simulation ***");
	}

	@Override
	public void simulationPeriodic() {}
}
