import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.PriorityQueue;

public class NotificationServer 
{
    private final int port;
    private final PriorityQueue<Notification> queue;

    public NotificationServer(int port) 
    {
        this.port = port;
        this.queue = new PriorityQueue<>();
    }

    public void start() 
    {
        System.out.println("[*] Starting server on port " + port);

        Thread scheduler = new Thread(new NotificationScheduler(queue));
        scheduler.setDaemon(true);
        scheduler.start();

        try (ServerSocket serverSocket = new ServerSocket(port)) 
        {
            while (true) 
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println("[CONNECTION] Client connected: " + clientSocket.getInetAddress());
                
                new Thread(new ClientHandler(clientSocket, queue)).start();
            }
        } catch (IOException e) {
            System.err.println("[FATAL ERROR] Server error: " + e.getMessage());
        }
    }
}
