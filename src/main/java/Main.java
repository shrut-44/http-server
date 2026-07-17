import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    public static void handleClient(Socket socket){
        try{
            HttpRequest httpRequest = new HttpRequest(socket);
            String requestLine = httpRequest.getRequestLine();
            String path = httpRequest.getPath();
            byte[] body = httpRequest.getBody();
            String userAgent = httpRequest.getHeader("User-Agent");
            String status;
            HttpResponse httpResponse = new HttpResponse(socket);
            if (!path.equals("/user-agent")) {
                httpResponse.buildResponse("404 Not Found");
            } else {
                httpResponse.buildResponse(
                        "200 OK",
                        Integer.toString(userAgent.getBytes(StandardCharsets.UTF_8).length),
                        userAgent
                );
            }
            httpResponse.write();
            socket.close();
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }
  public static void main(String[] args) {
      try {
          HttpServer httpServer = new HttpServer(4221);
          while(true){
              final Socket socket = httpServer.accept();
              Thread.startVirtualThread(() -> handleClient(socket));
          }

      } catch (IOException e) {
          throw new RuntimeException(e);
      }
  }
}
