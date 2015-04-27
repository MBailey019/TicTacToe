/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tictactoe;

import java.util.Random;

/**
 * A computer player that switches between random & intelligent moves. 
 * 
 * <h2>CS&143 S1 | Project 2 - Tic-Tac-Toe</h2>
 *
 * @author Matt Bailey (MBaileyWebDev@gmail.com)
 * @since 04/10/15
 * @version 1.0
 */
public class HalfDumbComputerPlayer extends ComputerPlayer {
    
    /**
     * Instantiates a HalfDumbComputerPlayer.
     */
    public HalfDumbComputerPlayer(){
        super();
    }
    
    /**
     * jumps back and forth between providing intelligent and random moves.
     * @param max the maximum accepted value
     * @return a move
     */
    @Override
    public int[] provideInput(int max)
    {
        int moveCount = getMovesCount();
        if (moveCount % 2 == 0){
            return super.provideInput(max);
        }
        
        int[] move = new int[2];
        
        Random randomGenerator = new Random();
        int row = randomGenerator.nextInt(max);
        int column = randomGenerator.nextInt(3);
        
        move[0] = row;
        move[1] = column;
        
        return move;
    }
}
