import java.io.BufferedReader;
import java.io.IOException;

public class ServerListener implements Runnable 
{
    private final BufferedReader in;

    public ServerListener(BufferedReader in) 
    {
        this.in = in;
    }

    @Override
    public void run() 
    {
        try 
        {
            String serverMessage;
            while ((serverMessage = in.readLine()) != null) 
            {
                System.out.println("\n\n [NOTIFICATION FROM SERVER]: " + serverMessage);
                System.out.print("Enter notification text (or 'exit' to quit): ");
            }
        }
        catch (IOException e) 
        {
            System.out.println("\n[SERVER] Connection to server lost.");
            System.exit(0);
        }
    }
}
