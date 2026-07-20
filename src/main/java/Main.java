import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {

  public static void main(String[] args) {
      try {
          HttpServer httpServer = new HttpServer(4221);
          String path = "/files/temp.txt";
          while(true){
              final Socket socket = httpServer.accept();
              Thread.startVirtualThread(() -> httpServer.handleClient(socket, path));
          }

      } catch (IOException e) {
          throw new RuntimeException(e);
      }
  }
}
