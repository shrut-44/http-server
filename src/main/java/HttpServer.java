import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServer {
    private final ServerSocket serverSocket;
    public HttpServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.serverSocket.setReuseAddress(true);
    }
    public Socket accept() throws IOException {
        return serverSocket.accept();
    }
    public void close() throws IOException {
        serverSocket.close();
    }
//    public void handleClient(Socket socket, String givenPath){
//        try{
//            HttpRequest httpRequest = new HttpRequest(socket);
//            String requestLine = httpRequest.getRequestLine();
//            String path = httpRequest.getPath();
//            byte[] body = httpRequest.getBody();
////            String userAgent = httpRequest.getHeader(header);
//            String fileData = httpRequest.getFileContent();
//            HttpResponse httpResponse = new HttpResponse(socket);
//            if (!path.equals(givenPath)) {
//                httpResponse.buildResponse("404 Not Found");
//            } else {
//                httpResponse.buildResponse(
//                        "200 OK",
//                        Integer.toString(userAgent.getBytes(StandardCharsets.UTF_8).length),
//                        userAgent
//                );
//            }
//            httpResponse.write();
//            socket.close();
//        }catch (IOException e){
//            throw new RuntimeException(e);
//        }
//
//    }
}
