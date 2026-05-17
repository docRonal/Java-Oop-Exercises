import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UserInputHandler 
{
    private final BufferedWriter out;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public UserInputHandler(BufferedWriter out) 
    {
        this.out = out;
    }

    public void handleInput() 
    {
        try (Scanner scanner = new Scanner(System.in)) 
        {
            while (true) 
            {
                System.out.print("Enter notification text (or 'exit' to quit): ");
                String text = scanner.nextLine();

                if ("exit".equalsIgnoreCase(text)) 
                    {
                        break;
                    }

                System.out.print("Enter target time (format YYYY-MM-DD HH:MM:SS): ");
                String timeStr = scanner.nextLine();

                if (!isValidTime(timeStr)) 
                {
                    continue;
                }

                String payload = timeStr + "|" + text;
                sendToServer(payload);
            }
        }
    }

    private boolean isValidTime(String timeStr) 
    {
        try 
        {
            LocalDateTime targetTime = LocalDateTime.parse(timeStr, FORMATTER);
                if (targetTime.isBefore(LocalDateTime.now())) 
                {
                    System.out.println("[ERROR] Time cannot be in the past!\n");
                    return false;
                }
            return true;
        } 
        catch (DateTimeParseException e) 
        {
            System.out.println("[ERROR] Invalid time format! Try again.\n");
            return false;
        }
    }

    private void sendToServer(String payload) 
    {
        try 
        {
            out.write(payload);
            out.newLine();
            out.flush();
            System.out.println("[*] Notification successfully sent to server.\n");
        } 
        catch (IOException e) 
        {
            System.err.println("[ERROR] Failed to send data: " + e.getMessage());
        }
    }
}
