package usbbackup;

//Java imports
import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

//JavaFX imports
import javafx.application.Platform;
import javafx.stage.Stage;

//USBDriveDetector libraries are maintained by Samuel Campos at https://github.com/samuelcampos/usbdrivedetector
import net.samuelcampos.usbdrivedetector.*;
import net.samuelcampos.usbdrivedetector.events.*;

/**
 *  Manages sequence of events when drive is inserted into computer.
 * 
 * @author   Kevin Cruse
 * @version  1.0
 */
public class DriveDetector implements IUSBDriveListener
{
    private Stage stage;
    
    /**
     *  Setup internal reference to UI stage.
     */
    public DriveDetector(Stage s)
    {
        stage = s;
    }
    
    /**
     *  Open UI when drives are inserted and backup if drive is already setup.
     */
    @Override
    public void usbDriveEvent(USBStorageEvent event)
    {
        Backup backup;
        USBStorageDevice device;
        List<File> files;
        String root;
        boolean present;
        
        if (event.getEventType().equals(DeviceEventType.CONNECTED))
        {
            backup = new Backup();
            device = event.getStorageDevice();
            root = device.getRootDirectory().toString();
            present = false;
            
            try
            {
                //Search for drive path in settings.txt
                files = Settings.getFiles();
                for (File item : files)
                    if (item.toString().indexOf(root) != -1)
                    {
                        present = true;
                        break;
                    }
                
                //Open UI and run backup if drive is found
                Platform.runLater(() -> stage.show());
                if (present)
                    backup.run();
                else
                    Platform.runLater(() -> Main.setStatus("New drive detected."));
            }
            catch (IOException e)
            {
                Main.exit("trying to backup USB drive", e);
            }
        }
    }
}