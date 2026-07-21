import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
public class FileOperator {
    private final Path fullPath;
    public FileOperator(String dirPath, String fileName){
        this.fullPath = Paths.get(dirPath,fileName);
    }
    public boolean doesExist(){
        return Files.exists(fullPath);
    }
    public String getFileContent(){

        try {
            return Files.readString(fullPath);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
