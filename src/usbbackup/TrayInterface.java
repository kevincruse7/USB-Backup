package usbbackup;

//Java Swing and JavaFX imports
import java.awt.AWTException;
import java.awt.event.ActionEvent;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import javafx.application.Platform;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *  Controller for system tray icon.
 * 
 * @author   Kevin Cruse
 * @version  1.0
 */
public class TrayInterface
{
    private Stage stage;
    private SystemTray tray;
    private TrayIcon trayIcon;
    
    /**
     *  Setup system tray icon.
     */
    public TrayInterface(Stage s) throws AWTException
    {
        stage = s;
        tray = SystemTray.getSystemTray();
        
        //Create popup menu
        PopupMenu menu = new PopupMenu();
        MenuItem open = new MenuItem("Open");
        MenuItem exit = new MenuItem("Exit");
        
        //Add action listeners to menu options
        open.addActionListener(e -> Platform.runLater(() -> stage.show()));
        exit.addActionListener(e -> Platform.exit());
        menu.add(open);
        menu.add(exit);
        
        //Set tray icon
        trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../resources/icon.png")), "USB Backup", menu);
        trayIcon.setImageAutoSize(true);
        
        //Add icon to tray
        tray.add(trayIcon);
    }
    
    /**
     *  Remove system tray icon.
     */
    public void close()
    {
        tray.remove(trayIcon);
    }
}