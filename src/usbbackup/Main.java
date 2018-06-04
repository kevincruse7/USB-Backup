package usbbackup;

//
import java.io.IOException;

//
import java.awt.AWTException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * 
 */
import net.samuelcampos.usbdrivedetector.USBDeviceDetectorManager;

/**
 *  
 * 
 * @author   Kevin Cruse
 * @version  Alpha
 */
public class Main extends Application
{
    private USBDeviceDetectorManager manager;  //
    private DriveDetector detector;            //
    private TrayInterface trayIcon;            //
    private Stage stage;                       //
    
    public static void main(String[] args)
    {
        launch(args);
    }
    
    /**
     * 
     */
    @Override
    public void start(Stage stage) throws AWTException, IOException
    {
        manager = new USBDeviceDetectorManager();
        detector = new DriveDetector();
        trayIcon = new TrayInterface(stage);
        this.stage = stage;
        
        manager.addDriveListener(detector);
        initStage();
    }
    
    /**
     * 
     */
    @Override
    public void stop()
    {
        manager.removeDriveListener(detector);
    }
    
    /*
     * 
     */
    private void initStage() throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/interface.fxml"));
        stage.setTitle("USB Backup");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/icon.png")));
        stage.setScene(new Scene(root, 490, 235));
        stage.setResizable(false);
    }
}