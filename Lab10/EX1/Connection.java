import java.io.*;
import java.net.Socket;

public class Connection {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public Connection(Socket socket) {
        this.socket = socket;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
        }
    }

    public String receive() {
        try {
            return in.readLine();
        } catch (IOException e) {
            System.err.println("Error reading message: " + e.getMessage());
            return null;
        }
    }

    public synchronized void send(String message) {
        out.println(message);
    }

    public void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error closing socket: " + e.getMessage());
        }
    }
}