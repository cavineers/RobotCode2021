package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.ToggleIntake;
import frc.robot.commands.ToggleReverseIntake;
import frc.robot.commands.auto.AutonomousExample;
import frc.robot.commands.auto.BarrelRacingAuto;
import frc.robot.commands.auto.BouncePathAuto;
import frc.robot.commands.auto.DeadAuto;
import frc.robot.commands.auto.GalacticSearch;
import frc.robot.commands.auto.SlalomPathAuto;
import frc.robot.commands.sim.ResetRobot;
import frc.robot.commands.sim.SimMenu;

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

    // Selected Auto Command
    public String m_selectedCommand = "BOUNCE";

    // Simulation Menu
    public boolean m_simMenu = false;

    // If the robot is field oriented
    public boolean m_fieldOriented = false;

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

        this.m_leftMenu.whenPressed(new SimMenu());

        // Toggle Intake
        System.out.println(this.m_xButton.get());
        
        this.m_xButton.whenPressed(new ToggleIntake());

        // ReverseIntake on bButton
        this.m_bButton.whenPressed(new ToggleReverseIntake());

        this.m_povDown.whenPressed(new InstantCommand() {
            @Override
            public void initialize() {
                if (Robot.robotContainer.m_simMenu) {
                    new ResetRobot(0, 0).schedule();
                }
                Robot.robotContainer.m_simMenu = false;
            }
        });

        this.m_povLeft.whenPressed(new InstantCommand() {
            @Override
            public void initialize() {
                if (Robot.robotContainer.m_simMenu) {
                    new ResetRobot(Units.inchesToMeters(30), Units.inchesToMeters(30)).schedule();
                }
                Robot.robotContainer.m_simMenu = false;
            }
        });

        this.m_povUp.whenPressed(new InstantCommand() {
            @Override
            public void initialize() {
                if (Robot.robotContainer.m_simMenu) {
                    new ResetRobot(Units.inchesToMeters(30), Units.inchesToMeters(90)).schedule();
                }
                Robot.robotContainer.m_simMenu = false;
            }
        });

        this.m_rightStick.whenPressed(new InstantCommand() {
            @Override
            public void initialize() {
                Robot.robotContainer.m_fieldOriented = !Robot.robotContainer.m_fieldOriented;
            }
        });
    }

    /**
     * Getter for the autonomous command selected in DANK.

     * @return The selected command.
     */
    public Command getAutonomousCommand() {
        switch (this.m_selectedCommand) {
            case "GALACTIC_SEARCH":
                return new GalacticSearch();
            case "SLALOM":
                return new SlalomPathAuto();
            case "BOUNCE":
                return new BouncePathAuto();
            case "BARREL_RACING":
                return new BarrelRacingAuto();
            case "TEST":
                return new AutonomousExample();
            default:
                return new DeadAuto();
        }
    }
}
