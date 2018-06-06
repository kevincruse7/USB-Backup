package usbbackup;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import net.samuelcampos.usbdrivedetector.*;
import net.samuelcampos.usbdrivedetector.events.*;

/**
 *  Manages sequence of events when drive is inserted into computer.
 * 
 * @author   Kevin Cruse
 * @version  Alpha
 */
public class DriveDetector implements IUSBDriveListener
{
    /**
     * 
     */
    public void usbDriveEvent(USBStorageEvent event)
    {
        Backup backup;
        USBStorageDevice device;
        
        if (event.getEventType().equals(DeviceEventType.CONNECTED))
        {
            backup = new Backup();
            device = event.getStorageDevice();
            
            try
            {
                if (Settings.getFiles().contains(device.getRootDirectory()))
                    backup.run();
            }
            catch (IOException e)
            {
                System.err.println("An error occurred when trying to read \'settings.txt\': " + e.getMessage());
                System.exit(1);
            }
        }
    }
}