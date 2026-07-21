import java.nio.charset.StandardCharsets;

public class Router {
//    private final String path;
    private final HttpRequest request;
    public Router(HttpRequest request){
        this.request =  request;
    }
    public HttpResponse route(){
        HttpResponse response = new HttpResponse();
        if(request.getPath().equals("/")){
            response.setStatus("200 OK");
        }
        else if (request.getPath().startsWith("/echo/")) {
            String echoStr = request.getPath().substring("/echo/".length());
            response.setStatus("200 OK");
            response.setHeaders("Content-Type", "text/plain");
            response.setHeaders("Content-Length", Integer.toString(echoStr.length()));
            response.setBody(echoStr.getBytes(StandardCharsets.UTF_8));
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
        return response;
    }

}
