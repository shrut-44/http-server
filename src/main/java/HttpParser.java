import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpParser {
    private final InputStream inputStream;
    private HttpRequest httpRequest;
    public HttpParser(InputStream inputStream){
        this.inputStream = inputStream;
    }
    public HttpRequest parse() throws IOException {
        httpRequest = new HttpRequest();
        String requestHeader = readHeaders(inputStream);
        String[] lines = requestHeader.split("\r\n");
        httpRequest.setRequestLine(lines[0]);
        httpRequest.setMethod(lines[0].split(" ")[0]);
        httpRequest.setPath(lines[0].split(" ")[1]);
        Map<String, String> requestHeaders = parseHeaders(lines);
        httpRequest.setRequestHeaders(requestHeaders);
        int contentLength = 0;
        if (requestHeaders.containsKey("content-length")) {
            contentLength = Integer.parseInt(
                    requestHeaders.get("content-length")
            );
        }
        httpRequest.setBody(inputStream.readNBytes(contentLength));
        return httpRequest;
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
}
