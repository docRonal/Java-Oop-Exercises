import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.PriorityQueue;

public class ClientHandler implements Runnable 
{
    private final Socket clientSocket;
    private final PriorityQueue<Notification> queue;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ClientHandler(Socket socket, PriorityQueue<Notification> queue) 
    {
        this.clientSocket = socket;
        this.queue = queue;
    }

    @Override
    public void run() 
    {
        try 
        (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))
        ) 
        {
            String inputLine;
            while ((inputLine = in.readLine()) != null) 
            {
                try
                {
                    String[] parts = inputLine.split("\\|", 2);
                    if (parts.length < 2) throw new IllegalArgumentException("Invalid data format");

                    LocalDateTime targetTime = LocalDateTime.parse(parts[0], FORMATTER);
                    String message = parts[1];

                    synchronized (queue) 
                        {
                            queue.add(new Notification(targetTime, message, out));
                        }
                    System.out.println("[SAVED] Notification for " + parts[0] + " from " + clientSocket.getInetAddress());

                } 
                catch (DateTimeParseException | IllegalArgumentException e) 
                {
                    System.err.println("[ERROR] Invalid data from client: " + e.getMessage());
                }
            }
        } 
        catch (IOException e) 
        {
            System.out.println("[DISCONNECT] Connection with client " + clientSocket.getInetAddress() + " lost.");
        } 
        finally 
        {
            try 
            {
                clientSocket.close();
            } 
            catch (IOException e) 
            {
                System.err.println("[ERROR] Failed to close socket.");
            }
        }
    }
}
