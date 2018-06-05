package usbbackup;

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
        USBStorageDevice device;
        
        if (event.getEventType().equals(DeviceEventType.CONNECTED))
        {
            
        }
    }
}