public class ClientMain {
    public static void main(String[] args) 
    {
        String host = "127.0.0.1";
        int port = 8080;
        
        NotificationClient client = new NotificationClient(host, port);
        client.start();
    }
}
