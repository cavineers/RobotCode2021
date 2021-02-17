package frc.robot;

import java.net.UnknownHostException;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.TeleopDrive;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Transportation;

public class Robot extends TimedRobot {
	// Robot Container
	public static RobotContainer robotContainer;

	// Logger
	public static Logger logger;

	// Dank
	public static DANK dank;

	// Shared Sensors
	public static AHRS gyro;
	public static Limelight limelight;

	// Subsystems
	public static Intake intake;
	public static SwerveDrive swerveDrive;
	public static Shooter shooter;
	public static Transportation transportation;

	public Robot() {
		super(0.02);

		// Static robot container
		robotContainer = new RobotContainer();

		// Static logger
		logger = new Logger();

		// Static DANK
		try {
			dank = new DANK();
			dank.start();
		} catch (UnknownHostException e) {
			logger.addInfo("DANK", "Error starting WS");
		}

		// Shared Sensors
		gyro = new AHRS(Port.kMXP);

		// Subsystems
		intake = new Intake();
		swerveDrive = new SwerveDrive();
		shooter = new Shooter();
		transportation = new Transportation();
	}

	@Override
	public void robotInit() {
		logger.addInfo("robot", "Initializing...");
	}

	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();
	}

	@Override
	public void disabledInit() {
		logger.addInfo("robot", "Robot Disabled");
	}

	@Override
	public void disabledPeriodic() {}

	@Override
	public void autonomousInit() {
		logger.addInfo("robot", "Robot running in Autonomous");
		// m_autonomousCommand = m_robotContainer.getAutonomousCommand();

		// if (m_autonomousCommand != null) {
		// 	m_autonomousCommand.schedule();
		// }
	}

	@Override
	public void autonomousPeriodic() {}

	@Override
	public void teleopInit() {
		logger.addInfo("robot", "Robot running in Teleop");
		// if (m_autonomousCommand != null) {
		// 	m_autonomousCommand.cancel();
		// }

		new TeleopDrive().schedule(false);
	}

	@Override
	public void teleopPeriodic() {}

	@Override
	public void testInit() {
		logger.addInfo("robot", "Robot running in Test");

		CommandScheduler.getInstance().cancelAll();
	}

	@Override
	public void testPeriodic() {}

	@Override
	public void simulationInit() {
		logger.addInfo("robot", "Robot running in Simulation");
		System.out.println("*** Running in Simulation ***");
	}

	@Override
	public void simulationPeriodic() {}
}
