package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.lib.control.ControllerDriveInput;
import frc.robot.Limelight.LedMode;
import frc.robot.commands.DropIntake;
import frc.robot.commands.TeleopDrive;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.SwerveDrive.SwerveDriveState;
import frc.robot.subsystems.Transportation;
import java.net.UnknownHostException;

/**
 * Main Robot class that contains all Subsystems and periodic methods.
 */
public class Robot extends TimedRobot {
    // Logger
    public static Logger logger;

    // PDP
    public static PowerDistributionPanel PDP;

    // Dank
    public static Dank dank;

    // Shared Sensors
    public static AHRS gyro;
    public static Limelight limelight;

    // Subsystems
    public static Hood hood;
    public static Intake intake;
    public static Shooter shooter;
    public static SwerveDrive swerveDrive;
    public static Transportation transportation;

    // Vision
    public static Vision vision;

    // Robot Container
    public static RobotContainer robotContainer;

    // Autonomous command
    private Command m_autonomousCommand;

    // Match time
    private static double m_matchTime;

    /**
     * Robot constructor.
     * 
     * <p>Used to create all subsystems and start various services.
     */
    public Robot() {
        super(Constants.Robot.kPeriod);

        // Static logger
        logger = new Logger();

        // PDP
        PDP = new PowerDistributionPanel(Constants.CanIds.kPowerDistributionPanel);

        // Static DANK
        try {
            dank = new Dank();
            dank.start();
        } catch (UnknownHostException e) {
            logger.addInfo("DANK", "Error starting WS");
        }

        // Shared Sensors
        gyro = new AHRS(Port.kMXP);
        limelight = new Limelight();

        // Subsystems
        hood = new Hood();
        intake = new Intake();
        shooter = new Shooter();
        swerveDrive = new SwerveDrive();
        transportation = new Transportation();

        // Vision
        vision = new Vision();

        // Static robot container
        robotContainer = new RobotContainer();
    }

    @Override
    public void robotInit() {
        logger.addInfo("robot", "Initializing...");

        gyro.calibrate();

        transportation.disable();
    
        SmartDashboard.putNumber("shooter_constant", Constants.Shooter.kVelocityConstant);

        limelight.setLightMode(LedMode.OFF);
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();

        m_matchTime = this.m_ds.getMatchTime();

        dank.periodic();
    }

    @Override
    public void disabledInit() {
        logger.addInfo("robot", "Robot Disabled");

        swerveDrive.setState(SwerveDriveState.SWERVE);
        
        swerveDrive.swerve(new ControllerDriveInput(0.0, 0.0, 0.0));

        transportation.disable();
    }

    @Override
    public void disabledPeriodic() {}

    @Override
    public void autonomousInit() {
        logger.addInfo("robot", "Robot running in Autonomous");
        m_autonomousCommand = robotContainer.getAutonomousCommand();

        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }

        transportation.enable();
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {
        logger.addInfo("robot", "Robot running in Teleop");
        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
        }

        hood.enable();
        hood.home();

        transportation.enable();

        new DropIntake().schedule();

        new TeleopDrive().schedule(false);

        // Below code will turn on the shooter and conveyors on start up.'

        // shooter.enable();
        // shooter.setSpeed(5000);

        // transportation.setFeederMotorState(Transportation.TransportMotorState.ON);
        // transportation.setConveyorMotorState(Transportation.TransportMotorState.ON);

        // intake.setMotorState(Intake.IntakeMotorState.ON);
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void testInit() {
        logger.addInfo("robot", "Robot running in Test");

        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {
        swerveDrive.testPeriodic();
    }

    @Override
    public void simulationInit() {
        logger.addInfo("robot", "Robot running in Simulation");
        System.out.println("*** Running in Simulation ***");
    }

    @Override
    public void simulationPeriodic() {}

    public static double getMatchTime() {
        return m_matchTime;
    }
}
