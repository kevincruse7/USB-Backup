import java.io.File;
import java.util.List;

/**
 * 
 * 
 * @author
 * @version
 */
public class Settings
{
    public static void setDirectory(File dir)
    {
        //stores ID in settings file on the computer, backs up everything
    }
    
    public static File getDirectory()
    {
        return null;
    }
    
    public static void setFiles(List<File> files)
    {
        //backs up specific files passed 
    }
    
    public static List<File> getFiles()
    {
        //gets all the file names from settings file and puts it in list, if none returns null
        return null;
    }
}