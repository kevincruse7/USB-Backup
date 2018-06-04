import java.util.List;
import java.util.ArrayList;

import net.samuelcampos.usbdrivedetector.*;
import net.samuelcampos.usbdrivedetector.events.*;

/**
 *  Manages sequence of events when drive is inserted into computer.
 * 
 * @author   Kevin Cruse
 * @version  0.1 Alpha
 */
public class DriveDetector implements IUSBDriveListener
{
    /**
     * 
     */
    public void usbDriveEvent(USBStorageEvent event)
    {
        USBStorageDevice device;
        
        if (event.getEventType().equals(DeviceEventType.CONNECTED))
        {
            device = event.getStorageDevice();
            
            if (Settings.getFiles().contains(device.getRootDirectory()))
            {
                ui.sendNotification("Backing up " + device.getDeviceName() + "...");
                Backup.run();
                ui.sendNotification("Backup complete.");
            }
            else
                ui.sendNotification("New drive detected. Configure backup settings in the app.");
        }
    }
}