public class Router {
//    private final String path;
    private final HttpRequest request;
    public Router(HttpRequest request){
        this.request =  request;
    }
    public HttpResponse route(){
        HttpResponse response = new HttpResponse();
        if(request.getPath().equals("/")){
            response.setStatus("200 Ok");
        }
        if(request.getPath().startsWith("/echo/")){
            response.setStatus("200 Ok");
        }
        if(request.getPath().startsWith("/user-agent/")){
            response.setStatus("200 Ok");
            response.setHeaders("Content-Type","text/plain");
            response.setHeaders("Content-lenght",Integer.toString(request.getHeader("User-Agent").length()));
        }
        if(request.getPath().startsWith("/files/")){
            FileOperator opr = new FileOperator(request.getDirectory().toString());
            response.setStatus("200 Ok");
            byte[] content = opr.getFileContent().getBytes();
            response.setHeaders("Content-Type", "text/plain");
            response.setHeaders("Content-lenght", Integer.toString(content.length));
            response.setBody(content);
        }
        else{
            response.setStatus("404 Not Found");
        }
        return response;
    }

}
