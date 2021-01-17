package frc.robot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;

import com.google.gson.Gson;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.lib.logging.LogEntry;

public class Logger {
    // Instance
    private static Logger m_instance;

    // File Stream
    private FileOutputStream m_fout;
    private String m_filename;

    public enum LogType {
        INFO,
        WARN,
        ERR,
    }

    public Logger() {
        this.m_filename = Long.toString(Instant.now().getEpochSecond());

        File file = new File("/home/lvuser/logs/"+this.m_filename+".log");
        try {
            file.createNewFile();
            this.m_fout = new FileOutputStream("/home/lvuser/logs/"+this.m_filename+".log");
            this.m_fout.write("{\"logVersion\": 1}".getBytes());
        } catch (IOException e) {
            System.out.println("Unable to create or open logging file.");
            e.printStackTrace();
        }
    }

    public static Logger getInstance() {
        if (m_instance == null) {
            m_instance = new Logger();
        }

        return m_instance;
    }

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

    public void add(LogEntry entry) {
        try {
            this.m_fout.write(new Gson().toJson(entry).getBytes());
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public void addInfo(String sender, String message, Object caller) {
        this.add(new LogEntry(Instant.now().getEpochSecond(), Timer.getFPGATimestamp(), sender, message, this.typeToString(LogType.INFO), caller));
    }

    public void addWarn(String sender, String message, Object caller) {
        this.add(new LogEntry(Instant.now().getEpochSecond(), Timer.getFPGATimestamp(), sender, message, this.typeToString(LogType.WARN), caller));
    }

    public void addError(String sender, String message, Object caller) {
        this.add(new LogEntry(Instant.now().getEpochSecond(), Timer.getFPGATimestamp(), sender, message, this.typeToString(LogType.ERR), caller));
    }

    public void addInfo(String sender, String message) {
        this.add(new LogEntry(Instant.now().getEpochSecond(), Timer.getFPGATimestamp(), sender, message, this.typeToString(LogType.INFO), null));
    }

    public void addWarn(String sender, String message) {
        this.add(new LogEntry(Instant.now().getEpochSecond(), Timer.getFPGATimestamp(), sender, message, this.typeToString(LogType.WARN), null));
    }

    public void addError(String sender, String message) {
        this.add(new LogEntry(Instant.now().getEpochSecond(), Timer.getFPGATimestamp(), sender, message, this.typeToString(LogType.ERR), null));
    }
}
