import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

public class Main {

  public static void main(String[] args) {
      try {
          HttpServer httpServer = new HttpServer(4221);
          Optional<String> directory = args[1].describeConstable();
          while(true){
              final Socket socket = httpServer.accept();
              Thread.startVirtualThread(() -> httpServer.handleClient(socket, directory));
          }

      } catch (IOException e) {
          throw new RuntimeException(e);
      }
  }
}
