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
        Player me = new Player("Matt");
        Player computer = new ComputerPlayer("Hal");
        Game game = new Game(me, computer);
        while (game.getWinner() == 0){
            game.currentPlayerMark();
        }
    }
    
}
