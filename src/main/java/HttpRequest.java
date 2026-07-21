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

    public Optional<String> getDirectory() {
        return directory;
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
