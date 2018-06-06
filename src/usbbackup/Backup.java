package usbbackup;

import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;

/**
 * Class takes care of backing up the files with built in recovery
 * 
 * @author Ryan Leahy 
 * @version 0.6 Beta build
 */
public class Backup
{
    private List listOfFiles, filesCreated;
    private File backupDirectory;
    
    /**
     * Empty constructor for backup class, all initialization is handled in the run() method call, so if you want to reinitialize just call run again
     */
    public Backup()
    {
    }
    
    /**
     * Method accomplishes the backup process, all you got to do is call it and it will take care of everything
     * 
     * @param none
     * @return boolean that returns true if the backup process executed without issue
     */
    public void run() throws IOException
    {
        initialize(); //initialize files to be backed up
        checkIfBackup(); //sees which files need to be backed up
        createDirs(); //creates the directories that are missing from the structure
        createAndModify(); //puts the files onto the hard drive and saves them with .BAk
        changeExtension(); //changes the extensions back to normal if the above executes without issue
        changeModified(); //changes the last modified time to match the files on the usb
    }
    
    /*
     * Method takes care of initializing static field elements in class
     */
    private void initialize() throws IOException
    {
        backupDirectory = Settings.getDirectory(); //gets the directory of where to put all the backed up files
        listOfFiles = Settings.getFiles(); //linked list to store a copy of all the files passed from the Settings class
        filesCreated = new LinkedList(); //linked list to store all the files that were created so we can go through and delete the others after it successfully backs up and delete the others and change the file names
    }
    
