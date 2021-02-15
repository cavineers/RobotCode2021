package frc.robot;
import frc.robot.subsystems.Intake;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI.Port;
import frc.robot.subsystems.SwerveDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
public class RobotContainer {
	private static RobotContainer m_instance;

	// Subsystems
	public Intake intake = new Intake(this);

	// Controller
	public Joystick joy = new Joystick(0);
    public JoystickButton a_button = new JoystickButton(joy, 1);
    public JoystickButton b_button = new JoystickButton(joy, 2);
    public JoystickButton x_button = new JoystickButton(joy, 3);
    public JoystickButton y_button = new JoystickButton(joy, 4);
    public JoystickButton l_bump = new JoystickButton(joy, 5);
    public JoystickButton r_bump = new JoystickButton(joy, 6);
    public JoystickButton left_menu = new JoystickButton(joy, 7);
    public JoystickButton right_menu = new JoystickButton(joy, 8);
    public JoystickButton left_stick = new JoystickButton(joy, 9);
    public JoystickButton right_stick = new JoystickButton(joy, 10);

	public SwerveDrive swerveDrive;

	public AHRS gyro;

	public RobotContainer() {
		Logger.getInstance().addInfo("RobotContainer", "Created RobotContainer instance");

		// Shared Sensors
		this.gyro = new AHRS(Port.kMXP);

		// Subsystems
		this.swerveDrive = new SwerveDrive(this.gyro);

		// Controller Bindings
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
