import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpRequest {
    private String requestLine;
    private Map<String,String> requestHeaders;
    private byte[] body;
    private String method;
    private String path;
    private Optional<String> directory;
    public HttpRequest(){

    }

    private String readHeaders(InputStream input) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int b;
        int[] lastFour = new int[4];
        int index = 0;
        while ((b = input.read()) != -1) {
            buffer.write(b);
            lastFour[index % 4] = b;
            index++;
            if (index >= 4 &&
                    lastFour[(index - 4) % 4] == '\r' &&
                    lastFour[(index - 3) % 4] == '\n' &&
                    lastFour[(index - 2) % 4] == '\r' &&
                    lastFour[(index - 1) % 4] == '\n') {
                break;
            }
        }
        return buffer.toString(java.nio.charset.StandardCharsets.ISO_8859_1);
    }

    private Map<String, String> parseHeaders(String[] lines) {
        Map<String, String> headers = new HashMap<>();
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split(":", 2);
            if (parts.length == 2) {
                String name = parts[0].trim();
                String value = parts[1].trim();
                headers.put(name.toLowerCase(), value);
            }
        }
        return headers;
    }

    public String getFileContent(){
        String filePath = "" + path.split("/")[2];
        return filePath;
    }

    public String getRequestLine() {
        return requestLine;
    }

    public String getMethod(){
        return this.method;
    }

    public String getPath(){
        return this.path;
    }

    public String getHeader(String name) {
        return requestHeaders.get(name.toLowerCase());
    }

    public byte[] getBody() {
        return body;
    }

    public void setDirectory(Optional<String> directory) {
        this.directory = directory;
    }

    public void setRequestLine(String requestLine) {
        this.requestLine = requestLine;
    }

    public void setRequestHeaders(Map<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
