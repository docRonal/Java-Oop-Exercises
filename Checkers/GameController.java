import javax.swing.Timer;
import javax.swing.SwingUtilities;

public class GameController 
{
    private Board board;
    private GameWindow window;
    
    private Player playerWhite;
    private Player playerBlack;
    private Player currentPlayer;

    private int selectedRow = -1;
    private int selectedCol = -1;
    
    private int turnTimeSeconds = 0;
    private Timer timer;
    private boolean isMultiJumping = false; 
    
    private NetworkManager networkManager; // menedzer od LAN

    public GameController(Board board, Player pWhite, Player pBlack) 
    {
        this.board = board;
        this.playerWhite = pWhite;
        this.playerBlack = pBlack;
        this.currentPlayer = pWhite;
        setupTimer();
    }

    public void setWindow(GameWindow window) 
    {
        this.window = window;
        timer.start();
        checkAITurn(); //w przypadku jesli komputer gra za bialych
    }

    public void setNetworkManager(NetworkManager nm) 
    {
        this.networkManager = nm; // podpinamy neta
    }

    private void setupTimer() 
    {
        timer = new Timer(1000, e ->  //licznik czasu 
        {
            turnTimeSeconds++;
            if (window != null) 
            {
                window.updateTimer(turnTimeSeconds, currentPlayer.isWhite()); //update game
            }
        });
    }

    public void stopTimer() 
    {
        timer.stop();
    }

    public void handleSquareClick(int row, int col) 
    {
        if (!currentPlayer.isHuman()) {return;} //dont move 2 times (blokada na bota i neta)

        Piece clickedPiece = board.getPiece(row, col);

        if (selectedRow != -1 && selectedCol != -1)  // sprawdzamy czy istjnie krok 
        {
            if (MoveValidator.isValidMove(board, selectedRow, selectedCol, row, col, currentPlayer.isWhite(), isMultiJumping)) 
            {
                makeMove(selectedRow, selectedCol, row, col);
                return;
            }
        }

        if (!isMultiJumping) //przy wybieraniu checkera sprawdzamy multijump, swój kolor, i czy pusta jest klatka 
        {
            if (clickedPiece != Piece.EMPTY && clickedPiece.isWhite() == currentPlayer.isWhite()) 
            {
                selectedRow = row; //wszyskto good
                selectedCol = col;
                if(window != null) window.setSelected(row, col);
            } 
            else
            {
                selectedRow = -1; // no nie good
                selectedCol = -1;
                if(window != null) window.setSelected(-1, -1);
            }
        }
    }

    public void applyNetworkMove(Move move) 
    {
        // ruch przychodzi z internetu od goscia
        selectedRow = move.fromRow;
        selectedCol = move.fromCol;
        makeMove(move.fromRow, move.fromCol, move.toRow, move.toCol);
    }

    public void makeMove(int fromRow, int fromCol, int toRow, int toCol) 
    {
        // wysylamy krok przez LAN, jesli my go zrobilismy
        if (currentPlayer.isHuman() && networkManager != null) 
        {
            networkManager.sendMove(new Move(fromRow, fromCol, toRow, toCol));
        }

        Piece piece = board.getPiece(fromRow, fromCol);
        board.setPiece(fromRow, fromCol, Piece.EMPTY); //prznosimy checker na nowe mejsce 

        int dist = Math.abs(toRow - fromRow);
        int dRow = (toRow - fromRow) / dist;
        int dCol = (toCol - fromCol) / dist;
        boolean wasCapture = false;
        
        for (int i = 1; i < dist; i++) //liczymy dlugość jump
        {
            int r = fromRow + i * dRow;
            int c = fromCol + i * dCol;
            if (board.getPiece(r, c) != Piece.EMPTY) 
            {
                board.setPiece(r, c, Piece.EMPTY);
                wasCapture = true; //zbilismy wroga
            }
        }

        if (!piece.isKing()) 
        {
            if (piece == Piece.WHITE && toRow == 0) piece = Piece.WHITE_KING;
            else if (piece == Piece.BLACK && toRow == Board.SIZE - 1) piece = Piece.BLACK_KING; //krol na mejscu 
        }
        board.setPiece(toRow, toCol, piece);

        if (wasCapture && MoveValidator.hasAvailableCapture(board, toRow, toCol, currentPlayer.isWhite())) //jesli jeszcze mamy z kim walczyc
        {
            isMultiJumping = true;
            selectedRow = toRow;
            selectedCol = toCol;
            if (window != null) {
                window.setSelected(selectedRow, selectedCol);
                window.repaintBoard();
            }
            checkAITurn(); 
            return; 
        }

        isMultiJumping = false; // skonczylismy serie
        currentPlayer = (currentPlayer == playerWhite) ? playerBlack : playerWhite; 
        turnTimeSeconds = 0;
        selectedRow = -1;
        selectedCol = -1;
        
        if (window != null) 
        {
            window.setSelected(-1, -1);
            window.repaintBoard();
        }

        if (!GameStatusChecker.hasWhitePieces(board)) 
        {
            GameOverHandler.showGameOverDialog(window, this, "Black wins! All white pieces were captured.");
        } else if (!GameStatusChecker.hasBlackPieces(board)) 
        {
            GameOverHandler.showGameOverDialog(window, this, "White wins! All black pieces were captured.");
        } else {
            checkAITurn();
        }
    }

    private void checkAITurn()  //computer turn 
    {
        // odpalamy tylko jesli gracz to AIPlayer (zeby nie odpalic dla ziomka z LAN)
        if (!currentPlayer.isHuman() && currentPlayer instanceof AIPlayer) 
        {
            Timer aiTimer = new Timer(700, e -> //zatrzymywanie komputera
            {
                AIPlayer ai = (AIPlayer) currentPlayer;
                Move bestMove = ai.getBestMove(board, isMultiJumping, selectedRow, selectedCol);
                
                if (bestMove != null) 
                {
                    selectedRow = bestMove.fromRow;
                    selectedCol = bestMove.fromCol;
                    makeMove(bestMove.fromRow, bestMove.fromCol, bestMove.toRow, bestMove.toCol);
                }
                else 
                {
                    GameOverHandler.showGameOverDialog(window, this, "White wins! AI has no available moves.");
                }
            });
            aiTimer.setRepeats(false);
            aiTimer.start();
        }
    }

    public void restartGame() 
    {
        board.initializeBoard(); //ponownie 12 checkerów
        currentPlayer = playerWhite; //bialy pierwsy
        isMultiJumping = false;
        selectedRow = -1;
        selectedCol = -1;
        turnTimeSeconds = 0;
        
        if (window != null) 
        {
            window.setSelected(-1, -1);
            window.repaintBoard(); //przemalować
            timer.start();
            checkAITurn();
        }
    }
}