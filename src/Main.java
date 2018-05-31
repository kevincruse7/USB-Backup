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
        UserInterface ui = new UserInterface();
        USBDeviceDetectorManager manager = new USBDeviceDetectorManager();
        
        ui.start();
        manager.addListener(new DriveDetector(ui));
    }
}