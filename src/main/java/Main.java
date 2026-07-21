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
          Optional<String> directory = Optional.empty();
          for (int i = 0; i < args.length - 1; i++) {
              if ("--directory".equals(args[i])) {
                  directory = Optional.of(args[i + 1]);
                  break;
              }
          }
          while(true){
              final Socket socket = httpServer.accept();
              Optional<String> finalDirectory = directory;
              Thread.startVirtualThread(() -> httpServer.handleClient(socket, finalDirectory));
          }

      } catch (IOException e) {
          throw new RuntimeException(e);
      }
  }
}
