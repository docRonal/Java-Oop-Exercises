import java.io.BufferedWriter;
import java.time.LocalDateTime;

public class Notification implements Comparable<Notification> 
{
    private final LocalDateTime time;
    private final String message;
    private final BufferedWriter clientWriter;

    public Notification(LocalDateTime time, String message, BufferedWriter clientWriter) 
    {
        this.time = time;
        this.message = message;
        this.clientWriter = clientWriter;
    }

    public LocalDateTime getTime() 
    {
        return time;
    }

    public String getMessage() 
    {
        return message;
    }

    public BufferedWriter getClientWriter() 
    {
        return clientWriter;
    }

    @Override
    public int compareTo(Notification o) 
    {
        return this.time.compareTo(o.time);
    }
}
