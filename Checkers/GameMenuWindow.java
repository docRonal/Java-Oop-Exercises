import javax.swing.*;
import java.awt.*;

public class GameMenuWindow extends JFrame 
{
    public GameMenuWindow() 
    {
        setTitle("Checkers - Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Welcome to Checkers!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(titleLabel, BorderLayout.NORTH);

        JPanel settingsPanel = new JPanel(new GridLayout(2, 2, 10, 15));
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel modeLabel = new JLabel("Game Mode:");
        modeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Trzy tryby: PvP, PvE, LAN
        JComboBox<String> modeBox = new JComboBox<>(new String[]{"Player vs Player", "Player vs Computer", "LAN Multiplayer"});
        
        JLabel colorLabel = new JLabel("Your Color (vs PC):");
        colorLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JComboBox<String> colorBox = new JComboBox<>(new String[]{"White (Goes first)", "Black"});

        settingsPanel.add(modeLabel);
        settingsPanel.add(modeBox);
        settingsPanel.add(colorLabel);
        settingsPanel.add(colorBox);
        add(settingsPanel, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setPreferredSize(new Dimension(100, 45));
        
        startButton.addActionListener(e -> 
        {
            int selectedMode = modeBox.getSelectedIndex();
            boolean playAsWhite = colorBox.getSelectedIndex() == 0;

            Player pWhite = null;
            Player pBlack = null;
            NetworkManager nm = null;

            // Wybór trybu
            if (selectedMode == 0) // PvP
            { 
                pWhite = new HumanPlayer(true);
                pBlack = new HumanPlayer(false);
            } 
            else if (selectedMode == 1) // PvE
            { 
                if (playAsWhite) 
                {
                    pWhite = new HumanPlayer(true);   
                    pBlack = new AIPlayer(false);    
                } 
                else 
                {
                    pWhite = new AIPlayer(true);     
                    pBlack = new HumanPlayer(false);  
                }
            } 
            else if (selectedMode == 2) // LAN Multiplayer
            { 
                String[] options = {"Host Game", "Join Game"};
                int choice = JOptionPane.showOptionDialog(this, "Host a server or join existing?", "LAN Setup",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                nm = new NetworkManager();
                if (choice == 0) 
                {
                    // Tworzysz serwer (Host gra białymi)
                    pWhite = new HumanPlayer(true);
                    pBlack = new NetworkPlayer(false);
                } 
                else 
                {
                    // Dołączasz (Klient gra czarnymi)
                    String ip = JOptionPane.showInputDialog(this, "Enter Host IP address (np. localhost):", "localhost");
                    if (ip == null || ip.isEmpty()) return; // Anulowano
                    pWhite = new NetworkPlayer(true);
                    pBlack = new HumanPlayer(false);
                }
            }

            setVisible(false);
            dispose(); // Zamknięcie menu

            Board board = new Board();
            GameController controller = new GameController(board, pWhite, pBlack);
            GameWindow window = new GameWindow(board, controller);
            
            // Podłączenie neta w tle, zeby nie zablokowac GUI
            if (selectedMode == 2) 
            {
                NetworkManager finalNm = nm;
                Player finalPWhite = pWhite;
                
                new Thread(() -> 
                {
                    try 
                    {
                        if (finalPWhite instanceof HumanPlayer) 
                        {
                            window.setTitle("Checkers (Hosting on port 12345...)");
                            finalNm.hostGame(8080, controller); // Otwiera port 12345
                            window.setTitle("Checkers - LAN (White)");
                        } 
                        else 
                        {
                            window.setTitle("Checkers (Connecting...)");
                            finalNm.joinGame("localhost", 8080, controller); // Łączy z portem 12345
                            window.setTitle("Checkers - LAN (Black)");
                        }
                        controller.setNetworkManager(finalNm); // Podpinamy neta pod kontroler
                    } 
                    catch (Exception ex) 
                    {
                        JOptionPane.showMessageDialog(null, "Network Error: " + ex.getMessage());
                        System.exit(0);
                    }
                }).start();
            }

            controller.setWindow(window);
            window.setVisible(true);
        });

        add(startButton, BorderLayout.SOUTH);

        pack();
        setSize(380, 220);
        setLocationRelativeTo(null); // Na środku ekranu
        setResizable(false);
    }
}