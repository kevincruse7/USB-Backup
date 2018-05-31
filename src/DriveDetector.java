import java.io.File;
import java.util.List;
import java.util.ArrayList;

import net.samuelcampos.usbdrivedetector.*;
import net.samuelcampos.usbdrivedetector.events.*;

/**
 * 
 * 
 * @author
 * @version
 */
public class DriveDetector implements IUSBDriveListener
{
    private UserInterface ui;
    
    public DriveDetector(UserInterface u)
    {
        ui = u;
    }
    
    public void usbDriveEvent(USBStorageEvent event)
    {
        USBStorageDevice device;
        File root;
        
        if (event.getEventType().equals(DeviceEventType.CONNECTED))
        {
            device = event.getStorageDevice();
            root = device.getRootDirectory();
            
            if (Settings.getFiles().contains(root))
            {
                ui.sendNotification("Backing up " + device.getDeviceName());
                Backup.run();
            }
        }
    }
}