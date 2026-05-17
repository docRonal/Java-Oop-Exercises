public class ServerMain {
    public static void main(String[] args) 
    {
        int port = 8080;
        NotificationServer server = new NotificationServer(port);
        server.start();
    }
}
