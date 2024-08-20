import java.io.*;
import java.net.*;

public class ClientTCP { 
   private static final String HOST = "localhost";
   private static final int PORT = 1234;

   public static void main(String[] arg) throws IOException, ClassNotFoundException {
      Socket socketConnection = new Socket(HOST, PORT);

      ObjectOutputStream clientOutputStream = new ObjectOutputStream(socketConnection.getOutputStream());
      ObjectInputStream clientInputStream = new ObjectInputStream(socketConnection.getInputStream());

      BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
      String operation = "";
      
      while (!operation.equalsIgnoreCase("CLOSE")) {
          System.out.println("Enter operation: ");
          operation = user.readLine();
          if (operation.equalsIgnoreCase("CLOSE")) {
              break;
          }

          System.out.println("Enter message: ");
          String message = user.readLine();
          System.out.println("Enter key: ");
          int key = Integer.parseInt(user.readLine());

          DataRequest req = new DataRequest(operation, message, key);
          clientOutputStream.writeObject(req);

          DataReply rep = (DataReply) clientInputStream.readObject();
          System.out.println("Message received from server: " + rep.getMessage());
      }

      clientOutputStream.close();
      clientInputStream.close();
      socketConnection.close();
      System.out.println("Data Socket closed");
   }
}
