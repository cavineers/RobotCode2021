package frc.robot.lib.logging;

public class LogEntry {
    public Long timestamp;
    public String sender;
    public String message;
    public Object telemetry;
    public String type;
    public double FPGAtime;

    public LogEntry(Long ts, double FPGAtime, String sender, String message, String type) {
        this(ts, FPGAtime, sender, message, type, null);
    }

    public LogEntry(Long ts, double FPGAtime, String sender, String message, String type, Object telemetry) {
        this.timestamp = ts;
        this.sender = sender;
        this.message = message;
        this.telemetry = telemetry;
        this.type = type;
        this.FPGAtime = FPGAtime;
    }

    public void setTimestamp(Long ts) {
        this.timestamp = ts;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTelemetry(Object telemetry) {
        this.telemetry = telemetry;
    }
}
