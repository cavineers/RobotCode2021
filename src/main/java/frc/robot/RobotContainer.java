package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
public class RobotContainer {
	// Controller
	public static Joystick joy;
    public static JoystickButton a_button;
    public static JoystickButton b_button;
    public static JoystickButton x_button;
    public static JoystickButton y_button;
    public static JoystickButton l_bump;
    public static JoystickButton r_bump;
    public static JoystickButton left_menu;
    public static JoystickButton right_menu;
    public static JoystickButton left_stick;
    public static JoystickButton right_stick;

	public RobotContainer() {
		Logger.getInstance().addInfo("RobotContainer", "Created RobotContainer");

		// Setup Controller
		joy = new Joystick(0);
		a_button = new JoystickButton(joy, 1);
		b_button = new JoystickButton(joy, 2);
		x_button = new JoystickButton(joy, 3);
		y_button = new JoystickButton(joy, 4);
		l_bump = new JoystickButton(joy, 5);
		r_bump = new JoystickButton(joy, 6);
		left_menu = new JoystickButton(joy, 7);
		right_menu = new JoystickButton(joy, 8);
		left_stick = new JoystickButton(joy, 9);
		right_stick = new JoystickButton(joy, 10);

		// Controller Bindings
		mapButtonBindings();
	}

	private void mapButtonBindings() {
		Logger.getInstance().addInfo("RobotContainer", "Start to map button bindings");

	}

	// public Command getAutonomousCommand() {
	// 	return m_autoCommand;
	// }
}
