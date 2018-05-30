import java.io.File;
import java.util.List;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * 
 * 
 * @ellyce
 * @5-30-18
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
    
    public static void setFiles(List<File> files) throws IOException
    {
        //backs up specific file objects passed         
        FileWriter writer = new FileWriter("settings.txt");//write to text file
        
        //traverse list of file objects and write each one to the settings file
        for (File item: files)
        {
            writer.write(item.toString());
        }
        writer.close();
    }
    
    public static List<File> getFiles() throws IOException
    {
        //gets all the file names from settings file and puts it in list, if none returns null
        FileReader reader = new FileReader("settings.txt");//if file reader fails it will throw exception
        List<String> names;
        
        if (reader.read() == -1)
        {
            return null;
        }
        else
        {
            
        }
        
        
        return null;
    }
}