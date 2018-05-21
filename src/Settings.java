import java.util.List;

/**
 * 
 * 
 * @author
 * @version
 */
public class Settings
{
    public static void setFiles(String id)
    {
        //stores ID in settings file on the computer, backs up everything
    }
    
    public static void setFiles(String id, List files)
    {
        //backs up specific files passed 
    }
    
    public static List getFiles()
    {
        //gets all the file names from settings file and puts it in list, if none returns null
        return null;
    }
}