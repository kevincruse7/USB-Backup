/**
 * 
 * 
 * @author
 * @version
 */
public class UserInterface implements Runnable
{
    private Thread thread;
    
    public void run()
    {
        //GUI code goes here
    }
    
    public void start()
    {
        if (thread == null)
        {
            thread = new Thread(this, "GUI");
            thread.start();
        }
    }
}