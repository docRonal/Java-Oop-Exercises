public class GameStatusChecker {
    
    public static boolean hasWhitePieces(Board board) 
    {
        return countPieces(board, true) > 0;
    }

    public static boolean hasBlackPieces(Board board) 
    {
        return countPieces(board, false) > 0;
    }

    private static int countPieces(Board board, boolean lookForWhite) 
    {
        int count = 0;
        for (int r = 0; r < Board.SIZE; r++) 
        {
            for (int c = 0; c < Board.SIZE; c++) 
            {
                Piece p = board.getPiece(r, c);
                if (p != Piece.EMPTY && p.isWhite() == lookForWhite) 
                {
                    count++;
                }
            }
        }
        return count;
    }
}