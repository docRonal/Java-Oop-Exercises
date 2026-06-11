
public class Board {
    public static final int SIZE = 8;
    private Piece[][] grid;

    public Board() 
    {
        grid = new Piece[SIZE][SIZE];
        initializeBoard();
    }

    public void initializeBoard() 
    {
        for (int row = 0; row < SIZE; row++) 
        {
            for (int col = 0; col < SIZE; col++) 
            {
                if ((row + col) % 2 != 0) 
                {
                    if (row < 3) grid[row][col] = Piece.BLACK;
                    else if (row > 4) grid[row][col] = Piece.WHITE;
                    else grid[row][col] = Piece.EMPTY;
                } 
                else
                {
                    grid[row][col] = Piece.EMPTY;
                }
            }
        }
    }

    public Piece getPiece(int row, int col) 
    {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) return Piece.EMPTY;
        return grid[row][col];
    }

    public void setPiece(int row, int col, Piece piece) 
    {
        grid[row][col] = piece;
    }
}