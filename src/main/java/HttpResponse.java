import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private byte[] body;
    private String status;
    private Map<String,String> headers;

    public HttpResponse(){
        this.headers = new HashMap<>();
    }
    public byte[] toBytes() {
        StringBuilder builder = new StringBuilder();
        builder.append("HTTP/1.1 ")
                .append(status)
                .append("\r\n");
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            builder.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append("\r\n");
        }
        builder.append("\r\n");
        byte[] headerBytes = builder.toString().getBytes(StandardCharsets.UTF_8);
        if (body == null) {
            return headerBytes;
        }
        byte[] response = new byte[headerBytes.length + body.length];
        System.arraycopy(headerBytes, 0, response, 0, headerBytes.length);
        System.arraycopy(body, 0, response, headerBytes.length, body.length);
        return response;
    }
    public void setBody(byte[] body){
        this.body = body.clone();
    }
    public void setHeaders(String key, String value){
        this.headers.put(key,value);
    }
    public void setStatus(String status){
        this.status = status;
    }
}
