package tictactoe;

import java.util.Random;

/**
 * A computer player that randomly chooses moves. 
 * 
 * <h2>CS&143 S1 | Project 2 - Tic-Tac-Toe</h2>
 *
 * @author Matt Bailey (MBaileyWebDev@gmail.com)
 * @since 04/10/15
 * @version 1.0
 */
public class DumbComputerPlayer extends ComputerPlayer 
{
    
    /**
     * Instantiates a DumbComputerPlayer.
     */
    public DumbComputerPlayer()
    {
        super();
    }
    
    /**
     * Randomly generates a valid move 
     * @param max the maximum accepted value
     * @return a move
     */
    @Override
    public int[] provideInput(int max)
    {
        int[] move = new int[2];
        
        Random randomGenerator = new Random();
        int row = randomGenerator.nextInt(max);
        int column = randomGenerator.nextInt(3);
        
        move[0] = row;
        move[1] = column;
        
        return move;    
    }
}
