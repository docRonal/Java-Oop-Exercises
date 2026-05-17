import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

public class NotificationClient 
{
    private final String host;
    private final int port;

    public NotificationClient(String host, int port) 
    {
        this.host = host;
        this.port = port;
    }

    public void start() 
    {
        try (Socket socket = new Socket(host, port);
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) 
        {

            System.out.println("[*] Successfully connected to server " + host + ":" + port);

            Thread listenerThread = new Thread(new ServerListener(in));
            listenerThread.setDaemon(true);
            listenerThread.start();

            UserInputHandler inputHandler = new UserInputHandler(out);
            inputHandler.handleInput();

        }
        catch (ConnectException e) 
        {
            System.err.println("[ERROR] Server is unreachable. Make sure it is running.");
        } 
        catch (IOException e) 
        {
            System.err.println("[ERROR] Network error: " + e.getMessage());
        }
        System.out.println("[*] Client shutdown.");
    }
}
