/*
 * 
 */

package usbbackup;

//
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

/**
 * 
 * 
 * @author   Kevin Cruse
 * @version  Alpha
 */
public class TrayInterface
{
    private TrayIcon trayIcon;  //
    
    /**
     * 
     */
    public TrayInterface(Stage stage) throws AWTException
    {
        SystemTray tray = SystemTray.getSystemTray();
        
        PopupMenu menu = new PopupMenu();
        MenuItem open = new MenuItem("Open");
        MenuItem exit = new MenuItem("Exit");
        
        open.addActionListener(e -> stage.show());
        exit.addActionListener(e -> Platform.exit());
        menu.add(open);
        menu.add(exit);
        
        trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage("/resources/icon.png"), "USB Backup", menu);
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(e -> stage.show());
        
        tray.add(trayIcon);
    }
}