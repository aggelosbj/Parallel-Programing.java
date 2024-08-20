import java.io.*;
import java.net.*;

public class ServerTCP {
   private static final int PORT = 1234;
   private static final String EXIT = "CLOSE";

   public static void main(String[] arg) throws IOException, ClassNotFoundException {
      ServerSocket socketConnection = new ServerSocket(PORT);
      System.out.println("Server is listening to port: " + PORT);

      Socket pipe = socketConnection.accept();
      ObjectInputStream serverInputStream = new ObjectInputStream(pipe.getInputStream());
      ObjectOutputStream serverOutputStream = new ObjectOutputStream(pipe.getOutputStream());

      ServerProtocol serverProt = new ServerProtocol();

      try {
          while (true) {
              DataRequest req = (DataRequest) serverInputStream.readObject();
              if (req.getOperation().equalsIgnoreCase(EXIT)) {
                  break;
              }
              DataReply rep = serverProt.processRequest(req);
              serverOutputStream.writeObject(rep);
          }
      } catch (Exception e) {
          e.printStackTrace();
      } finally {
          serverInputStream.close();
          serverOutputStream.close();
          pipe.close();
          socketConnection.close();
          System.out.println("Server socket closed");
      }
   }
}
