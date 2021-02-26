package frc.robot.commands.sim;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Robot;

public class SimMenu extends InstantCommand {
    public SimMenu() {}

    @Override
    public void initialize() {
        Robot.robotContainer.simMenu = true;
    }
}
