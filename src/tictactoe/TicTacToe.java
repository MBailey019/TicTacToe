/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tictactoe;

/**
 *
 * @author Matt
 */
public class TicTacToe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Player me = new Player("Frances");
        Player computer = new ComputerPlayer("Hal");
        Game game = Game.setupGame(3, me, computer);
        for (int gameNumber = 0; gameNumber<2; gameNumber++)
        {    
            while (game.getWinner() == 0)
            {
                game.currentPlayerMark();
            }
            game.clearBoard();
        }
        game.endGame();
    }
}
