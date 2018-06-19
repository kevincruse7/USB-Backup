package usbbackup;

import java.io.File;
import java.util.List;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Maintains the settings folder
 *  
 * @ellyce
 * @5-30-18
 */
public class Settings
{
    /**
     * returns the directory where the backups should be stored
     * just the first line of the settings file 
     * @return the directory where the backups should be stored
     */
    public static File getDirectory() throws IOException, URISyntaxException
    {
        //reads the first line of the settings file
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(Settings.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().toString() + "/settings.txt"));
        String directory = bufferedReader.readLine();
        File fileDirectory = new File(directory);
        
        return fileDirectory;
    }
    
    /**
     * changes the directory to what is passed
     * @param the new directory
     */
    public static void setDirectory(File dir) throws IOException, URISyntaxException
    {
        //changes the first line in the settings file
        List<File> oldSettings = new ArrayList<>();
        oldSettings = getFiles(); //arraylist of settings folder
        
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(Settings.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().toString() + "/settings.txt"));
        
        //remove first thing in settings
        oldSettings.remove(0);
        //add in new directory
        oldSettings.add(0, dir);
        
        //rewrite the arraylist to settings
        for (File data: oldSettings)
        {
            bufferedWriter.append(data.toString());
            bufferedWriter.newLine();
        }
        
        bufferedWriter.close();
    }
    
    /**
     *
     * adds the sent files to the settings file
     * @param a list of file objects to add
     */
    public static void addFiles(List<File> files) throws IOException, URISyntaxException
    {
        //backs up specific file objects passed         
        //write to text file
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(Settings.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().toString() + "/settings.txt", true));
        
        //traverse list of file objects and write each one to the settings file
        for (File item : files)
        {
            bufferedWriter.append(item.toString());
            bufferedWriter.newLine();
        }
        
        bufferedWriter.close();
    }
    
    /**
     * removes all the files from settings that are passed
     * @param a list of file objects that should be removed
     */
    public static void removeFiles(List<File> files) throws IOException, URISyntaxException
    {
        //read in from settings file and put it into an arraylist of file objects
        List<File> oldSettings = new ArrayList<>();
        oldSettings = getFiles();
        
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(Settings.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().toString() + "/settings.txt"));
        
        
        //compare list to oldSettings looking for things to remove
        for (File item : oldSettings)
        {
            if (!files.contains(item))//if you don't want to remove it
            {
                //write back to settings
                bufferedWriter.append(item.toString());
                bufferedWriter.newLine();
            }
        }
        bufferedWriter.close();
    }
    
    
    /**
     * gets all the files from the settings file
     * @return a list of file objects containing the files from settings
     */
    public static List<File> getFiles() throws IOException, URISyntaxException
    {
        //gets all the file names from settings file and puts it in list, if none returns null
        //if file reader fails it will throw exception
        List<File> names = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(Settings.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().toString() + "/settings.txt"));
        String line = null;
        
        line = bufferedReader.readLine();
        while (line != null)
        {
            if ( !line.equals(""))
            {
                names.add(new File(line));//only returning non blank lines
            }
            line = bufferedReader.readLine();
        }
        
        bufferedReader.close();
        return names;
    }
}
