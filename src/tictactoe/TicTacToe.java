

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Runs a specified number of games of tic-tac-toe. 
 * 
 * <h2>CS&143 S1 | Project 2 - Tic-Tac-Toe</h2>
 *
 * @author Matt Bailey (MBaileyWebDev@gmail.com)
 * @since 04/10/15
 * @version 1.0
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
            String introduction = "Matt Bailey \n" +
                    "Program 2: TicTacToe \n" +
                    "\n" +
                    "A game of wits or just dumb luck? \n" +
                    "Find out for yourself by pitting yourself " +
                    "against the world's \nmost advanced AI system " +
                    "specialized for children's games. Good luck! \n\n";
            
            System.out.println(introduction);
            Player frances = new Player("Frances");
            Player wargames = new ComputerPlayer("WOPR", "WOPR_brain-3X3.txt");
            Player spaceOdyssey = new ComputerPlayer("HAL", "HAL_brain-3X3.txt");
            //Player wargames = new ComputerPlayer("WOPR");
            //Player spaceOdyssey = new ComputerPlayer("HAL");
            
            Game game = Game.setupGame(3, frances, spaceOdyssey);
            assert (game.getPlayers().length == 3) : "wrong number players!!";
            
            for (int gameNumber = 0; gameNumber < 1; gameNumber++)
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
