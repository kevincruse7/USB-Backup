import java.util.*;
import java.io.*;
import java.nio.file.*;
/**
 * Class takes care of backing up the files with built in recovery
 * 
 * @author Ryan Leahy 
 * @version 0.1 Alpha build
 */
public class Backup
{
    private static List listOfFiles;
    private static File backupDirectory;
    private static boolean noError;
    
    public static boolean run()
    {
        initialize(); //initialize files to be backed up
        checkIfBackup(); //sees which files need to be backed up
        createDirs(); //creates the directories that are missing from the structure
        createAndModify(); //puts the files onto the hard drive and saves them with .BAk
        changeExtension(); //changes the extensions back to normal if the above executes without issue
        changeModified(); //changes the last modified time to match the files on the usb
        
        return noError;
    }
    
    /*
     * Method takes care of initializing static field elements in class
     */
    private static void initialize()
    {
        copyList(Settings.getFiles()); //gets the list of files from the usb
        backupDirectory = Settings.getDirectory(); //gets the directory of where to put all the backed up files
        noError = true;
    }
    
    /*
     * Method creates a local copy of the list of files to backup so this class can modify the files without affecting
     * the list in other classes
     */
    private static void copyList(List listFromUSB)
    {
        Iterator iter = listFromUSB.iterator();
        listOfFiles = new LinkedList();
        
        while (iter.hasNext())
        {
            listOfFiles.add(iter.next()); 
        }
    }
    
    /*
     * Method takes care of creating the folder hierarchy inside the destination folder to maintain the higherarchy that is in the usb
     */
    private static void createDirs()
    {
         String dir; //holds the directory to try and create
         String root; //holds the root to be removed from the path
         File current; //holds the file where the path is being extracted from
         Iterator iter = listOfFiles.iterator();
         Path filePath; 
         
         while (iter.hasNext())
         { 
            current = (File)iter.next();
            filePath = current.toPath(); //puts the full directory into the usb
            root = filePath.getRoot().toString(); //say the file is E:\User\text.txt it will hold E:
            dir = filePath.subpath(0, filePath.getNameCount() - 1).toString(); //gets up to file so say the file is E:\User\text.txt it gets E:\User
            dir = dir.substring(dir.indexOf(root) + root.length()); //removes root from the file path say you have E:\User at this point now you only have User
            dir = backupDirectory.getAbsolutePath() + dir; //say the path for the hard drive is C:\test\USB and now the files in the filePath is \User now you have C:\test\USB\User
            filePath = Paths.get(dir);
            try
            {
                Files.createDirectories(filePath);
            }
            catch (IOException e)
            {
                noError = false;
                System.out.println("Failed to create directories on target drive");
                System.exit(0); //for now crash the program, should probably change later
            }
         }
    }
    
    /*
     * Method goes through the given list of Files and compares them to files that already exist to see if they should be backed up
     * 
     * Currently the method of checking to see if it needs to be backed up is check if the last modified dates match
     * if they do the file is removed from the list
     */
    private static void checkIfBackup()
    {
        
    }
    
    /*
     * Method does the actual backing up of the files
     * 
     * the process of doing this has built in recovery, as the files are written they are saved as .BAK extensions
     * and if the method can fully execute without catching any exceptions the method finishes, if an exception occurs
     * all of the backups are deleted and the method throws an exception.
     */
    private static void createAndModify()
    {
        
    }
    
    /*
     * This method takes care of deleting all the .BAK from the files name so it can function as normal and 
     * then deletes the old files
     */
    private static void changeExtension()
    {
        
    }
    
    /*
     * Method changes all the last modified times to match the ones on the usb
     * if an exception is caught it will change all the times to match the beginning of the epoch so that all the files will be
     * re backed up next time it's plugged in
     */
    private static void changeModified()
    {
        
    }
}