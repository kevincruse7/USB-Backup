package usbbackup;

//Java libraries
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

//JavaFX libraries
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 *  JavaFXML controller for user interface.
 * 
 * @author   Samir Nafez
 * @version  1.0
 */
public class UserInterface
{
    @FXML private AnchorPane pane;
    @FXML private Text drivePath;
    @FXML private Text status;
    @FXML private ListView<String> driveView;
    
    /**
     *  Set up list view factory and initial values.
     */
    @FXML
    public void initialize()
    {
        File dir;
        List<File> files;
        List<String> paths;

        try
        {
            //Set drive path if present
            dir = Settings.getDirectory();
            if (dir != null && !dir.toString().equals("temp"))
                drivePath.setText(dir.toString());
                
            //Display files marked for backup if present
            files = Settings.getFiles();
            paths = new LinkedList<>();
            for (File item : files)
                paths.add(item.toString());
            paths.remove(0);
            driveView.getItems().addAll(paths);
        }
        catch (Exception e)
        {
            Main.exit("reading paths from \'settings.txt\'", e);
        }

        //Create custom ListView tiles
        driveView.setCellFactory(param -> new PathItem());
        
        //Setup status changer method
        Main.setUI(this);
    }

    /**
     *  Backup button listener.
     */
    @FXML
    public void backup(ActionEvent event)
    {
        Backup backup = new Backup();

        try
        {
            backup.run();
        }
        catch (Exception e)
        {
            Main.exit("backing up USB drive", e);
        }
    }

    /**
     *  Choose location button listener.
     *  Sets location at which backups are to be stored.
     */
    @FXML
    public void chooseBackupLocation(ActionEvent event)
    {
        DirectoryChooser chooser = new DirectoryChooser();
        File directory;

        chooser.setTitle("Backup Location Selection");
        directory = chooser.showDialog(pane.getScene().getWindow());

        if (directory != null)
        {
            try
            {
                Settings.setDirectory(directory);
            }
            catch (Exception e)
            {
                Main.exit("setting the backup directory", e);
            }

            drivePath.setText(directory.toString());
        }
    }

    /**
     *  Choose drive button listener.
     *  Choose entire drives or directories to backup.
     */
    @FXML
    public void chooseDrive(ActionEvent event)
    {
        DirectoryChooser chooser = new DirectoryChooser();
        List<File> files, copy;
        List<String> paths;
        File drive;

        chooser.setTitle("Drive Selection");
        drive = chooser.showDialog(pane.getScene().getWindow());

        if (drive != null)
        {
            files = new LinkedList<>(Arrays.asList(drive.listFiles()));
            paths = new ArrayList<>();

            //Create copy of list for reference when recursively searching directories
            copy = new LinkedList<>();
            for (File item : files)
                if (item.isDirectory())
                    searchDir(item, copy);
                else
                    copy.add(item);
            files = copy;
            files.sort((obj1, obj2) -> obj1.toString().compareTo(obj2.toString()));
            
            //Convert files to strings for display in ListView
            for (File item : files)
                paths.add(item.toString());
            paths.sort((String obj1, String obj2) -> obj1.compareTo(obj2));
            driveView.getItems().addAll(paths);

            try
            {
                if (Settings.getFiles() != null && !Settings.getFiles().containsAll(files))
                    Settings.addFiles(files);
            }
            catch (Exception e)
            {
                Main.exit("trying to add drive paths to \'settings.txt\'", e);
            }
        }
    }

    /**
     *  Choose files button listener.
     *  Choose specific files to backup.
     */
    @FXML
    public void chooseFiles(ActionEvent event)
    {
        FileChooser chooser = new FileChooser();
        List<File> files;
        List<String> paths;

        chooser.setTitle("File Selection");
        files = chooser.showOpenMultipleDialog(pane.getScene().getWindow());

        if (files != null)
        {
            //Add selected files to ListView
            paths = new ArrayList<>();
            for (File item : files)
                paths.add(item.toString());
            paths.sort((String obj1, String obj2) -> obj1.compareTo(obj2));
            driveView.getItems().addAll(paths);

            try
            {
                if (Settings.getFiles() != null && !Settings.getFiles().containsAll(files))
                    Settings.addFiles(files);
            }
            catch (Exception e)
            {
                Main.exit("trying to add file paths to \'settings.txt\'", e);
            }
        }
    }

    /**
     *  Exit menu option listener.
     *  Exit program.
     */
    @FXML
    public void exit(ActionEvent event)
    {
        Platform.exit();
    }
    
    /**
     *  Set program status at bottom left of UI.
     */
    public void setStatus(String s)
    {
        status.setText(s);
    }

    /**
     *  Recursively search directories to get paths to actual files.
     */
    private void searchDir(File dir, List<File> files)
    {
        File[] sub = dir.listFiles();
        
        for (File file : sub)
        {
            if (file.isDirectory())
                searchDir(file, files);
            else
                files.add(file);
        }
    }
}
