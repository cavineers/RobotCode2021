package frc.robot;

import com.google.gson.Gson;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.logging.ByteArray;
import frc.lib.logging.LogEntry;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;

/**
 * The Logger for the robot.
 */
public class Logger {
    // File Stream
    private FileOutputStream m_fout;
    private String m_filename;

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
        if (Robot.isReal()) {
            this.m_filename = Long.toString(Instant.now().getEpochSecond());

            SmartDashboard.putString("logs_filename", this.m_filename);

            File file = new File("/home/lvuser/logs/" + this.m_filename + ".log");
            try {
                file.createNewFile();
                this.m_fout = new FileOutputStream("/home/lvuser/logs/" + this.m_filename + ".log");
                this.m_fout.write("{\"logVersion\": 1}".getBytes());
            } catch (IOException e) {
                System.out.println("Unable to create or open logging file.");
                e.printStackTrace();
            }
        }
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
        if (Robot.isReal()) {
            try {
                this.m_fout.write(ByteArray.concatenateByteArrays(new Gson().toJson(entry).getBytes(), ",".getBytes()));
            } catch (IOException e) {
                System.out.println(e.getStackTrace());
            }
        }
        System.out.println(new Gson().toJson(entry));
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
