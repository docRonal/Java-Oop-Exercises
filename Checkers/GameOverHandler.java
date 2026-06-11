import javax.swing.JOptionPane;

public class GameOverHandler {

    public static void showGameOverDialog(GameWindow window, GameController controller, String message) 
    {
        controller.stopTimer();
        
        int choice = JOptionPane.showConfirmDialog(window, 
                message + "\nDo you want to play again?", 
                "Game Over", 
                JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) 
        {
            controller.restartGame(); 
        } 
        else 
        {
            System.exit(0); 
        }
    }
}