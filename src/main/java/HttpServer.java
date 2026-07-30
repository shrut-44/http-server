import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class HttpServer {
    private final ServerSocket serverSocket;
    public HttpServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.serverSocket.setReuseAddress(true);
    }
//    public HttpServer(int port, String directory) throws IOException {
//        this.serverSocket = new ServerSocket(port);
//        this.serverSocket.setReuseAddress(true);
//    }
    public Socket accept() throws IOException {
        return serverSocket.accept();
    }
//    public void close() throws IOException {
//        serverSocket.close();
//    }
    public void handleClient(Socket socket, Optional<String> directory){
        try(socket){
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            while(true){
                if(inputStream.available()==0 && socket.isClosed()){
                    break;
                }
                HttpRequest httpRequest = new HttpParser(inputStream, directory).parse();
                HttpResponse httpResponse = new Router(httpRequest).route();
                outputStream.write(httpResponse.toBytes());
                outputStream.flush();
                String connectionHeader = httpRequest.getHeader("Connection");
                if ("close".equalsIgnoreCase(connectionHeader)) {
                    break;
                }
            }

        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }
}
