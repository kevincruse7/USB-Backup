import java.io.File;

import net.samuelcampos.usbdrivedetector.events.IUSBDriveListener;
import net.samuelcampos.usbdrivedetector.events.USBStorageEvent;

/**
 * 
 * 
 * @author
 * @version
 */
public class DriveDetector implements IUSBDriveListener
{
    public File getDrive()
    {
        return null;
    }
    
    public void usbDriveEvent(USBStorageEvent event)
    {
        System.out.println(event.getStorageDevice().getRootDirectory());
    }
}