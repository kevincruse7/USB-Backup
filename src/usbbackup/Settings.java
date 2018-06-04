package usbbackup;

import java.io.File;
import java.util.List;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;

/**
 * 
 * 
 * @ellyce
 * @5-30-18
 */
public class Settings
{
    /**
     * 
     */
    public static File getDirectory()
    {
        return null;
    }
    
    /**
     * 
     */
    public static void setDirectory(File dir)
    {
        
    }
    
    /**
     * 
     */
    public static void addFiles(List<File> files) throws IOException
    {
        //backs up specific file objects passed         
        //write to text file
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("settings.txt"));
        
        //traverse list of file objects and write each one to the settings file
        for (File item : files)
        {
            bufferedWriter.append(item.toString());
            bufferedWriter.newLine();
        }
        
        bufferedWriter.newLine();
        bufferedWriter.close();
    }

    /**
     * //read in everything to do processing(list of file objects), delete old settings file, process
        //traverse list(for each of the file list passed) use .contains()
        //see if list you just read in from settings contains anything on the list
        //for everything in list passed, delete corresponding file in the ones 
        //use .remove on the list you just read in 
       
        
        //once teh thing read in has been processed, delete the old settings file
        //create the new settings file with the processed things
     */
    public static void removeFiles(List<File> files) throws IOException
    {
        //read in from settings file and put it into an arraylist of file objects
        
    }
    
    
    
    /**
     * 
     */
    public static List<File> getFiles() throws IOException
    {
        //gets all the file names from settings file and puts it in list, if none returns null
        //if file reader fails it will throw exception
        List<File> names = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("settings.txt"));
        String line = null;
        
        line = bufferedReader.readLine();
        while (line != null)
        {
            names.add(new File(line));
        }
        
        bufferedReader.close();
        return names;
    }
}