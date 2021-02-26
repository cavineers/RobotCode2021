package frc.robot.commands.sim;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Robot;

public class SimMenu extends InstantCommand {
    public SimMenu() {}

    @Override
    public void initialize() {
        if (Robot.isSimulation()) {
            Robot.robotContainer.simMenu = !Robot.robotContainer.simMenu;
        }
    }
}
