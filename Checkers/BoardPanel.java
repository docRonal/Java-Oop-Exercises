import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class BoardPanel extends JPanel 
{
    private static final int TILE_SIZE = 80;
    private Board board;
    private int selRow = -1;
    private int selCol = -1;

    public BoardPanel(Board board, GameController controller) 
    {
        this.board = board;
        setPreferredSize(new Dimension(Board.SIZE * TILE_SIZE, Board.SIZE * TILE_SIZE));
        
        addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mousePressed(MouseEvent e) 
            {
                int col = e.getX() / TILE_SIZE;
                int row = e.getY() / TILE_SIZE;
                controller.handleSquareClick(row, col);
            }
        });
    }

    public void setSelected(int row, int col) 
    {
        this.selRow = row;
        this.selCol = col;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) 
            {
                if ((row + col) % 2 == 0) g.setColor(new Color(240, 217, 181));
                else g.setColor(new Color(181, 136, 99));
                
                g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);

                if (row == selRow && col == selCol) 
                {
                    g.setColor(new Color(255, 255, 0, 150));
                    g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }

                Piece piece = board.getPiece(row, col);
                if (piece != Piece.EMPTY) 
                {
                    g.setColor(piece.isWhite() ? Color.WHITE : Color.DARK_GRAY);
                    g.fillOval(col * TILE_SIZE + 10, row * TILE_SIZE + 10, TILE_SIZE - 20, TILE_SIZE - 20);
                    
                    g.setColor(Color.BLACK);
                    g.drawOval(col * TILE_SIZE + 10, row * TILE_SIZE + 10, TILE_SIZE - 20, TILE_SIZE - 20);

                    if (piece.isKing()) 
                    {
                        g.setColor(new Color(255, 215, 0)); 
                        g.fillOval(col * TILE_SIZE + 25, row * TILE_SIZE + 25, TILE_SIZE - 50, TILE_SIZE - 50);
                        g.setColor(Color.BLACK);
                        g.drawOval(col * TILE_SIZE + 25, row * TILE_SIZE + 25, TILE_SIZE - 50, TILE_SIZE - 50);
                        
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("Arial", Font.BOLD, 22));
                        g.drawString("K", col * TILE_SIZE + 33, row * TILE_SIZE + 50);
                    }
                }
            }
        }
    }
}