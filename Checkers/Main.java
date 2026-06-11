import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> {
            GameMenuWindow menu = new GameMenuWindow();
            menu.setVisible(true);
        });
    }
}