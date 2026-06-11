import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame 
{
    private BoardPanel boardPanel;
    private JLabel statusLabel;

    public GameWindow(Board board, GameController controller) 
    {
        setTitle("Warcaby");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        statusLabel = new JLabel("white move | time: 0 sek", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(statusLabel, BorderLayout.NORTH);

        boardPanel = new BoardPanel(board, controller);
        add(boardPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void updateTimer(int seconds, boolean isWhiteTurn) {
        String turnText = isWhiteTurn ? "white move" : "black move";
        statusLabel.setText(turnText + " | time: " + seconds + " sek");
    }

    public void setSelected(int row, int col) {
        boardPanel.setSelected(row, col);
    }

    public void repaintBoard() {
        boardPanel.repaint();
    }
}