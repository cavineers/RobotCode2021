package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Deadzone;
import frc.robot.Robot;
import frc.robot.subsystems.SwerveDrive.SwerveDriveState;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;

/**
 * Record autonomous.
 */
public class AutonomousRecord extends CommandBase {
    private String m_filename = "";
    private FileOutputStream m_fout;

    public AutonomousRecord() {}

    @Override
    public void initialize() {
        Robot.swerveDrive.setState(SwerveDriveState.OTHER_AUTO);

        if (Robot.isReal()) {
            this.m_filename = Long.toString(Instant.now().getEpochSecond());

            File file = new File("/home/lvuser/paths_out/" + this.m_filename + ".csv");
            
            try {
                file.createNewFile();
                this.m_fout = new FileOutputStream("/home/lvuser/paths_out/" + this.m_filename + ".csv");
            } catch (IOException e) {
                System.out.println("Unable to create or open path file.");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void execute() {
        // Read values
        double fwd = Deadzone.apply(Robot.robotContainer.m_joy.getRawAxis(1) / 1, 0.1);
        double strafe = Deadzone.apply(Robot.robotContainer.m_joy.getRawAxis(0) / 1, 0.1);
        double rotate = -Deadzone.apply(Robot.robotContainer.m_joy.getRawAxis(4) / 1, 0.1);

        // Print to file
        try {
            this.m_fout.write((fwd + "," + strafe + "," + rotate + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Actually swerve
        Robot.swerveDrive.heldSwerve(fwd, strafe, rotate, false);
    }

    @Override
    public void end(boolean interrupted) {
        try {
            this.m_fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
