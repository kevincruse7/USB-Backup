package usbbackup;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

/**
 * 
 * 
 * @author
 * @version  Alpha
 */
public class UserInterface
{
    @FXML private Text drivePath;
    @FXML private ListView<?> driveView;

    @FXML
    public void backup(ActionEvent event)
    {

    }

    @FXML
    public void chooseBackupLocation(ActionEvent event)
    {

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
