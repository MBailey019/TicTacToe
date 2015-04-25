package tictactoe;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Runs a specified number of games of tic-tac-toe
 *
 * @author Matt
 */
public class TicTacToe {

    /**
     * plays the specified number of tic-tac-toe games.
     * 
     * computer-player WOPR must be the first player, and HAL the second,
     * otherwise their brains are gonna get all mixed up.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try 
        {
            System.out.println("branch: WinProb");
            Player frances = new Player("Frances");
            Player wargames = new ComputerPlayer("WOPR", "WOPR_brain-3X3.txt");
            Player spaceOdyssey = new ComputerPlayer("HAL", "HAL_brain-3X3.txt");
            //Player wargames = new ComputerPlayer("WOPR");
            //Player spaceOdyssey = new ComputerPlayer("HAL");
            Game game = Game.setupGame(3, frances, spaceOdyssey);
            for (int gameNumber = 0; gameNumber<1; gameNumber++)
            {
                while (game.getWinner() == 0)
                {
                    game.currentPlayerMark();
                }
                game.clearBoard();
            }
            game.endGame();
        }
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
