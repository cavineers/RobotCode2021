package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.auto.AutonomousExample;
import frc.robot.commands.auto.DeadAuto;
import frc.robot.commands.auto.GalacticSearch;
import frc.robot.commands.auto.SlalomPathAuto;
import frc.robot.commands.sim.ResetRobot;
import frc.robot.commands.sim.SimMenu;
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
	public POVButton pov_up = new POVButton(joy, 0, 0);
	public POVButton pov_right = new POVButton(joy, 90, 0);
	public POVButton pov_down = new POVButton(joy, 180, 0);
	public POVButton pov_left = new POVButton(joy, 270, 0);
	public String selectedCommand = "TEST";

	public boolean simMenu = false;
	public boolean fieldOriented = false;

	public RobotContainer() {
		Robot.logger.addInfo("RobotContainer", "Created RobotContainer");

		// Controller Bindings
		mapButtonBindings();
	}

	private void mapButtonBindings() {
		Robot.logger.addInfo("RobotContainer", "Start to map button bindings");

		left_menu.whenPressed(new SimMenu());

		this.pov_down.whenPressed(new InstantCommand() {
			@Override
			public void initialize() {
				if (Robot.robotContainer.simMenu) {
					new ResetRobot(0,0).schedule();
				}
				Robot.robotContainer.simMenu = false;
			}
		});

		this.pov_left.whenPressed(new InstantCommand() {
			@Override
			public void initialize() {
				if (Robot.robotContainer.simMenu) {
					new ResetRobot(Units.inchesToMeters(30), Units.inchesToMeters(30)).schedule();
				}
				Robot.robotContainer.simMenu = false;
			}
		});

		this.right_stick.whenPressed(new InstantCommand() {
			@Override
			public void initialize() {
				Robot.robotContainer.fieldOriented = !Robot.robotContainer.fieldOriented;
			}
		});
	}

	public Command getAutonomousCommand() {
		switch (this.selectedCommand) {
			case "GALACTIC_SEARCH":
				return new GalacticSearch();
			case "SLALOM":
				return new SlalomPathAuto();				
			case "TEST":
				return new AutonomousExample();
			default:
				return new DeadAuto();				
		}
	}
}
