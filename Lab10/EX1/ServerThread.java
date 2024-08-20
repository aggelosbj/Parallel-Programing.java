import java.net.Socket;
import java.io.IOException;

public class ServerThread extends Thread {
    private Connection connection;
    private Connections connections;
    private int clientId;

    public ServerThread(Connection connection, Connections connections, int clientId) {
        this.connection = connection;
        this.connections = connections;
        this.clientId = clientId;
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = connection.receive()) != null) {
                System.out.println("Client " + clientId + ": " + message);
                connections.broadcast(message, clientId);
            }
        } catch (Exception e) {
            System.err.println("Error in ServerThread: " + e.getMessage());
        } finally {
            connections.removeConnection(clientId);
            connection.closeSocket();
        }
    }
}
