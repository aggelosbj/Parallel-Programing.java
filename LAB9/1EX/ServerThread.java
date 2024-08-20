import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
    private Socket dataSocket;
    private ServerProtocol protocol;

    public ServerThread(Socket socket) {
        this.dataSocket = socket;
        this.protocol = new ServerProtocol();
    }

    public void run() {
        try {
            InputStream is = dataSocket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            OutputStream os = dataSocket.getOutputStream();
            PrintWriter out = new PrintWriter(os, true);

            String inmsg, outmsg;
            inmsg = in.readLine();
            outmsg = protocol.processRequest(inmsg);
            while (!outmsg.equals("CLOSE")) {
                out.println(outmsg);
                inmsg = in.readLine();
                outmsg = protocol.processRequest(inmsg);
            }

            dataSocket.close();
            System.out.println("Data socket closed for client: " + dataSocket.getInetAddress());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }
}
