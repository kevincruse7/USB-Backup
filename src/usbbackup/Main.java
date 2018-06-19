package usbbackup;

//Java imports
import java.io.IOException;

//Java Swing and JavaFX imports
import java.awt.AWTException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//USBDriveDetector libraries are maintained by Samuel Campos at https://github.com/samuelcampos/usbdrivedetector
import net.samuelcampos.usbdrivedetector.USBDeviceDetectorManager;

/**
 *  JavaFX entry point and main controller class.
 * 
 * @author   Kevin Cruse
 * @version  1.0
 */
public class Main extends Application
{
    private USBDeviceDetectorManager manager;
    private DriveDetector detector;
    private Stage stage;
    private TrayInterface trayIcon;
    private static UserInterface ui;
    
    public static void main(String[] args)
    {
        launch(args);
    }
    
    /**
     *  Allow outside classes to set status messages on UI.
     */
    public static void setStatus(String status)
    {
        ui.setStatus(status);
    }
    
    /**
     *  Receive reference to UI for use in messaging.
     */
    public static void setUI(UserInterface u)
    {
        ui = u;
    }
    
    /**
     *  Exit program when a fatal error occurs
     */
    public static void exit(String action, Exception e)
    {
        System.err.println("An error occurred when " + action + ": " + e.toString());
        e.printStackTrace();
        System.exit(1);
    }
    
    /**
     *  Initialize program threads and listeners
     */
    @Override
    public void start(Stage s)
    {
        stage = s;
        manager = new USBDeviceDetectorManager();
        detector = new DriveDetector(stage);
        
        //Add drive detector to listeners
        manager.addDriveListener(detector);
        
        //Red exit button does not close program, only UI
        Platform.setImplicitExit(false);
        
        //Setup tray icon
        try
        {
            trayIcon = new TrayInterface(stage);
        }
        catch (AWTException e)
        {
            exit("initializing system tray icon", e);
        }
        
        //Setup UI
        try
        {
            initStage();
        }
        catch (IOException e)
        {
            exit("initializing JavaFX stage", e);
        }
        
        stage.show();
    }
    
    /**
     *  Safely exit program when user selects exit option.
     */
    @Override
    public void stop()
    {
        trayIcon.close();
        manager.removeDriveListener(detector);
    }
    
    /*
     *  Load in interface.fxml file for UI
     */
    private void initStage() throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("resources/interface.fxml"));
        
        stage.setTitle("USB Backup");
        stage.getIcons().add(new Image(getClass().getClassLoader().getResource("resources/icon.png").toString()));
        stage.setScene(new Scene(root, 490, 235));
        stage.setResizable(false);
    }
}