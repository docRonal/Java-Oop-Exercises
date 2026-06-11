import java.io.Serializable;

public class Move implements Serializable {
    private static final long serialVersionUID = 1L; // Идентификатор версии для сети
    
    public int fromRow, fromCol, toRow, toCol;

    public Move(int fromRow, int fromCol, int toRow, int toCol) 
    {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
    }
}