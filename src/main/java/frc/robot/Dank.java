package frc.robot;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

/**
 * Dashboard and Navigation Kit.
 */
public class Dank extends WebSocketServer {
    private WebSocket m_visionAddress;
    
    public Dank() throws UnknownHostException {
        super(new InetSocketAddress(Constants.Dank.kPORT));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        Robot.logger.addInfo("DANK", "WS Connected");

        conn.send("05;" + Constants.Robot.kYear);
        conn.send("06;" + Constants.Robot.kName);

        conn.send("02;" + "{\"version\": \"v3\", \"layout\": [{\"type\": \"StaticText\",\"x\": \"30px\",\"y\": \"-10px\",\"z\": \"2\",\"text\": \"Match: #\",\"textColor\": \"whitesmoke\",\"fontSize\": \"64px\"}]}");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        Robot.logger.addInfo("DANK", "WS Disconnect");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        Robot.logger.addInfo("DANK", "WS " + message);
        this.handle(conn, message);
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        Robot.logger.addInfo("DANK", "WS " + message);
        this.handle(conn, message.toString());
    }

    private void handle(WebSocket conn, String message) {
        String type = message.substring(0, 2);
        String content = message.substring(3);

        switch (type) {
            case "00":
                conn.send("01;pong");
                break;
            case "01":
                conn.send("01;ping");
                break;
            case "03":
                Robot.logger.addInfo("DANK-WS-03", content);
                break;
            case "04":
                Robot.vision.ingest(content);
                break;
            case "08":
                this.m_visionAddress = conn;
                Robot.logger.addInfo("DANK", "Vision Connected");
                this.broadcast("03;Vision Connected");
                break;
            default:
                Robot.logger.addInfo("DANK-WS", "Unknown type: " + type);
                break;
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
        if (conn != null) {
            Robot.logger.addInfo("DANK", "Error creating connection");
        } else {
            Robot.logger.addInfo("DANK", "Error occurred");
        }
    }

    @Override
    public void onStart() {
        Robot.logger.addInfo("DANK", "WS Server started");
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
    }

    /**
     * DankDash Periodic.
     */
    public void periodic() {
        if (this.m_visionAddress != null) {
            if (this.m_visionAddress.isClosed()) {
                this.broadcast("03;Vision Disconnected");
                Robot.logger.addInfo("DANK", "Vision Disconnected");
                this.m_visionAddress = null;
            }
        }
    }
}
