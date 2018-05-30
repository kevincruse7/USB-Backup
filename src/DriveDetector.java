import java.io.File;

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
    public void usbDriveEvent(USBStorageEvent event)
    {
        File device;
        
        if (event.getEventType().equals(DeviceEventType.CONNECTED))
        {
            device = event.getStorageDevice().getRootDirectory();
            
        }
    }
}