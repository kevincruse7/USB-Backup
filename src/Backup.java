import java.util.*;
/**
 * 
 * 
 * @author Ryan Leahy 
 * @version 0.1 Alpha build
 */
public class Backup
{
    private static List listOfFiles;
    
    public static void backup()
    {
        initialize(); //initialize files to be backed up
    }
    
    /*
     * Method takes care of initializing static field elements in class
     */
    private static void initialize()
    {
        listOfFiles = Settings.getFiles();
    }
}