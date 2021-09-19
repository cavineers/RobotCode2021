package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.AutoShoot;
import frc.robot.commands.Flush;
import frc.robot.commands.ManualShoot;
import frc.robot.commands.TimedReverseIntake;
import frc.robot.commands.ToggleConveyor;
import frc.robot.commands.ToggleFeeder;
import frc.robot.commands.ToggleIntake;
import frc.robot.commands.auto.AutonomousExample;
import frc.robot.commands.auto.AutonomousExecute;
import frc.robot.commands.auto.AutonomousRecord;
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
   
    // Simulation Menu
    public boolean m_simMenu = false;

    public Command m_shootCommand;
    public Command m_manualShootCommand;

    // Autonomous Command Chooser
    private SendableChooser<Command> m_autoChooser = new SendableChooser<>();

    /**
     * Constructor for RobotContainer.
     */
    public RobotContainer() {
        Robot.logger.addInfo("RobotContainer", "Created RobotContainer");

        // Add Autonomous Options
        this.m_autoChooser.setDefaultOption("DEAD", new DeadAuto());
        this.m_autoChooser.addOption("AUTONOMOUS_EXAMPLE", new AutonomousExample());
        this.m_autoChooser.addOption("BARREL_RACING", new BarrelRacingAuto());
        this.m_autoChooser.addOption("BOUNCE_PATH", new BouncePathAuto());
        this.m_autoChooser.addOption("GALACTIC_SEARCH", new GalacticSearch());
        this.m_autoChooser.addOption("SLALOM_PATH", new SlalomPathAuto());
        this.m_autoChooser.addOption("RECORD", new AutonomousRecord());
        this.m_autoChooser.addOption("EXECUTE", new AutonomousExecute("1617056056.csv"));
        
        // Add SmartDashboard Autonomous Picker
        SmartDashboard.putData("Autonomous Command", this.m_autoChooser);

        // Shoot Command
        this.m_shootCommand = new AutoShoot();
        this.m_manualShootCommand = new ManualShoot();

        // Controller Bindings
        mapButtonBindings();
    }

    private void mapButtonBindings() {
        Robot.logger.addInfo("RobotContainer", "Start to map button bindings");

        this.m_leftMenu.whenPressed(new TimedReverseIntake(1.5));

        // Toggle Intake
        this.m_xButton.whenPressed(new ToggleIntake());

        // ToggleFeeder on bButton
        this.m_bButton.whenPressed(new ToggleFeeder());

        // Toggle Flush on rMenu
        this.m_rightMenu.whenPressed(new Flush());

        // Toggle Conveyor and Feeder on yButton
        this.m_yButton.whenPressed(new ToggleConveyor());

        // Shoot
        this.m_aButton.whenPressed(new InstantCommand() {
            @Override
            public void initialize() {
                if (Robot.robotContainer.m_manualShootCommand.isScheduled()) {
                    Robot.robotContainer.m_manualShootCommand.cancel();
                } else {
                    Robot.robotContainer.m_manualShootCommand.schedule();
                }
            }
        });

        // this.m_lBump.whenPressed(new InstantCommand() {
        //     @Override
        //     public void initialize() {
        //         if (Robot.robotContainer.m_shootCommand.isScheduled()) {
        //             Robot.robotContainer.m_shootCommand.cancel();
        //         } else {
        //             Robot.robotContainer.m_shootCommand.schedule();
        //         }
        //     }
        // });

        // Open Simulation Menu
        this.m_povRight.whenPressed(new SimMenu());

        this.m_povDown.whenPressed(new InstantCommand() {
            @Override
            public void initialize() {
                if (Robot.robotContainer.m_simMenu) {
                    new ResetRobot(0, 0, 0).schedule();
                }
                Robot.robotContainer.m_simMenu = false;
            }
        });

        this.m_povLeft.whenPressed(new InstantCommand() {
            @Override
            public void initialize() {
                if (Robot.robotContainer.m_simMenu) {
                    new ResetRobot(Units.inchesToMeters(30), Units.inchesToMeters(30), 0).schedule();
                }
                Robot.robotContainer.m_simMenu = false;
            }
        });

        this.m_povUp.whenPressed(new InstantCommand() {
            @Override
            public void initialize() {
                if (Robot.robotContainer.m_simMenu) {
                    new ResetRobot(Units.inchesToMeters(30), Units.inchesToMeters(90), 0).schedule();
                }
                Robot.robotContainer.m_simMenu = false;
            }
        });

        this.m_rightStick.whenPressed(new InstantCommand() {
            @Override
            public void initialize() {
                Robot.swerveDrive.setFieldOriented(!Robot.swerveDrive.isFieldOriented());
            }
        });

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
