package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.DropIntake;
import frc.robot.commands.TeleopDrive;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.SwerveDrive.SwerveDriveState;
import frc.robot.subsystems.Transportation.TransportMotorState;
import frc.robot.subsystems.Transportation;
import java.net.UnknownHostException;
import frc.lib.Deadzone;

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

    private static double m_matchTime;

    /**
     * Robot constructor.
     * 
     * <p>Used to create all subsystems and start various services.
     */
    public Robot() {
        super(0.02);

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
        
        swerveDrive.swerve(0, 0, 0, false);

        transportation.disable();
    }

    @Override
    public void disabledPeriodic() {
    }

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
    public void autonomousPeriodic() {
        // swerveDrive.swerve(0.2, -0.2, -0.0, false);
    }

    @Override
    public void teleopInit() {
        logger.addInfo("robot", "Robot running in Teleop");
        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
        }

        gyro.reset();

        hood.enable();
        hood.home();

        transportation.enable();

        new DropIntake().schedule();

        new TeleopDrive().schedule(false);
    }

    @Override
    public void teleopPeriodic() {
        // if (!hood.isHoming()) {
        //     hood.turnToAngle(90.0);
        // }
        // hood.disable();

        // hood.m_hoodMotor.set(ControlMode.PercentOutput, -0.5);
        // transportation.setConveyorMotorState(TransportMotorState.ON);
        // transportation.setFeederMotorState(TransportMotorState.ON);

        // shooter.setSpeed(4000);
        // shooter.enable();
        // if (Deadzone.apply(robotContainer.m_rBump.get() ? speed : 0.0, 0.1) != 0.0) {
        //     hood.m_hoodMotor.set(Deadzone.apply(robotContainer.m_rBump.get() ? speed : 0.0, 0.1));
        // } else {
        //     hood.m_hoodMotor.set(-Deadzone.apply(robotContainer.m_lBump.get() ? speed : 0.0, 0.1));
        // }
    }

    @Override
    public void testInit() {
        logger.addInfo("robot", "Robot running in Test");

        System.out.println(Rotation2d.fromDegrees(190).getDegrees());

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
    public void simulationPeriodic() {
    }

    public static double getMatchTime() {
        return m_matchTime;
    }
}