    /*
     * Method takes care of creating the folder hierarchy inside the destination folder to maintain the higherarchy that is in the usb
     */
    private void createDirs() throws IOException
    {
         String dir; //holds the directory to try and create
         String root; //holds the root to be removed from the path
         File current; //holds the file where the path is being extracted from
         Iterator iter = listOfFiles.iterator(); //iterator to traverse through the list of files to back up
         Path filePath; //intermediate between File objects and Strings
         
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
                throw new IOException("Failed to create directories on target drive");
            }
         }
    }
    
    /*
     * Method goes through the given list of Files and compares them to files that already exist to see if they should be backed up
     * 
     * Currently the method of checking to see if it needs to be backed up is check if the last modified dates match
     * if they do the file is removed from the list
     */
    private void checkIfBackup()
    {
        String dir; //holds the directory to try and create
        String root; //holds the root to be removed from the path
        File currentUSB , currentHD; //holds the file coming off the list and also holds the file that already exists on the hard drive
        Iterator iter = listOfFiles.iterator(); //iterator to traverse through the list of files to back up
        Path filePath; //intermediate between File objects and Strings
        
        while (iter.hasNext())
        { 
           currentUSB = (File)iter.next();
           filePath = currentUSB.toPath(); //puts the full directory into the usb
           root = filePath.getRoot().toString(); //say the file is E:\User\text.txt it will hold E:
           dir = filePath.subpath(0, filePath.getNameCount()).toString(); //gets the whole files address
           dir = dir.substring(dir.indexOf(root) + root.length()); //removes root from the file path say you have E:\User\file.txt at this point now you only have User\file.txt
           dir = backupDirectory.getAbsolutePath() + dir; //say the path for the hard drive is C:\test\USB and now the files in the filePath is \User\file.txt now you have C:\test\USB\User\file.txt
           filePath = Paths.get(dir); //makes the string into an actual path
           
           if (Files.exists(filePath)) //if the file trying to be backed up on the hard drive exists
           {
               currentHD = filePath.toFile(); //put that file into a File reference
               
               if (currentUSB.lastModified() == currentHD.lastModified()) //check if the modified date on both files match
               {
                   iter.remove(); //if they do match there is no need to back it up so just remove it from the local list
               }
           }
        }
    }
    
    /*
     * Method does the actual backing up of the files
     * 
     * the process of doing this has built in recovery, as the files are written they are saved as .BAK extensions
     * and if the method can fully execute without catching any exceptions the method finishes, if an exception occurs
     * all of the backups are deleted and the method throws an exception.
     */
    private void createAndModify() throws IOException
    {
        String dir; //holds the directory to try and create
        String root; //holds the root to be removed from the path
        File currentUSB , currentHD; //holds the file coming off the list and also holds the file that already exists on the hard drive
        Iterator fileListIter = listOfFiles.iterator(); //iterator to traverse through the list of files to back up
        Path filePath; //intermediate between File objects and Strings
        Iterator filesCreatedIter = filesCreated.iterator(); //iterator for the list that keeps track of the files created, will be used in an event of an error or just to go through and change the names
        
        while (fileListIter.hasNext())
        { 
           currentUSB = (File)fileListIter.next();
           filePath = currentUSB.toPath(); //puts the full directory into the usb
           root = filePath.getRoot().toString(); //say the file is E:\User\text.txt it will hold E:
           dir = filePath.subpath(0, filePath.getNameCount()).toString(); //gets the whole files address
           dir = dir.substring(dir.indexOf(root) + root.length()); //removes root from the file path say you have E:\User\file.txt at this point now you only have User\file.txt
           dir = backupDirectory.getAbsolutePath() + dir + ".BAK"; //say the path for the hard drive is C:\test\USB and now the files in the filePath is \User\file.txt now you have C:\test\USB\User\file.txt.BAK
           filePath = Paths.get(dir); //makes the string into an actual path
           
           try
           {
               filesCreated.add(Files.createFile(filePath)); //creates a blank .BAK file on hard drive and adds the path to a linked list storing all the paths
               Files.copy(currentUSB.toPath(), filePath); //copies file from the usb to the just made backup
           }
           catch (IOException e) //if an exception is caught then the program will delete all the .BAK files created and tell the user an error occured and for right now crash the program
           {
               while (filesCreatedIter.hasNext()) //while loop to delete the files
               {
                   try
                   {
                       Files.delete((Path)filesCreatedIter.next());
                   }
                   catch (IOException e2) //honestly if another error is thrown at this point something has gone really wrong so there really isn't any way to recover
                   {
                       throw new IOException("Failed when trying to recover from creating and copying files to target drive");
                   }
               }
               
               throw new IOException("Failed to create and copy files to target drive");
           }
        }
    }
    
    /*
     * This method takes care of deleting all the .BAK files and copying the contents of the BAK into the original before it gets deleted
     */
    private void changeExtension() throws IOException
    {
        String copyStr; //String that will hold the path of the file to copy once the file path has been worked out
        Path copyPath, curPath; //holds the actual path of the file to copy the .BAK to
        Iterator filesCreatedIter = filesCreated.iterator(); //iterator for the files created used to help copy and delete the bak
        
        while (filesCreatedIter.hasNext())
        {
            curPath = (Path)filesCreatedIter.next(); //puts the path into a reference so I can do my work mwahahahaha
            copyStr = curPath.toString();
            copyStr = copyStr.substring(0, copyStr.indexOf(".BAK")); //removes the .BAK from the string
            copyPath = Paths.get(copyStr); //puts the string into the path
            
            try
            {
                Files.copy(curPath, copyPath); //copy the .BAK into the original
                Files.delete(curPath); //delete the .BAK
            }
            catch (IOException e)
            {
                throw new IOException("Faled to copy contents of .BAK files to destination files and remove the .BAK files");
            }
        }
    }
    
    /*
     * Method changes all the last modified times to match the ones on the usb
     * if an exception is caught it will change all the times to match the beginning of the epoch so that all the files will be
     * re backed up next time it's plugged in
     */
    private void changeModified() throws IOException
    {
        Iterator filesTargetIter = filesCreated.iterator(), filesSourceIter = listOfFiles.iterator();
        Path filesTargetPath, filesSourcePath;
        
        while (filesTargetIter.hasNext()) 
        {
            filesTargetPath = (Path)filesTargetIter.next();
            filesSourcePath = ((File)filesSourceIter.next()).toPath();
            
            try
            {
                Files.setLastModifiedTime(filesTargetPath, Files.getLastModifiedTime(filesSourcePath)); //this line gets the last modified date from the usb and writes it to the hard drive
            }
            catch (IOException e)
            {
                filesTargetIter = filesCreated.iterator();
                
                while (filesTargetIter.hasNext()) //this block will set all the file times to the first epoch time so all the files can be re backed up
                {
                    try
                    {
                        Files.setLastModifiedTime((Path)filesTargetIter.next(), FileTime.fromMillis(0));
                    }
                    catch (IOException e2) //once again if this somehow happens the user is kind of on their own
                    {
                        throw new IOException("Failed to set last modified date to beginning of epoch after initially failing to set the modified date from the usb, recommended to delete backup and rerun program");
                    }
                }
                
                throw new IOException("Failed to set last modified date for the files from the usb");
            }
        }
    }
}