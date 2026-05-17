import java.io.IOException;
import java.time.LocalDateTime;
import java.util.PriorityQueue;

public class NotificationScheduler implements Runnable 
{
    private final PriorityQueue<Notification> queue;

    public NotificationScheduler(PriorityQueue<Notification> queue) 
    {
        this.queue = queue;
    }

    @Override
    public void run() 
    {
        while (true) 
        {
            try 
            {
                LocalDateTime now = LocalDateTime.now();
                synchronized (queue) 
                {
                    while (!queue.isEmpty() && !queue.peek().getTime().isAfter(now)) 
                    {
                        Notification n = queue.poll();
                        try 
                        {
                            n.getClientWriter().write(n.getMessage());
                            n.getClientWriter().newLine();
                            n.getClientWriter().flush();
                            System.out.println("[SENT] Notification: '" + n.getMessage() + "'");
                        } 
                        catch (IOException e) 
                        {
                            System.err.println("[SEND ERROR] Client unreachable for notification: " + n.getMessage());
                        }
                    }
                }
                Thread.sleep(1000);
            } 
            catch (InterruptedException e) 
            {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
