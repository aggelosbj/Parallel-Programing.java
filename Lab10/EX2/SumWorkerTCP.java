import java.net.*;
import java.io.*;

public class SumWorkerTCP {
    private static final String HOST = "localhost";
    private static final int PORT = 1234;

    public static void main(String args[]) throws IOException {
        Socket dataSocket = new Socket(HOST, PORT);

        InputStream is = dataSocket.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        OutputStream os = dataSocket.getOutputStream();
        PrintWriter out = new PrintWriter(os, true);

        String inmsg = in.readLine();
        WorkerProtocol app = new WorkerProtocol();
        String outmsg = app.compute(inmsg);
        out.println(outmsg);

        dataSocket.close();
    }
}
