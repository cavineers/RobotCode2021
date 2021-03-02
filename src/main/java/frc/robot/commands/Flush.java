package frc.robot.commands;

import java.awt.event.*;
import javax.swing.Timer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class Flush extends CommandBase {

    Timer timer;

    // Constructor
    public Flush() {

    }

    // Set Motor State to OFF / REVERSED
    @Override
    public void initialize() {
        Robot.logger.addInfo("Flush", "Flush Systems Starting");

        // Reverse the Intake systems
        new ToggleReverseIntake();

        // Stop Systems After 1 Second
        this.timer = new Timer(Constants.Flush.kFlushTimer, taskPerformer);
        this.timer.setRepeats(false);
        this.timer.start();
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }

    // Method to be run after given time
    ActionListener taskPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            new ToggleReverseIntake();
            timer.stop();
        }
    };
}
