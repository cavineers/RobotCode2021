package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.auto.DeadAuto;
public class RobotContainer {
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

	public String selectedCommand = "";

	public RobotContainer() {
		Robot.logger.addInfo("RobotContainer", "Created RobotContainer");

		// Controller Bindings
		mapButtonBindings();
	}

	private void mapButtonBindings() {
		Robot.logger.addInfo("RobotContainer", "Start to map button bindings");

	}

	public Command getAutonomousCommand() {
		switch (this.selectedCommand) {
			case "GALACTIC_SEARCH":
				return new DeadAuto();
			default:
				return new DeadAuto();
		}
	}
}
