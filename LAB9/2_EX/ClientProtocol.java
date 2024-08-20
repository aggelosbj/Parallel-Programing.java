import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientProtocol {

    BufferedReader user = new BufferedReader(new InputStreamReader(System.in));

    public String prepareRequest() throws IOException {
        System.out.println("Enter message to send to server (format: <operation>-<message>-<key>):");
        return user.readLine();
    }

    public void processReply(String theInput) {
        System.out.println("Message received from server: " + theInput);
    }
}
