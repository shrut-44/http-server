import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
public class FileOperator {
    private final String pathStr;
    public FileOperator(String pathStr){
        this.pathStr = pathStr;
    }
    public String getFileContent(){
        Path path = Paths.get(pathStr);
        try {
            String content = Files.readString(path);
            return content;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
