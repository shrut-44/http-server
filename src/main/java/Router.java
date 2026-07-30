import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.GZIPOutputStream;

public class Router {
//    private final String path;
    static byte[] toGzip(String body){
        if (body == null || body.length() == 0) {
            return new byte[0];
        }
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GZIPOutputStream gos = new GZIPOutputStream(baos)){
            gos.write(body.getBytes(StandardCharsets.UTF_8));
            gos.finish();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private final HttpRequest request;
    public Router(HttpRequest request){
        this.request =  request;
    }
    public HttpResponse route(){
        HttpResponse response = new HttpResponse();
        Set<String> supportedEncodingTypes = Set.of("gzip");
        switch(request.getMethod()){
            case "GET" :
                if(request.getPath().equals("/")){
                    response.setStatus("200 OK");
                }
                else if (request.getPath().startsWith("/echo/")) {
                    String echoStr = request.getPath().substring("/echo/".length());
                    response.setStatus("200 OK");
                    response.setHeaders("Content-Type", "text/plain");
                    Optional<String> matchedEncoding = Optional.ofNullable(request.getHeader("Accept-Encoding"))
                            .stream()
                            .flatMap(header -> Arrays.stream(header.split(",")))
                            .map(String::trim)
                            .filter(supportedEncodingTypes::contains)
                            .findFirst();
                    if(supportedEncodingTypes.contains(matchedEncoding.orElse(""))){
                        response.setHeaders("Content-Encoding", "gzip");
                        response.setBody(toGzip(request.getPath().substring("/echo/".length())));
                    }
//                    response.setHeaders("Content-Length", Integer.toString(echoStr.length()));
//                    response.setBody(echoStr.getBytes(StandardCharsets.UTF_8));
                }
                else if(request.getPath().startsWith("/user-agent")){
                    response.setStatus("200 OK");
                    response.setHeaders("Content-Type","text/plain");
                    response.setHeaders("Content-Length",Integer.toString(request.getHeader("User-Agent").length()));
                    response.setBody(request.getHeader("User-Agent").getBytes(StandardCharsets.UTF_8));
                }
                else if(request.getPath().startsWith("/files")){
                    String fileName = request.getPath().substring("/files/".length());
                    String dirPath = request.getDirectory().orElse(".");
                    FileOperator opr = new FileOperator(dirPath, fileName);
                    if(opr.doesExist()){
                        response.setStatus("200 OK");
                        byte[] content = opr.getFileContent().getBytes();
                        response.setHeaders("Content-Type", "application/octet-stream");
                        response.setHeaders("Content-Length", Integer.toString(content.length));
                        response.setBody(content);
                    }
                    else{
                        response.setStatus("404 Not Found");
                    }
                }
                else{
                    response.setStatus("404 Not Found");
                }
                break;
            case "POST":
                if (request.getPath().startsWith("/files/")) {
                    String fileName = request.getPath().substring("/files/".length());
                    String dirPath = request.getDirectory().orElse(".");
                    FileOperator opr = new FileOperator(dirPath, fileName);
                    opr.writeToFile(new String(request.getBody(), StandardCharsets.UTF_8));
                    response.setStatus("201 Created");
                } else {
                    response.setStatus("404 Not Found");
                }
                break;
        }
        return response;
    }
}
