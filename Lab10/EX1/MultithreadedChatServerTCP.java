import java.net.*;
import java.io.*;

public class MultithreadedChatServerTCP {
    private static final int PORT = 1234;

    public static void main(String[] args) throws IOException {
        ServerSocket connectionSocket = new ServerSocket(PORT);
        Connections connections = new Connections();
        int clientId = 0;

        while (true) {
            System.out.println("Server is waiting for clients on port: " + PORT);
            Socket dataSocket = connectionSocket.accept();
            System.out.println("Received request from " + dataSocket.getInetAddress());

            Connection connection = new Connection(dataSocket);
            connections.addConnection(clientId, connection);

            ServerThread serverThread = new ServerThread(connection, connections, clientId);
            serverThread.start();
            clientId++;
        }
    }
}
