public class MoveValidator {

    public static boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol, boolean isWhiteTurn, boolean isMultiJumping) 
    {
        // Pole musi być puste
        if (board.getPiece(toRow, toCol) != Piece.EMPTY) {return false;}

        int rowDiff = toRow - fromRow;
        int colDiff = toCol - fromCol;
        
        // Tylko ruch po przekątnej
        if (Math.abs(rowDiff) != Math.abs(colDiff)) {return false;}

        // Parametry ruchu
        int dist = Math.abs(rowDiff);
        int dRow = rowDiff / dist;
        int dCol = colDiff / dist;
        int enemiesCount = 0;
        
        // Skanowanie drogi
        for (int i = 1; i < dist; i++) 
        {
            int r = fromRow + i * dRow;
            int c = fromCol + i * dCol;
            Piece p = board.getPiece(r, c);
            if (p != Piece.EMPTY) 
            {
                // Bez bicia swoich
                if (p.isWhite() == isWhiteTurn) {return false;}
                enemiesCount++;
            }
        }
        
        // Maksymalnie 1 wróg na drodze
        if (enemiesCount > 1) {return false;}

        // Czy to damka?
        boolean isKing = board.getPiece(fromRow, fromCol).isKing();

        // Zwykły ruch (bez bicia)
        if (enemiesCount == 0) 
        {
            // W trakcie serii bicia nie można robić zwykłych ruchów
            if (isMultiJumping) {return false;}
            if (isKing) {return true;}
            
            // Pionek idzie tylko do przodu
            int direction = isWhiteTurn ? -1 : 1;
            return dist == 1 && rowDiff == direction;
        }
        // Ruch z biciem
        else 
        {
            // Pionek bije tylko na odległość 2 pól
            if (!isKing && dist != 2) {return false;}
            return true;
        }
    }

    public static boolean hasAvailableCapture(Board board, int r, int c, boolean isWhiteTurn) 
    {
        Piece piece = board.getPiece(r, c);
        boolean isKing = piece.isKing();
        
        // 4 kierunki po skosie
        int[] dR = {-1, -1, 1, 1};
        int[] dC = {-1, 1, -1, 1};

        for (int i = 0; i < 4; i++) 
        {
            // Logika dla damki (promień)
            if (isKing) 
            {
                int step = 1;
                boolean foundEnemy = false;
                while (true) 
                {
                    int checkR = r + step * dR[i];
                    int checkC = c + step * dC[i];
                    
                    // Koniec planszy
                    if (checkR < 0 || checkR >= Board.SIZE || checkC < 0 || checkC >= Board.SIZE) break;
                    
                    Piece p = board.getPiece(checkR, checkC);
                    if (p != Piece.EMPTY) 
                    {
                        // Swój pionek lub drugi wróg z rzędu
                        if (p.isWhite() == isWhiteTurn) break; 
                        if (foundEnemy) break; 
                        foundEnemy = true;
                    } 
                    else 
                    {
                        // Puste pole za wrogiem = można bić
                        if (foundEnemy) return true; 
                    }
                    step++;
                }
            } 
            // Logika dla zwykłego pionka
            else 
            {
                int toR = r + dR[i] * 2;
                int toC = c + dC[i] * 2;
                
                // Czy skok mieści się na planszy?
                if (toR >= 0 && toR < Board.SIZE && toC >= 0 && toC < Board.SIZE) 
                {
                    Piece mid = board.getPiece(r + dR[i], c + dC[i]);
                    Piece dest = board.getPiece(toR, toC);
                    
                    // Wróg w połowie drogi i puste pole za nim
                    if (mid != Piece.EMPTY && mid.isWhite() != isWhiteTurn && dest == Piece.EMPTY) 
                    {
                        return true;
                    }
                }
            }
        }
        // Brak możliwości bicia
        return false;
    }
}