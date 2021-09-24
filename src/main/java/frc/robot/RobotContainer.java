package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.Flush;
import frc.robot.commands.TimedReverseIntake;
import frc.robot.commands.ToggleConveyor;
import frc.robot.commands.ToggleFeeder;
import frc.robot.commands.ToggleIntake;
/**
 * RobotContainer stores all controls and autonomous routines.
 */
public class RobotContainer {
    // Controller
    public Joystick m_joy = new Joystick(0);
    public JoystickButton m_aButton = new JoystickButton(m_joy, 1);
    public JoystickButton m_bButton = new JoystickButton(m_joy, 2);
    public JoystickButton m_xButton = new JoystickButton(m_joy, 3);
    public JoystickButton m_yButton = new JoystickButton(m_joy, 4);
    public JoystickButton m_lBump = new JoystickButton(m_joy, 5);
    public JoystickButton m_rBump = new JoystickButton(m_joy, 6);
    public JoystickButton m_leftMenu = new JoystickButton(m_joy, 7);
    public JoystickButton m_rightMenu = new JoystickButton(m_joy, 8);
    public JoystickButton m_leftStick = new JoystickButton(m_joy, 9);
    public JoystickButton m_rightStick = new JoystickButton(m_joy, 10);
    public POVButton m_povUp = new POVButton(m_joy, 0, 0);
    public POVButton m_povRight = new POVButton(m_joy, 90, 0);
    public POVButton m_povDown = new POVButton(m_joy, 180, 0);
    public POVButton m_povLeft = new POVButton(m_joy, 270, 0);
   
    // Simulation Menu
    public boolean m_simMenu = false;

    public Command m_shootCommand;
    public Command m_lowShootCommand;

    // Autonomous Command Chooser
    private SendableChooser<Command> m_autoChooser = new SendableChooser<>();

    /**
     * Constructor for RobotContainer.
     */
    public RobotContainer() {
        Robot.logger.addInfo("RobotContainer", "Created RobotContainer");

        // Controller Bindings
        mapButtonBindings();
    }

    private void mapButtonBindings() {
        Robot.logger.addInfo("RobotContainer", "Start to map button bindings");

        // Toggle Intake
        this.m_xButton.whenPressed(new ToggleIntake());

        // ReverseIntake on bButton
        this.m_bButton.whenPressed(new TimedReverseIntake(1.5));

        // Toggle Flush on rMenu
        this.m_rightMenu.whenPressed(new Flush());

        // Shoot

        // Toggle Conveyor
        this.m_yButton.whenPressed(new ToggleConveyor());

        this.m_rBump.whenPressed(new ToggleFeeder());
    }

    /**
     * Getter for the autonomous command selected in DANK.

     * @return The selected command.
     */
    public Command getAutonomousCommand() {
        return this.m_autoChooser.getSelected();
    }
}
