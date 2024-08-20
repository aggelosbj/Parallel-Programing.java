import java.io.*;
import java.net.*;

public class MultiThreadServer {
    private static final int PORT = 1234;

    public static void main(String args[]) throws IOException {
        ServerSocket connectionSocket = new ServerSocket(PORT);

        SharedData sharedData = new SharedData();

        while (true) {
            System.out.println("Server is listening to port: " + PORT);
            Socket dataSocket = connectionSocket.accept();
            System.out.println("Received request from " + dataSocket.getInetAddress());

            ServerThread sthread = new ServerThread(dataSocket, sharedData);
            sthread.start();
        }
    }
}
