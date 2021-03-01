package frc.lib.logging;

/**
 * Log entry.
 */
public class LogEntry {
    public Long m_timestamp;
    public String m_sender;
    public String m_message;
    public Object m_telemetry;
    public String m_type;
    public double m_fpgatime;

    public LogEntry(Long ts, double fpgatime, String sender, String message, String type) {
        this(ts, fpgatime, sender, message, type, null);
    }

    /**
     * Create log entry.

     * @param ts Unix Timestamp
     * @param fpgatime FPGA Timestamp
     * @param sender Entry Sender
     * @param message Message
     * @param type Type of message
     * @param telemetry Telemetry (class of sender)
     */
    public LogEntry(Long ts, double fpgatime, String sender, String message, String type, Object telemetry) {
        this.m_timestamp = ts;
        this.m_sender = sender;
        this.m_message = message;
        this.m_telemetry = telemetry;
        this.m_type = type;
        this.m_fpgatime = fpgatime;
    }

    public void setTimestamp(Long ts) {
        this.m_timestamp = ts;
    }

    public void setSender(String sender) {
        this.m_sender = sender;
    }

    public void setMessage(String message) {
        this.m_message = message;
    }

    public void setTelemetry(Object telemetry) {
        this.m_telemetry = telemetry;
    }
}
