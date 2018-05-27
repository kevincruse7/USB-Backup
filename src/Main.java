import net.samuelcampos.usbdrivedetector.USBDeviceDetectorManager;

/**
 *  Driver class for program.
 * 
 * @author   Kevin Cruse
 * @version  1.0
 */
public class Main
{
    public static void main(String[] args)
    {
        USBDeviceDetectorManager manager = new USBDeviceDetectorManager();
        DriveDetector detector = new DriveDetector();
        
        manager.addDriveListener(detector);
    }
}