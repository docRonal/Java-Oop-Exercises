import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.SwingUtilities;

public class NetworkManager 
{
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private GameController controller;

    // Tworzenie serwera (Host)
    public void hostGame(int port, GameController ctrl) throws Exception 
    {
        ServerSocket server = new ServerSocket(port);
        System.out.println("Czekam na drugiego gracza...");
        this.socket = server.accept(); // Czeka aż ktoś się podłączy
        System.out.println("Gracz dołączył!");
        setupStreams(ctrl);
    }

    // Podłączanie do serwera (Klient)
    public void joinGame(String ip, int port, GameController ctrl) throws Exception 
    {
        this.socket = new Socket(ip, port);
        System.out.println("Podłączono do hosta!");
        setupStreams(ctrl);
    }

    // Ustawienie wysyłania i odbierania danych
    private void setupStreams(GameController ctrl) throws Exception 
    {
        this.controller = ctrl;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());

        // Osobny wątek do nasłuchiwania ruchów
        new Thread(() -> 
        {
            try 
            {
                while (true) 
                {
                    Move move = (Move) in.readObject(); // Czekamy na ruch z sieci
                    
                    // Wrzucamy ruch na planszę w głównym wątku GUI
                    SwingUtilities.invokeLater(() -> 
                    {
                        controller.applyNetworkMove(move); 
                    });
                }
            } 
            catch (Exception e) 
            {
                System.out.println("Połączenie przerwane.");
            }
        }).start();
    }

    // Wysyłanie naszego ruchu do sieci
    public void sendMove(Move move) 
    {
        try 
        {
            if (out != null) 
            {
                out.writeObject(move);
                out.flush(); // Wypycha dane w neta
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}