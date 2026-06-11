import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIPlayer implements Player 
{
    private boolean isWhite;
    // Generator liczb losowych (do wyboru ruchów)
    private Random random = new Random();

    public AIPlayer(boolean isWhite) 
    {
        this.isWhite = isWhite;
    }

    @Override
    public boolean isWhite() { return isWhite; }

    @Override
    public boolean isHuman() { return false; } // To jest komputer

    public Move getBestMove(Board board, boolean isMultiJumping, int activeRow, int activeCol) 
    {
        // Lista wszystkich legalnych ruchów
        List<Move> possibleMoves = new ArrayList<>();

        // Jeśli komputer jest w trakcie serii bicia (wielokrotne bicie)
        if (isMultiJumping) 
        {
            // Szuka ruchów TYLKO dla aktywnego pionka
            possibleMoves.addAll(getMovesForPiece(board, activeRow, activeCol, true));
        } 
        else 
        {
            // Przeszukiwanie całej planszy
            for (int r = 0; r < Board.SIZE; r++) 
            {
                for (int c = 0; c < Board.SIZE; c++) 
                {
                    Piece p = board.getPiece(r, c);
                    // Znalezienie własnego pionka
                    if (p != Piece.EMPTY && p.isWhite() == this.isWhite) 
                    {
                        // Zbieranie wszystkich jego ruchów
                        possibleMoves.addAll(getMovesForPiece(board, r, c, false));
                    }
                }
            }
        }

        // Brak ruchów oznacza przegraną
        if (possibleMoves.isEmpty()) {return null; }

        // Filtrowanie: szukamy tylko ruchów z biciem
        List<Move> captureMoves = new ArrayList<>();
        for (Move m : possibleMoves) 
        {
            // Skok o 2 pola lub więcej oznacza bicie
            if (Math.abs(m.toRow - m.fromRow) >= 2) 
            {
                captureMoves.add(m);
            }
        }

        // Jeśli jest bicie, komputer MUSI je wykonać 
        if (!captureMoves.isEmpty()) 
        {
            // Losuje jedno z dostępnych bić
            return captureMoves.get(random.nextInt(captureMoves.size()));
        }

        // Jeśli nie ma bicia, losuje jakikolwiek zwykły ruch
        return possibleMoves.get(random.nextInt(possibleMoves.size()));
    }

    // Metoda pomocnicza: sprawdza wszystkie 64 pola dla jednego pionka
    private List<Move> getMovesForPiece(Board board, int r, int c, boolean isMultiJumping) 
    {
        List<Move> moves = new ArrayList<>();
        // Pętla przez każdą kratkę na planszy
        for (int toR = 0; toR < Board.SIZE; toR++) 
        {
            for (int toC = 0; toC < Board.SIZE; toC++) 
            {
                // Pytamy MoveValidator, czy ten pionek może tam pójść
                if (MoveValidator.isValidMove(board, r, c, toR, toC, isWhite, isMultiJumping)) 
                {
                    moves.add(new Move(r, c, toR, toC));
                }
            }
        }
        return moves;
    }
}