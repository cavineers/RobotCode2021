package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.lib.logging.LogEntry;
import java.time.Instant;

/**
 * The Logger for the robot.
 */
public class Logger {

    /**
     * Type of the log message.
     */
    public enum LogType {
        INFO, WARN, ERR,
    }

    /**
     * Logger constructor.
     */
    public Logger() {
    }

    /**
     * Converts log type to string.

     * @param type LogType enum to convert
     * @return The converted string
     */
    public String typeToString(LogType type) {
        switch (type) {
            case INFO:
                return "INFO";
            case WARN:
                return "WARNING";
            case ERR:
                return "ERROR";
            default:
                return "OTHER";
        }
    }

    /**
     * Add entry to logs.

     * @param entry entry
     */
    public void add(LogEntry entry) {
    }

    /**
     * Add an info message to the logs.

     * @param sender Sender of the log message
     * @param message Message for the log
     * @param caller Object calling the log
     */
    public void addInfo(String sender, String message, Object caller) {
        this.add(new LogEntry(Instant.now().getEpochSecond(), Timer.getFPGATimestamp(), sender, message,
                this.typeToString(LogType.INFO), caller));
    }

    /**
     * Add an info message to the logs.

     * @param sender Sender of the log message
     * @param message Message for the log
     */
    public void addInfo(String sender, String message) {
        this.add(new LogEntry(Instant.now().getEpochSecond(), Timer.getFPGATimestamp(), sender, message,
                this.typeToString(LogType.INFO), null));
    }

    /**
     * Add a warn message to the logs.

     * @param sender Sender of the log message
     * @param message Message for the log
     * @param caller Object calling the log
     */
    public void addWarn(String sender, String message, Object caller) {
        this.add(new LogEntry(Instant.now().getEpochSecond(), Timer.getFPGATimestamp(), sender, message,
                this.typeToString(LogType.WARN), caller));
    }

    /**
     * Add a warn message to the logs.

     * @param sender Sender of the log message
     * @param message Message for the log
     */
    public void addWarn(String sender, String message) {
        this.add(new LogEntry(Instant.now().getEpochSecond(), Timer.getFPGATimestamp(), sender, message,
                this.typeToString(LogType.WARN), null));
    }

    /**
     * Add an error message to the logs.

     * @param sender Sender of the log message
     * @param message Message for the log
     * @param caller Object calling the log
     */
    public void addError(String sender, String message, Object caller) {
        this.add(new LogEntry(Instant.now().getEpochSecond(), Timer.getFPGATimestamp(), sender, message,
                this.typeToString(LogType.ERR), caller));
    }

    /**
     * Add an error message to the logs.

     * @param sender Sender of the log message
     * @param message Message for the log
     */
    public void addError(String sender, String message) {
        this.add(new LogEntry(Instant.now().getEpochSecond(), Timer.getFPGATimestamp(), sender, message,
                this.typeToString(LogType.ERR), null));
    }
}
