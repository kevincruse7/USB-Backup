package usbbackup;

import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * 
 * 
 * @author
 * @version  Alpha
 */
public class UserInterface
{
    @FXML private AnchorPane pane;
    @FXML private Text drivePath;
    @FXML private ListView<?> driveView;

    @FXML
    public void backup(ActionEvent event)
    {
        Backup backup = new Backup();
        
        try
        {
            backup.run();
        }
        catch (IOException e)
        {
            System.err.println("An error occurred when backing up: " + e.getMessage());
            System.exit(1);
        }
    }

    @FXML
    public void chooseBackupLocation(ActionEvent event)
    {
        DirectoryChooser chooser = new DirectoryChooser();
        File directory;
        
        chooser.setTitle("Backup Location Selection");
        try
        {
            directory = chooser.showDialog((Stage)pane.getScene().getWindow());
            Settings.setDirectory(directory);
            
            if (!pane.getChildren().contains(drivePath))
                pane.getChildren().add(drivePath);
            
            drivePath.setText(directory.toString());
        }
        catch (IOException e)
        {
            System.err.println("An error occurred when setting the backup directory: " + e.getMessage());
        }
    }

    @FXML
    public void chooseDrives(ActionEvent event)
    {

    }

    @FXML
    public void exit(ActionEvent event)
    {
        Platform.exit();
    }

    @FXML
    public void openFileLocation(ActionEvent event)
    {

    }
}
