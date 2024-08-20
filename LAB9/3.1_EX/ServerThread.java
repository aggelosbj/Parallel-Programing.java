import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
    private Socket dataSocket;
    private InputStream is;
    private BufferedReader in;
    private OutputStream os;
    private PrintWriter out;
    
    private static final String EXIT = "CLOSE";

    public ServerThread(Socket socket) {
        this.dataSocket = socket;
        try {
            this.is = dataSocket.getInputStream();
            this.in = new BufferedReader(new InputStreamReader(is));
            this.os = dataSocket.getOutputStream();
            this.out = new PrintWriter(os, true);
        } catch (IOException e) {
            System.out.println("I/O Error " + e);
        }
    }

    public void run() {
        try {
            String inmsg, outmsg;
            ServerProtocol protocol = new ServerProtocol(); 
            while (true) {
                inmsg = in.readLine();
                if (inmsg.equals("CLOSE")) {
                    break;
                }
                outmsg = protocol.processRequest(inmsg);
                out.println(outmsg);
            }
            dataSocket.close();
        } catch (IOException e) {
            System.out.println("I/O Error " + e);
        }
    }
}
